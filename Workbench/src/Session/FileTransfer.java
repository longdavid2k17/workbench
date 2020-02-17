package Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class FileTransfer
{
    JFileChooser fileChooser;
    JFrame framePointer;
    JPanel chatPanelPointer;

    public final static int SOCKET_PORT = 13267;
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;
    boolean isSent = false;
    boolean isSending = false;

    FileOutputStream fos = null;
    BufferedOutputStream bos = null;     // you may change this
    //public final static String SERVER = "127.0.0.1";
    public final static int FILE_SIZE = 6022386;

    public FileTransfer(JFrame framePointer, JPanel chatPanelPointer)
    {
        this.framePointer = framePointer;
        this.chatPanelPointer = chatPanelPointer;
    }

    public void sendFile()
    {
        fileChooser = new JFileChooser();
        fileChooser.setApproveButtonText("Wyślij");
        fileChooser.setDragEnabled(true);
        fileChooser.setBackground(Color.LIGHT_GRAY);
        fileChooser.setDialogTitle("Wybierz plik");
        fileChooser.repaint();
        if (fileChooser.showSaveDialog(framePointer) == JFileChooser.APPROVE_OPTION)
        {
            File choosedFile = fileChooser.getSelectedFile();
            try
            {
                try
                {
                    servsock = new ServerSocket(SOCKET_PORT);
                    isSending = true;
                    while (isSending)
                    {
                        System.out.println("Oczekiwanie...");
                        try
                        {
                            sock = servsock.accept();
                            System.out.println("Połączono : " + sock);
                            // send file
                            byte [] mybytearray  = new byte [(int)choosedFile.length()];
                            fis = new FileInputStream(choosedFile);
                            bis = new BufferedInputStream(fis);
                            bis.read(mybytearray,0,mybytearray.length);
                            os = sock.getOutputStream();
                            System.out.println("Wysyłanie pliku " + choosedFile.getName() + "(" + mybytearray.length + " bajtów)");
                            os.write(mybytearray,0,mybytearray.length);
                            os.flush();
                            System.out.println("Zakończono");
                            isSent = true;
                            isSending = false;
                        }
                        finally
                        {
                            if (bis != null) bis.close();
                            if (os != null) os.close();
                            if (sock!=null) sock.close();
                        }
                    }
                }
                finally
                {
                    isSending = false;
                    if (servsock != null) servsock.close();
                }
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            finally
            {
                if (isSent)
                {
                    String messeage = "Użytkownik na czacie wysłał plik " + choosedFile.getName();
                    JLabel mess = new JLabel(messeage);
                    mess.setForeground(Color.BLUE.darker());
                    mess.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    mess.addMouseListener(new MouseAdapter()
                    {
                        @Override
                        public void mouseClicked(MouseEvent e)
                        {
                            try
                            {
                                Desktop.getDesktop().browse(new URI("http://www.codejava.net"));

                            }
                            catch (IOException | URISyntaxException e1)
                            {
                                e1.printStackTrace();
                            }
                        }
                    });
                    chatPanelPointer.add(mess);
                }
            }
        }
    }
}
