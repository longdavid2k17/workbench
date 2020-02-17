package Session;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class Connection
{
    private String nickname;
    private int SOCKET_PORT;
    private String ipAdress;
    private final static String SERVER = "127.0.0.1";  // localhost
    //private final static String SERVER = "5.173.120.109";
    private Socket socket;
    private FileOutputStream fos = null;
    private BufferedOutputStream bos = null;
    private static String userIP[];
    private String authCode;

    public String getAuthCode()
    {
        return authCode;
    }

    public void setAuthCode(String authCode)
    {
        this.authCode = authCode;
    }

    public String getIpAdress()
    {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress)
    {
        this.ipAdress = ipAdress;
    }

    public String getNickname()
    {
        return nickname;
    }

    public static String getSERVER()
    {
        return SERVER;
    }
}
