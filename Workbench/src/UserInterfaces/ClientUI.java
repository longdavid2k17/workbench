package UserInterfaces;

import DrawArea.DrawArea;
import Session.FileTransfer;
import UtilitiesPackage.UITools;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;

public class ClientUI
{
    private JFrame mainFrame;
    private JPanel chatPanel, usersPanel;
    private JTextArea messeageArea;
    private JButton settingsButton, sendButton, sendFileButton, openToolsButton;
    private JScrollPane scrollChatPanel, scrollUserPanel;
    private DrawArea drawArea;
    private UITools uiTools;
    private FileTransfer fileTransfer;

    Date date = new Date();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Border border = BorderFactory.createLineBorder(Color.BLACK);
    ImageIcon micIcon = new ImageIcon("src/Resources/Mic.png");
    ImageIcon noMicIcon = new ImageIcon("src/Resources/noMic.gif");

    private String nickname, ipAddress;
    private int port=9876;

    ClientUI(String nickname, String ipAddress) throws IOException
    {
        this.nickname = nickname;
        this.ipAddress = ipAddress;

        uiTools = new UITools();
        fileTransfer = new FileTransfer(mainFrame, chatPanel);

        createUI();
        JLabel newUserLabel = new JLabel("Użytkownik: " + nickname + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", noMicIcon, JLabel.LEFT);
        usersPanel.add(newUserLabel);
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

        settingsButton = new JButton("Ustawienia");
        settingsButton.addActionListener(actionListener);
        settingsButton.setBorder(new RoundedBorder(30));
        settingsButton.setFocusPainted(false);
        settingsButton.setForeground(Color.BLACK);
        settingsButton.setBackground(Color.white);
        settingsButton.setOpaque(false);

        openToolsButton = new JButton("Narzędzia");
        openToolsButton.addActionListener(actionListener);
        openToolsButton.setBorder(new RoundedBorder(30));
        openToolsButton.setFocusPainted(false);
        openToolsButton.setForeground(Color.BLACK);
        openToolsButton.setBackground(Color.white);
        openToolsButton.setOpaque(false);
        openToolsButton.setEnabled(false);

        drawArea = new DrawArea(true);
        drawArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

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

        uiTools.resizeClientUI(chatPanel, usersPanel, drawArea, messeageArea, scrollUserPanel, scrollChatPanel, settingsButton, sendButton, sendFileButton,openToolsButton);

        mainFrame.add(sendButton);
        mainFrame.add(openToolsButton);
        mainFrame.add(scrollUserPanel);
        mainFrame.add(sendFileButton);
        mainFrame.add(scrollChatPanel);
        mainFrame.add(messeageArea);
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
                    String messageString=nickname+" : "+messeageArea.getText();
                    JLabel messageLabel = new JLabel(messageString);
                    chatPanel.add(messageLabel);
                    //clientConnection.sendMessage(messageString);
                    chatPanel.repaint();
                    System.out.println("TEST WIADOMOŚCI - "+messageString);
                    messeageArea.setText("");
                }
                catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
            else if(e.getSource() == openToolsButton)
            {
                ClientTools tools = new ClientTools(drawArea);
            }
        }
    };

    void setOpenToolsButtonActive()
    {
        this.openToolsButton.setEnabled(true);
    }

}
