package UserInterfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class FileReciverFrame extends JFrame
{
    private JFrame mainFrame;
    private JButton submitButton;
    private JLabel messageLabel, iconLabel;
    private Socket sock = null;
    byte [] mybytearray=new byte[6022386];
    private InputStream is;
    private File newRecivedFile;
    private FileOutputStream fos;
    private BufferedOutputStream bos;
    private int portSocket  = 13267;
    private String ServerAddress;
    ImageIcon preloadGif = new ImageIcon("src/Resources/preloader64.gif");
    ImageIcon biggerPreloadGif = new ImageIcon("src/Resources/preloader128.gif");
    ImageIcon checkmarkGif = new ImageIcon("src/Resources/animat-checkmark.gif");
    ImageIcon checkmark = new ImageIcon("src/Resources/check.gif");
    public final static int FILE_SIZE = 6022386;
    private boolean isReciving = false;
    private boolean isRecived = false;


    public void setServerAddress(String serverAddress)
    {
        ServerAddress = serverAddress;
    }

    public FileReciverFrame(String ServerAddress) throws HeadlessException , IOException
    {
        setServerAddress(ServerAddress);
        CreateUI();
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    reciveFile();
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(mainFrame,ex, mainFrame.getTitle(),JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            }
        }.start();
    }
    void reciveFile() throws IOException
    {
        int bytesRead;
        int current = 0;
        try
        {
            sock = new Socket(getServerAddress(), portSocket);
            isReciving=true;
            while (isReciving)
            {
                System.out.println("Łączenie...");

                byte[] mybytearray = new byte[FILE_SIZE];
                InputStream is = sock.getInputStream();
                newRecivedFile = new File("newFile.txt");
                fos = new FileOutputStream(newRecivedFile);
                bos = new BufferedOutputStream(fos);
                bytesRead = is.read(mybytearray, 0, mybytearray.length);
                current = bytesRead;
                do
                {
                    bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
                    if (bytesRead >= 0) current += bytesRead;
                    messageLabel.setText("Pobieranie");
                }
                while (bytesRead > -1);

                bos.write(mybytearray, 0, current);
                bos.flush();
                isRecived=true;
                isReciving=false;
            }
            System.out.println("Plik " + newRecivedFile + " został pobrany (" + current + " bitów)");
            messageLabel.setText("Odebrano plik");
            iconLabel.setIcon(checkmark);
            submitButton.setEnabled(true);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(mainFrame,e.getMessage(),"Błąd podczas połączenia",JOptionPane.ERROR_MESSAGE);
            mainFrame.dispose();
        }
        finally
        {
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (sock != null) sock.close();
        }
    }

    String getServerAddress()
    {
        return ServerAddress;
    }

    void CreateUI()
    {
        mainFrame = new JFrame();
        mainFrame.setTitle("Odbiór plików");
        mainFrame.setSize(300,400);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLayout(null);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.getContentPane().setBackground(new Color(222,240,252));

        submitButton =new JButton();
        submitButton.setText("OK");
        submitButton.setEnabled(false);
        submitButton.setBounds(100,300,100,40);
        submitButton.setVisible(true);
        submitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                mainFrame.dispose();
            }
        });

        messageLabel =new JLabel();
        messageLabel.setText("Oczekiwanie");
        messageLabel.setBounds(110,250,100,40);
        messageLabel.setVisible(true);

        iconLabel =new JLabel();
        iconLabel.setIcon(biggerPreloadGif);
        iconLabel.setBounds(86,61,128,128);
        //iconLabel.setBounds(118,93,64,64);
        iconLabel.setVisible(true);

        mainFrame.add(iconLabel);
        mainFrame.add(messageLabel);
        mainFrame.add(submitButton);
        mainFrame.repaint();
    }
}
