package StartPackage;

import UserInterfaces.ConfigurationUI;

import java.io.IOException;
import java.text.ParseException;

public class WorkbenchStart
{
    public static void main(String args[])
    {
        try
        {
            ConfigurationUI startWorkbench = new ConfigurationUI();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
}
