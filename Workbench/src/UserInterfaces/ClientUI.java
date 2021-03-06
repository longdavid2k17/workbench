package UserInterfaces;

import DrawArea.DrawArea;
import Session.FileTransfer;
import UtilitiesPackage.UITools;
import VoiceChatPackage.VoiceClient;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

class ChatAccess extends Observable
{
    private Socket socket;
    private OutputStream outputStream;

    @Override
    public void notifyObservers(Object arg)
    {
        super.setChanged();
        super.notifyObservers(arg);
    }

    public void InitSocket(String server, int port) throws IOException
    {
        socket = new Socket(server, port);
        outputStream = socket.getOutputStream();

        Thread receivingThread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null)
                        notifyObservers(line);
                }
                catch (IOException ex)
                {
                    notifyObservers(ex);
                }
            }
        };
        receivingThread.start();
    }

    private static final String CRLF = "\r\n";

    public void send(String text)
    {
        try
        {
            outputStream.write((text + CRLF).getBytes());
            outputStream.flush();
        }
        catch (IOException ex)
        {
            notifyObservers(ex);
        }
    }

    public void close()
    {
        try
        {
            socket.close();
        }
        catch (IOException ex)
        {
            notifyObservers(ex);
        }
    }
}

public class ClientUI implements Observer
{
    private JFrame mainFrame;
    private JPanel chatPanel;
    private JTextArea messeageArea;
    private JButton settingsButton, sendButton, sendFileButton,reciveFileButton;
    private JScrollPane scrollChatPanel;
    private JPanel drawAreaPanel;
    private JScrollPane scrollDrawAreaPanel;
    private DrawArea drawArea;
    private UITools uiTools;
    private FileTransfer fileTransfer;


    Date date = new Date();
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();
    Border border = BorderFactory.createLineBorder(Color.BLACK);

    private String nickname, ipAddress;
    private int port=9876;
    ChatAccess access;

    ClientUI(String ipAddress) throws IOException
    {
        this.nickname = nickname;
        this.ipAddress = ipAddress;
        access = new ChatAccess();
        access.addObserver(this);

        uiTools = new UITools();
        fileTransfer = new FileTransfer(mainFrame, chatPanel);

        try
        {
            access.InitSocket(ipAddress,port);
        }
        catch (IOException ex)
        {
            System.out.println("Nie udało się podłączyć do serwera na " + ipAddress + ":" + port);
            String message = "Nie udało się połączyć z serwerem na "+ipAddress+":"+port+"\nProgram zostanie zamknięty. Spróbuj ponownie!";
            JOptionPane.showMessageDialog(mainFrame,message,"Błąd połączenia!",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
        }
        createUI();
        new VoiceClient(ipAddress,8765).start();
    }

    void createUI()
    {
        mainFrame = new JFrame("Workbench");
        mainFrame.setSize(width, height - 60);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);
        mainFrame.setVisible(true);
        mainFrame.setLayout(null);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.getContentPane().setBackground(new Color(222,240,252));
        mainFrame.validate();
        mainFrame.setIconImage(new ImageIcon(getClass().getResource("/Resources/app_icon.png")).getImage());

        settingsButton = new JButton("Ustawienia");
        settingsButton.addActionListener(actionListener);
        settingsButton.setBorder(new RoundedBorder(30));
        settingsButton.setFocusPainted(false);
        settingsButton.setForeground(Color.BLACK);
        settingsButton.setBackground(Color.white);
        settingsButton.setOpaque(false);

        try
        {
            drawArea = new DrawArea(ipAddress,false,mainFrame);
            drawAreaPanel = new JPanel();
            drawAreaPanel.add(drawArea);
            scrollDrawAreaPanel = new JScrollPane(drawAreaPanel);
            scrollDrawAreaPanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        mainFrame.add(chatPanel);

        messeageArea = new JTextArea();
        messeageArea.setEditable(true);
        messeageArea.setLineWrap(true);
        messeageArea.setBackground(Color.white);
        messeageArea.setVisible(true);
        messeageArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        messeageArea.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    try
                    {
                        if(!messeageArea.getText().isEmpty())
                        {
                            String messageString =  messeageArea.getText();
                            access.send(messageString);
                            chatPanel.repaint();
                            messeageArea.setText(null);
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

        scrollChatPanel = new JScrollPane(chatPanel);
        scrollChatPanel.setVisible(true);
        scrollChatPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollChatPanel.setViewportView(chatPanel);

        sendButton = new JButton("Wyślij");
        sendButton.setBorder(new RoundedBorder(30));
        sendButton.setFocusPainted(false);
        sendButton.setOpaque(false);
        sendButton.setForeground(Color.BLACK);
        sendButton.setBackground(Color.white);
        sendButton.addActionListener(actionListener);

        sendFileButton = new JButton("Wyślij plik");
        sendFileButton.setBorder(new RoundedBorder(30));
        sendFileButton.addActionListener(actionListener);
        sendFileButton.setFocusPainted(false);
        sendFileButton.setOpaque(false);
        sendFileButton.setForeground(Color.BLACK);
        sendFileButton.setBackground(Color.white);

        reciveFileButton = new JButton("Odbierz plik");
        reciveFileButton.setBorder(new RoundedBorder(30));
        reciveFileButton.addActionListener(actionListener);
        reciveFileButton.setFocusPainted(false);
        reciveFileButton.setOpaque(false);
        reciveFileButton.setForeground(Color.BLACK);
        reciveFileButton.setBackground(Color.white);

        uiTools.resizeClientUI(chatPanel, drawArea, messeageArea, scrollChatPanel, settingsButton, sendButton, sendFileButton,reciveFileButton);

        mainFrame.add(sendButton);
        mainFrame.add(sendFileButton);
        mainFrame.add(reciveFileButton);
        mainFrame.add(scrollChatPanel);
        mainFrame.add(messeageArea);
        mainFrame.add(drawArea);
        mainFrame.add(settingsButton);

        mainFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                access.close();
            }
        });
        mainFrame.validate();
        mainFrame.repaint();
    }

    ActionListener actionListener = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == settingsButton)
            {
                SettingsUI settings = new SettingsUI();
                if (settings.getMicAvalibility())
                {
                    mainFrame.repaint();
                }
            }
            else if (e.getSource()==sendFileButton)
            {
                fileTransfer.sendFile();
            }
            else if(e.getSource() == sendButton)
            {
                try
                {
                    if(!messeageArea.getText().isEmpty())
                    {
                        String messageString =  messeageArea.getText();
                        access.send(messageString);
                        chatPanel.repaint();
                        messeageArea.setText(null);
                    }
                }
                catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
            else if(e.getSource() == reciveFileButton)
            {
                try
                {
                    new FileReciverFrame(ipAddress);
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    };


    @Override
    public void update(Observable o, Object arg)
    {
        final Object finalArg = arg;
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                chatPanel.add(new JLabel(finalArg.toString()));
                chatPanel.revalidate();
                chatPanel.repaint();
            }
        });
    }
}
