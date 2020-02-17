import javax.swing.*;
import java.io.*;
import java.net.*;

public class Chat extends JFrame
{
    private static ServerSocket serverSocket;
    private static int port = 9876;
    static String mulitcastHost = "239.0.0.0";
    boolean isServerRunning = true;

    JLabel mess;

    public boolean isServerRunning()
    {
        return isServerRunning;
    }

    public void setServerRunning(boolean serverRunning)
    {
        isServerRunning = serverRunning;
    }

    void sendMessage(String nickname,JFrame framePointer,JTextArea messageAreaPointer,JPanel panelPointer) throws IOException
    {
        if(!messageAreaPointer.getText().isEmpty())
        {
/*                    BufferedImage image = null;
                    try
                    {
                        image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                        ImageIO.write(image, "png", new File("screenshot.png"));
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }*/

            String message = nickname+" : "+messageAreaPointer.getText()+"\n";
            //System.out.println(message+message);
            JLabel mess = new JLabel(message);
            //panelPointer.add(mess);

            messageAreaPointer.setText("");


           /* Socket clientSocket = new Socket("localhost", 9876);
            System.out.println("Connected!");

            // get the output stream from the socket.
            OutputStream outputStream = clientSocket.getOutputStream();
            // create a data output stream from the output stream so we can send data through it
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            System.out.println("Sending string to the ServerSocket");

            // write the message we want to send
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush(); // send the message
            dataOutputStream.close(); // close the output stream when we're done.
            clientSocket.close();*/

            MulticastSocket socket = new MulticastSocket(port);
            socket.setTimeToLive(0);
            //this on localhost only (For a subnet set it as 1)
            InetAddress group = InetAddress.getByName(mulitcastHost);
            socket.joinGroup(group);

            byte[] buffer = message.getBytes();
            DatagramPacket datagram = new DatagramPacket(buffer,buffer.length,group,port);
            socket.send(datagram);

            panelPointer.validate();
            panelPointer.repaint();
            framePointer.validate();
            framePointer.repaint();
        }
    }
}