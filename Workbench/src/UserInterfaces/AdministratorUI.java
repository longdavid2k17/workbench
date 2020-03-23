package UserInterfaces;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import DrawArea.DrawArea;
import Session.*;
import UtilitiesPackage.UITools;
import VoiceChatPackage.*;

public class AdministratorUI implements Observer
{
    private JFrame mainFrame;
    private JPanel chatPanel, usersPanel;
    private JTextArea messeageArea;
    private JButton settingsButton, sendButton, sendFileButton, reciveFileButton;
    private JLabel addressLabel, authCodeLabel;
    private JScrollPane scrollChatPanel, scrollUserPanel;
    private JButton clearBtn, textButton, colorButton, rubberButton, sendInviteButton;
    private JPanel drawAreaPanel;
    private JScrollPane scrollDrawAreaPanel;
    private DrawArea drawArea;
    private UITools uiTools;
    private FileTransfer fileTransfer;
    private ChatAccess access;

    private String authCode;
    private String ipAddress;
    private int port;

    public static boolean userConnected = false;
    public static String userName = null;

    Date date = new Date();
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();
    Border border = BorderFactory.createLineBorder(Color.BLACK);
    ImageIcon micIcon = new ImageIcon("src/Resources/Mic.png");
    ImageIcon noMicIcon = new ImageIcon("src/Resources/noMic.gif");

    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static final int maxClientsCount = 10;
    private static final ClientThread[] threads = new ClientThread[maxClientsCount];

    AdministratorUI(String ipAddress, String authCode, int port) throws IOException
    {
        this.ipAddress = ipAddress;
        this.authCode = authCode;
        this.port = port;
        access = new ChatAccess();
        access.addObserver(this);

        uiTools = new UITools();
        fileTransfer = new FileTransfer(mainFrame, chatPanel);


        createUI();

        try
        {
            access.InitSocket(ipAddress,port);
        }
        catch (IOException ex)
        {
            System.out.println("Nie udało się podłączyć do serwera na " + ipAddress + " : " + port);
            ex.printStackTrace();
            System.exit(0);
        }

        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    new VoiceChatServer(8765,true);
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(mainFrame,ex,mainFrame.getTitle(),JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            }
        }.start();

        new Thread()
        {
            @Override
            public void run()
            {
                for (;;)
                {
                    Utils.sleep(100);
                }
            }
        }.start();

        new Thread()
        {
            @Override
            public void run()
            {
                for (;;)
                {
                    checkForConnection();
                    System.out.println(userConnected);
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new VoiceClient(ipAddress,8765).start();
        DrawArea.addToClientList(ipAddress);
    }

    public static void setUserConnected(boolean value)
    {
        userConnected = value;
    }
    public static void setUserName(String name)
    {
        userName = name;
    }

    void checkForConnection()
    {
        if(userConnected==true)
        {
            Date freshdate = new Date();
            usersPanel.add(new JLabel("Połączono użytkownika "+userName+" : " + freshdate.getHours() + ":" + freshdate.getMinutes() + ":" + freshdate.getSeconds() + ")", micIcon, JLabel.LEFT));
            usersPanel.repaint();
            usersPanel.revalidate();
            userConnected=false;
            userName=null;
        }
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
        mainFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        clearBtn = new JButton("Wyczyść");
        clearBtn.addActionListener(actionListener);
        clearBtn.setBorder(new RoundedBorder(30));
        clearBtn.setFocusPainted(false);
        clearBtn.setOpaque(false);
        clearBtn.setForeground(Color.BLACK);
        clearBtn.setBackground(Color.white);

        textButton = new JButton("T");
        textButton.addActionListener(actionListener);
        textButton.setBorder(new RoundedBorder(30));
        textButton.setFocusPainted(false);
        textButton.setOpaque(false);
        textButton.setForeground(Color.BLACK);
        textButton.setBackground(Color.white);

        rubberButton = new JButton("Gumka");
        rubberButton.addActionListener(actionListener);
        rubberButton.setBorder(new RoundedBorder(30));
        rubberButton.setFocusPainted(false);
        rubberButton.setForeground(Color.BLACK);
        rubberButton.setBackground(Color.white);
        rubberButton.setOpaque(false);

        settingsButton = new JButton("Ustawienia");
        settingsButton.addActionListener(actionListener);
        settingsButton.setBorder(new RoundedBorder(30));
        settingsButton.setFocusPainted(false);
        settingsButton.setForeground(Color.BLACK);
        settingsButton.setBackground(Color.white);
        settingsButton.setOpaque(false);

        sendInviteButton = new JButton("Zaproś do czatu");
        sendInviteButton.setVisible(false);
        sendInviteButton.addActionListener(actionListener);
        sendInviteButton.setFocusPainted(false);
        sendInviteButton.setBorder(new RoundedBorder(30));
        sendInviteButton.setForeground(Color.BLACK);
        sendInviteButton.setBackground(Color.white);
        sendInviteButton.setOpaque(false);


        try
        {
            drawArea = new DrawArea(ipAddress,true,mainFrame);
            drawAreaPanel = new JPanel();
            drawAreaPanel.add(drawArea);
            scrollDrawAreaPanel = new JScrollPane(drawAreaPanel);
            scrollDrawAreaPanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //drawArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        addressLabel = new JLabel();
        addressLabel.setText(ipAddress);
        addressLabel.setVisible(false);
        mainFrame.add(addressLabel);

        authCodeLabel = new JLabel();
        authCodeLabel.setText("Twój kod autoryzacyjny: " + authCode);
        authCodeLabel.setVisible(false);
        mainFrame.add(authCodeLabel);

        colorButton = new JButton();
        colorButton.setToolTipText("Zmień kolor czcionki");
        colorButton.setBackground(Color.black);
        colorButton.addActionListener(actionListener);

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
                            String messageString = messeageArea.getText();
                            access.send(messageString);
                            chatPanel.repaint();
                            messeageArea.setText(null);
                            //messeageArea.
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

        usersPanel = new JPanel();
        usersPanel.setBackground(Color.white);
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
        usersPanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        mainFrame.add(usersPanel);

        scrollUserPanel = new JScrollPane();
        scrollUserPanel.setVisible(true);
        scrollUserPanel.setViewportView(usersPanel);

        uiTools.resizeAdminUI(chatPanel, usersPanel, drawArea, messeageArea, scrollUserPanel, scrollChatPanel, textButton, colorButton, clearBtn, rubberButton, settingsButton, sendButton, sendFileButton, sendInviteButton, authCodeLabel, addressLabel,reciveFileButton);
        sendInviteButton.setVisible(true);
        authCodeLabel.setVisible(true);
        addressLabel.setVisible(true);

        mainFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                access.close();
            }
        });

        mainFrame.add(sendButton);
        mainFrame.add(scrollUserPanel);
        mainFrame.add(sendFileButton);
        mainFrame.add(scrollChatPanel);
        mainFrame.add(messeageArea);
        mainFrame.add(colorButton);
        mainFrame.add(textButton);
        mainFrame.add(clearBtn);
        mainFrame.add(rubberButton);
        mainFrame.add(sendInviteButton);
        mainFrame.add(reciveFileButton);
        mainFrame.add(drawArea);
        mainFrame.add(settingsButton);

        Thread receivingThread = new Thread()
        {
            @Override
            public void run()
            {
                int portNumber = 9876;
                try
                {
                    serverSocket = new ServerSocket(portNumber);
                    System.out.println("Uruchomiono serwer czatu na porcie: "+portNumber);
                }
                catch (IOException e)
                {
                    System.out.println(e);
                }

                while (true)
                {
                    try
                    {
                        clientSocket = serverSocket.accept();
                        int i = 0;
                        for (i = 0; i < maxClientsCount; i++)
                        {
                            if (threads[i] == null)
                            {
                                (threads[i] = new ClientThread(clientSocket, threads)).start();
                                break;
                            }
                        }
                        if (i == maxClientsCount)
                        {
                            PrintStream os = new PrintStream(clientSocket.getOutputStream());
                            os.println("Serwer obciążony. Nie udało się połączyć.");
                            os.close();
                            clientSocket.close();
                        }
                    }
                    catch (IOException e)
                    {
                        System.out.println(e);
                    }
                }
            }
        };
        receivingThread.start();

        usersPanel.validate();
        scrollUserPanel.validate();
        mainFrame.validate();
        mainFrame.repaint();
    }

    ActionListener actionListener = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == clearBtn)
            {
                drawArea.clear();
            }
            else if (e.getSource() == textButton)
            {
                //drawArea.text();
            }
            else if (e.getSource() == rubberButton)
            {
                drawArea.rubber();
            }
            else if (e.getSource() == settingsButton)
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
            else if (e.getSource() == sendInviteButton)
            {
                String invite = "Adres serwera: " + ipAddress + " | Kod dostępu do sesji: " + authCode;
                StringSelection stringSelection = new StringSelection(invite);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                JOptionPane.showMessageDialog(mainFrame, "Utworzono i skopiowano do schowka twoje zaproszenie.\n" + invite, "Zaproś", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(e.getSource() == colorButton)
            {
                drawArea.setColor(mainFrame);
                colorButton.setBackground(drawArea.getColor());
            }
            else if(e.getSource() == sendButton)
            {
                try
                {
                    if(!messeageArea.getText().isEmpty())
                    {
                        String messageString=messeageArea.getText();
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

