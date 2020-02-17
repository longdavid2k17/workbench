import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class Session
{
    public int SOCKET_PORT;
    public final static String SERVER = "127.0.0.1";  // localhost
    //public final static String SERVER = "5.173.120.109";
    Socket socket;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    private static String userIP[];
}
