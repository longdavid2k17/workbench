package UtilitiesPackage;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ReadThread implements Runnable
{
    private MulticastSocket socket;
    private InetAddress group;
    private int port;
    private static final int MAX_LEN = 1000;
    JPanel panelPointer;
    public ReadThread(MulticastSocket socket, InetAddress group, int port, JPanel panelPointer)
    {
        this.socket = socket;
        this.group = group;
        this.port = port;
        this.panelPointer = panelPointer;
    }

    @Override
    public void run()
    {
        byte[] buffer = new byte[ReadThread.MAX_LEN];
        DatagramPacket datagram = new
                DatagramPacket(buffer, buffer.length, group, port);
        String message;
        while (true)
        {
            try
            {
                socket.receive(datagram);
                message = new String(buffer, 0, datagram.getLength(), "UTF-8");
                System.out.println("Odebrano wiadomość: " + message);
                JLabel mess = new JLabel(message);
                panelPointer.add(mess);
                Thread.sleep(500);
                panelPointer.repaint();
            }
            catch (IOException e)
            {
                System.out.println("Socket closed!");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
