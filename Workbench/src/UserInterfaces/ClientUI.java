package UserInterfaces;

import DrawArea.DrawArea;
import Session.Connection;
import Session.FileTransfer;
import UtilitiesPackage.Chat;
import UtilitiesPackage.ReadThread;
import UtilitiesPackage.UITools;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;

public class ClientUI
{
    private JFrame mainFrame;
    private JPanel chatPanel, usersPanel;
    private JTextArea messeageArea;
    private JButton settingsButton, sendButton, sendFileButton;
    private JScrollPane scrollChatPanel, scrollUserPanel;
    private JButton clearBtn, textButton, colorButton, rubberButton;
    private DrawArea drawArea;
    private UITools uiTools;
    private Chat chatListiner;
    private Connection connection;
    private FileTransfer fileTransfer;

    Date date = new Date();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Border border = BorderFactory.createLineBorder(Color.BLACK);
    ImageIcon micIcon = new ImageIcon("src/Resources/Mic.png");
    ImageIcon noMicIcon = new ImageIcon("src/Resources/noMic.gif");

    private String nickname;
    private int port=9876;

    ClientUI(String nickname) throws IOException
    {
        this.nickname = nickname;
        chatListiner = new Chat();
        chatListiner.setServerRunning(true);

        uiTools = new UITools();
        chatListiner = new Chat();
        connection = new Connection();
        fileTransfer = new FileTransfer(mainFrame, chatPanel);

        createUI();
        JLabel newUserLabel = new JLabel("Użytkownik: " + nickname + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", noMicIcon, JLabel.LEFT);
        usersPanel.add(newUserLabel);

        MulticastSocket socket = null;
        try
        {
            socket = new MulticastSocket(port);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        socket.setTimeToLive(0);
        InetAddress group = InetAddress.getByName(Chat.getMulitcastHost());
        socket.joinGroup(group);
        Thread t = new Thread(new ReadThread(socket,group,port,chatPanel));
        t.start();
    }
    void createUI()
    {
        mainFrame = new JFrame("Workbench");
        mainFrame.setSize(screenSize.width, screenSize.height - 60);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);
        mainFrame.setVisible(true);
        mainFrame.setLayout(null);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.getContentPane().setBackground(new Color(222,240,252));
        mainFrame.validate();

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

        drawArea = new DrawArea(true);
        drawArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

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

        usersPanel = new JPanel();
        usersPanel.setBackground(Color.white);
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
        usersPanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        mainFrame.add(usersPanel);

        scrollUserPanel = new JScrollPane();
        scrollUserPanel.setVisible(true);
        scrollUserPanel.setViewportView(usersPanel);

        uiTools.resizeClientUI(chatPanel, usersPanel, drawArea, messeageArea, scrollUserPanel, scrollChatPanel, textButton, colorButton, clearBtn, rubberButton, settingsButton, sendButton, sendFileButton);

        mainFrame.add(sendButton);
        mainFrame.add(scrollUserPanel);
        mainFrame.add(sendFileButton);
        mainFrame.add(scrollChatPanel);
        mainFrame.add(messeageArea);
        mainFrame.add(colorButton);
        mainFrame.add(textButton);
        mainFrame.add(clearBtn);
        mainFrame.add(rubberButton);
        mainFrame.add(drawArea);
        mainFrame.add(settingsButton);

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
                drawArea.text();
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

            else if(e.getSource() == colorButton)
            {
                drawArea.setColor(mainFrame);
                colorButton.setBackground(drawArea.getColor());
            }
            else if(e.getSource() == sendButton)
            {
                try
                {
                    chatListiner.sendMessage(nickname, mainFrame, messeageArea, chatPanel);
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    };
}
