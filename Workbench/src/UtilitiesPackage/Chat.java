package UtilitiesPackage;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;

public class Chat
{
    private static ServerSocket serverSocket;
    private static int port = 9876;
    static String mulitcastHost = "239.0.0.0";
    boolean isServerRunning = true;

    public static String getMulitcastHost()
    {
        return mulitcastHost;
    }

    public void sendMessage(String nickname, JFrame framePointer, JTextArea messageAreaPointer, JPanel panelPointer) throws IOException
    {
        if(!messageAreaPointer.getText().isEmpty())
        {
            String message = nickname+" : "+messageAreaPointer.getText()+"\n";
            JLabel messageLabel = new JLabel(message);
            //panelPointer.add(messageLabel);

            messageAreaPointer.setText("");
            //MulticastSocket socket = new MulticastSocket(port);
            //socket.setTimeToLive(0);
            //InetAddress group = InetAddress.getByName(mulitcastHost);
            //socket.joinGroup(group);

            byte[] buffer = message.getBytes();
            //DatagramPacket datagram = new DatagramPacket(buffer,buffer.length,group,port);
            //socket.send(datagram);

            panelPointer.validate();
            panelPointer.repaint();
            framePointer.validate();
            framePointer.repaint();
        }
    }
    public boolean isServerRunning()
    {
        return isServerRunning;
    }

    public void setServerRunning(boolean serverRunning)
    {
        isServerRunning = serverRunning;
    }
}
