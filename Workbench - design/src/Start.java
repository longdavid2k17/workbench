import java.io.IOException;
import java.text.ParseException;

public class Start
{
    public static void main(String args[]) throws IOException
    {
        try
        {
            new TypeAction();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
}
