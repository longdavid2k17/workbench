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

    private UpnpService u;

    public void addToBroadcastQueue(Message m)
    {
        try
        {
            broadCastQueue.add(m);
        }
        catch (Throwable t)
        {
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
            Log.add("Rozpoczęcie przekazywanie portów NAT...");
            try
            {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                System.out.println("Zainicjowano przekazywanie portów");
            }
            catch (SocketException ex)
            {
                Log.add("Błąd sieci");
                System.out.println("Błąd sieci");
                throw new Exception("Błąd sieci");
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
                Log.add("Nie połączono do żadnej sieci");
                throw new Exception("Błąd sieci");
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
                Log.add("Nie połączono do żadnej sieci IPv4");
                throw new Exception("Błąd sieci");
            }
            u = new UpnpServiceImpl(new PortMappingListener(new PortMapping(port, ipAddress, PortMapping.Protocol.TCP)));
            u.getControlPoint().search();
        }
        try
        {
            s = new ServerSocket(port);
            Log.add("Port " + port + ": serwer rozpoczął pracę");
            System.out.println("Serwer działa...");
        }
        catch (IOException ex)
        {
            Log.add("Błąd serwera: " + ex + "(port " + port + ")");
            throw new Exception("Błąd "+ex);
        }
        new BroadcastThread().start();
        for (;;)
        {
            try
            {
                Socket c = s.accept();
                VoiceClientConnection cc = new VoiceClientConnection(this, c); //create a ClientConnection thread
                cc.start();
                addToClients(cc);
                Log.add("Nowy klient " + c.getInetAddress() + ":" + c.getPort() + " na porcie " + port);
            }
            catch (IOException ex)
            {

            }
        }
    }

    private void addToClients(VoiceClientConnection cc)
    {
        try
        {
            clients.add(cc);
            System.out.println("Połączono klienta! "+cc.getName());
        }
        catch (Throwable t)
        {
            Utils.sleep(1);
            addToClients(cc);
        }
    }

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
                    ArrayList<VoiceClientConnection> toRemove = new ArrayList<VoiceClientConnection>();
                    for (VoiceClientConnection cc : clients)
                    {
                        if (!cc.isAlive())
                        {
                            Log.add("Zamknięto martwe połączenie: " + cc.getInetAddress() + ":" + cc.getPort() + " na porcie " + port);
                            toRemove.add(cc);
                        }
                    }
                    clients.removeAll(toRemove);
                    if (broadCastQueue.isEmpty())
                    {
                        Utils.sleep(10);
                        continue;
                    }
                    else
                    {
                        Message m = broadCastQueue.get(0);
                        for (VoiceClientConnection cc : clients)
                        {
                            if (cc.getChId() != m.getChId())
                            {
                                cc.addToQueue(m);
                            }
                        }
                        broadCastQueue.remove(m);
                    }
                }
                catch (Throwable t)
                {

                }
            }
        }
    }
}

