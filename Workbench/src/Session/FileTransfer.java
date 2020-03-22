package Session;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    BufferedOutputStream bos = null;
    public final static int FILE_SIZE = 6022386;

    public FileTransfer(JFrame framePointer, JPanel chatPanelPointer)
    {
        this.framePointer = framePointer;
        this.chatPanelPointer = chatPanelPointer;
    }

    public void sendFile()
    {
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Pliki tekstowe (.txt)","txt");
        fileChooser = new JFileChooser();
        fileChooser.setApproveButtonText("Wyślij");
        fileChooser.setDragEnabled(true);
        fileChooser.setBackground(Color.LIGHT_GRAY);
        fileChooser.setDialogTitle("Wybierz plik");
        fileChooser.repaint();
        fileChooser.setFileFilter(txtFilter);
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
                    String message = "Użytkownik na czacie wysłał plik " + choosedFile.getName();
                    chatPanelPointer.add(new JLabel(message));
                    chatPanelPointer.revalidate();
                    chatPanelPointer.repaint();
                }
            }
        }
    }
}
