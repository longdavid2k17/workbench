package VoiceChatPackage;

import javax.swing.JOptionPane;

/**
 *
 * @author dosse
 */
public class ErrorMessage
{
    public static void main(String args[])
    {
        JOptionPane.showMessageDialog(new JOptionPane(),"This is a library, not a program.\nPlease run VoiceChat_Server or VoiceChat_Client");
    }
}
