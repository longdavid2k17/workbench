package VoiceChatPackage;

import org.teleal.cling.UpnpService;
import org.teleal.cling.UpnpServiceImpl;
import org.teleal.cling.support.igd.PortMappingListener;
import org.teleal.cling.support.model.PortMapping;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class VoiceChatServer
{
    private ArrayList<Message> broadCastQueue = new ArrayList<Message>();
    private ArrayList<VoiceClientConnection> clients = new ArrayList<VoiceClientConnection>();
    private int port;

    private UpnpService u; //when upnp is enabled, this points to the upnp service

    public void addToBroadcastQueue(Message m)
    {
        //add a message to the broadcast queue. this method is used by all ClientConnection instances
        try
        {
            broadCastQueue.add(m);
        }
        catch (Throwable t)
        {
            //mutex error, try again
            Utils.sleep(1);
            addToBroadcastQueue(m);
        }
    }
    private ServerSocket s;

    public VoiceChatServer(int port, boolean upnp) throws Exception
    {
        this.port = port;
        if(upnp)
        {
            Log.add("Setting up NAT Port Forwarding...");
            //first we need the address of this machine on the local network
            try
            {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                System.out.println("Zainicjowano przekazywanie portów");
            }
            catch (SocketException ex)
            {
                Log.add("Network error");
                System.out.println("Błąd!!!");
                throw new Exception("Network error");
            }
            String ipAddress = null;
            Enumeration<NetworkInterface> net = null;
            try
            {
                net = NetworkInterface.getNetworkInterfaces();
            }
            catch (SocketException e)
            {
                System.out.println("Nie połączono do żadnej sieci");
                Log.add("Not connected to any network");
                throw new Exception("Network error");
            }

            while (net.hasMoreElements())
            {
                NetworkInterface element = net.nextElement();
                Enumeration<InetAddress> addresses = element.getInetAddresses();
                while (addresses.hasMoreElements())
                {
                    InetAddress ip = addresses.nextElement();
                    if (ip instanceof Inet4Address)
                    {
                        if (ip.isSiteLocalAddress())
                        {
                            ipAddress = ip.getHostAddress();
                            break;
                        }
                    }
                }
                if (ipAddress != null)
                {
                    break;
                }
            }
            if (ipAddress == null)
            {
                System.out.println("Nie połączono do żadnej sieci IPv4");
                Log.add("Not connected to any IPv4 network");
                throw new Exception("Network error");
            }
            u = new UpnpServiceImpl(new PortMappingListener(new PortMapping(port, ipAddress, PortMapping.Protocol.TCP)));
            u.getControlPoint().search();
        }
        try
        {
            s = new ServerSocket(port); //listen on specified port
            Log.add("Port " + port + ": server started");
            System.out.println("Serwer działa...");
        } catch (IOException ex) {
            Log.add("Server error " + ex + "(port " + port + ")");
            throw new Exception("Error "+ex);
        }
        new BroadcastThread().start(); //create a BroadcastThread and start it
        for (;;) { //accept all incoming connection
            try {
                Socket c = s.accept();
                VoiceClientConnection cc = new VoiceClientConnection(this, c); //create a ClientConnection thread
                cc.start();
                addToClients(cc);
                Log.add("new client " + c.getInetAddress() + ":" + c.getPort() + " on port " + port);
            } catch (IOException ex) {
            }
        }
    }

    private void addToClients(VoiceClientConnection cc)
    {
        try
        {
            clients.add(cc); //add the new connection to the list of connections
            System.out.println("Połączono klienta! "+cc.getName());
        }
        catch (Throwable t)
        {
            //mutex error, try again
            Utils.sleep(1);
            addToClients(cc);
        }
    }

    /**
     * broadcasts messages to each ClientConnection, and removes dead ones
     */
    private class BroadcastThread extends Thread
    {

        public BroadcastThread()
        {

        }

        @Override
        public void run()
        {
            for (;;)
            {
                try
                {
                    ArrayList<VoiceClientConnection> toRemove = new ArrayList<VoiceClientConnection>(); //create a list of dead connections
                    for (VoiceClientConnection cc : clients)
                    {
                        if (!cc.isAlive())
                        {
                            //connection is dead, need to be removed
                            Log.add("dead connection closed: " + cc.getInetAddress() + ":" + cc.getPort() + " on port " + port);
                            toRemove.add(cc);
                        }
                    }
                    clients.removeAll(toRemove); //delete all dead connections
                    if (broadCastQueue.isEmpty())
                    { //nothing to send
                        Utils.sleep(10); //avoid busy wait
                        continue;
                    }
                    else
                    { //we got something to broadcast
                        Message m = broadCastQueue.get(0);
                        for (VoiceClientConnection cc : clients)
                        {
                            //broadcast the message
                            if (cc.getChId() != m.getChId())
                            {
                                cc.addToQueue(m);
                            }
                        }
                        broadCastQueue.remove(m); //remove it from the broadcast queue
                    }
                }
                catch (Throwable t)
                {
                    //mutex error, try again
                }
            }
        }
    }
}

