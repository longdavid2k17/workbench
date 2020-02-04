import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileSender
{
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
    File newRecivedFile;

    void sendFile(File file) throws IOException
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
                    byte [] mybytearray  = new byte [(int)file.length()];
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    bis.read(mybytearray,0,mybytearray.length);
                    os = sock.getOutputStream();
                    System.out.println("Wysyłanie pliku " + file.getName() + "(" + mybytearray.length + " bajtów)");
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

    void reciveFile() throws IOException
    {
        int bytesRead;
        int current = 0;
        try
        {
            //sock = new Socket(SERVER, SOCKET_PORT);
            System.out.println("Connecting...");

            byte [] mybytearray  = new byte [FILE_SIZE];
            InputStream is = sock.getInputStream();
            newRecivedFile = new File("newFile.txt");
            fos = new FileOutputStream(newRecivedFile);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bos.write(mybytearray, 0 , current);
            bos.flush();
            System.out.println("File " + newRecivedFile
                    + " downloaded (" + current + " bytes read)");
        }
        finally
        {
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (sock != null) sock.close();
        }
    }
}


