import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

/*
proponowane opcje:
- rysowanie kwadratów i okręgów
- ulepszone dodawanie tekstu
- zaznaczanie fragmentu tablicy półprzeźroczystym zółtym znacznikiem
- ulepszona gumka - nie jako kursor ale normalny kwadracik
- łącznie przez zewnętrzne IP
- precyzyjne przesyłanie plików , wraz z przesyłaniem formatu i rozmiaru pliku
- mute/unmute mikrofonu przy swojej ikonie na userPanel
- opcja zapisu utworzonej tablicy do pliku oraz automatyczne przesłanie go do pozostałych użytkowników
- robienie zrzutu ekranu (podobnie jak opcja wyżej)

OPERUJEMY NIE NA BAZIE DANYCH TYLKO NA SOCKETACH!!!

 */

public class UI extends JFrame
{
    JFrame mainFrame;
    JPanel chatPanel, usersPanel;
    JTextArea messeageArea;
    JButton settingsButton, sendButton, sendFileButton;
    JLabel addressLabel, authCodeLabel;
    JScrollPane scrollChatPanel, scrollUserPanel;
    JButton clearBtn, textButton, colorButton, rubberButton, sendInviteButton;
    DrawArea drawArea;
    JFileChooser chooser;
    String nickname;
    private TargetDataLine mic;
    public boolean isMicAvalibleForUser = false;
    String ipAddress, authCode;
    JLabel newUser, newUser1, newUser2,newUser3, newUser4,newUser5, newUser6,newUser7, newUser8,newUser9, newUser10,newUser11, newUser12,newUser13, newUser14;
    Date date = new Date();

    Icon micIcon = new ImageIcon("src/res/mic-resized.png");
    Icon noMicIcon = new ImageIcon("src/res/no-mic-resized.gif");
    FileSender fileSender;



    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public String getAuthCode()
    {
        return authCode;
    }

    public void setAuthCode(String authCode)
    {
        this.authCode = authCode;
    }

    UI(String ipAddress, String authCode, String nickname, int choose)
    {

        /*
        AudioFormat af = SoundPacket.defaultFormat;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, null);
        mic = (TargetDataLine) (AudioSystem.getLine(info));
        mic.open(af);
        mic.start();
        */
        setIpAddress(ipAddress);
        setAuthCode(authCode);
        this.nickname = nickname;
        clientUI();

        fileSender = new FileSender();

        if(isMicAvalibleForUser==true)
        {
            newUser = new JLabel("Użytkownik: " + nickname + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", micIcon, JLabel.LEFT);
        }
        else
        {
            newUser = new JLabel("Użytkownik: " + nickname + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", noMicIcon, JLabel.LEFT);
        }
        usersPanel.add(newUser);
        //addStaticUsers();

       if(choose==1)
       {
           System.out.println("CLIENT");
       }
       else if(choose==2)
       {
           System.out.println("ADMIN");
           sendInviteButton.setVisible(true);
           authCodeLabel.setVisible(true);
           addressLabel.setVisible(true);
       }
       else
           System.out.println("ERROR");
    }

    void clientUI()
    {
        mainFrame = new JFrame("Workbench");
        mainFrame.setSize(Resources.screenSize.width,Resources.screenSize.height-60);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);
        mainFrame.setVisible(true);
        mainFrame.setLayout(null);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
        mainFrame.validate();

        drawArea = new DrawArea();

        clearBtn = new JButton("Wyczyść");
        clearBtn.addActionListener(actionListener);
        textButton = new JButton("T");
        textButton.addActionListener(actionListener);
        rubberButton = new JButton("Gumka");
        rubberButton.addActionListener(actionListener);
        settingsButton = new JButton("Ustawienia");
        settingsButton.addActionListener(actionListener);

        sendInviteButton = new JButton("Zaproś do czatu");
        sendInviteButton.setVisible(false);
        sendInviteButton.addActionListener(actionListener);

        addressLabel = new JLabel(getIpAddress());
        addressLabel.setVisible(false);
        mainFrame.add(addressLabel);

        authCodeLabel = new JLabel("Twój kod autoryzacyjny: "+getAuthCode());
        authCodeLabel.setVisible(false);
        mainFrame.add(authCodeLabel);

        colorButton = new JButton();
        colorButton.setToolTipText("Zmień kolor czcionki");
        colorButton.setBackground(Color.black);
        colorButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                drawArea.setColor(mainFrame);
                colorButton.setBackground(drawArea.color);
            }
        });
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel,BoxLayout.Y_AXIS));
        mainFrame.add(chatPanel);

        messeageArea = new JTextArea();
        messeageArea.setEditable(true);
        messeageArea.setLineWrap(true);
        messeageArea.setBackground(Color.white);
        messeageArea.setVisible(true);

        scrollChatPanel = new JScrollPane(chatPanel);
        scrollChatPanel.setVisible(true);
        scrollChatPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollChatPanel.setViewportView(chatPanel);

        sendButton = new JButton("Wyślij");
        sendButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!messeageArea.getText().isEmpty())
                {
/*                    BufferedImage image = null;
                    try
                    {
                        image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                        ImageIO.write(image, "png", new File("screenshot.png"));
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }*/

                    String messeage = nickname+" : "+messeageArea.getText()+"\n";
                    System.out.println(messeage+messeage);
                    JLabel mess = new JLabel(messeage);
                    chatPanel.add(mess);
                    chatPanel.validate();
                    chatPanel.repaint();
                    messeageArea.setText("");
                    mainFrame.validate();
                    mainFrame.repaint();
                }
            }
        });

        sendFileButton = new JButton();
        sendFileButton = new JButton("Wyślij plik");
        sendFileButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                chooser = new JFileChooser();
                chooser.setApproveButtonText("Wyślij");
                chooser.setDragEnabled(true);
                chooser.setBackground(Color.LIGHT_GRAY);
                chooser.setDialogTitle("Wybierz plik");
                chooser.repaint();
                if (chooser.showSaveDialog(mainFrame) == JFileChooser.APPROVE_OPTION)
                {
                    File choosedFile = chooser.getSelectedFile();
                    try
                    {
                        fileSender.sendFile(choosedFile);
                    }
                    catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }
                    finally
                    {
                        if (fileSender.isSent)
                        {
                            String messeage = "Użytkownik " + nickname + " wysłał plik "+choosedFile.getName();
                            JLabel mess = new JLabel(messeage);
                            mess.setForeground(Color.BLUE.darker());
                            mess.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            mess.addMouseListener(new MouseAdapter() {

                                                      @Override
                                                      public void mouseClicked(MouseEvent e)
                                                      {
                                                          try
                                                          {

                                                          Desktop.getDesktop().browse(new URI("http://www.codejava.net"));

                                                      }
                                                      catch (IOException | URISyntaxException e1)
                                                      {
                                                          e1.printStackTrace();
                                                      }
                                                      }
                                                  });
                            chatPanel.add(mess);
                            chatPanel.repaint();
                            chatPanel.validate();
                            chatPanel.invalidate();
                            mainFrame.repaint();
                            mainFrame.validate();
                            mainFrame.invalidate();
                        }
                    }
                }
            }
        });

        usersPanel = new JPanel();
        usersPanel.setBackground(Color.white);
        usersPanel.setLayout(new BoxLayout(usersPanel,BoxLayout.Y_AXIS));

        mainFrame.add(usersPanel);

        scrollUserPanel = new JScrollPane();
        scrollUserPanel.setVisible(true);
        scrollUserPanel.setViewportView(usersPanel);


        resizePane(chatPanel,usersPanel,drawArea,messeageArea, scrollUserPanel, scrollChatPanel,textButton,colorButton,clearBtn,rubberButton,settingsButton,sendButton,sendFileButton, sendInviteButton, authCodeLabel,addressLabel);

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
            else if(e.getSource() == textButton)
            {
                drawArea.text();
            }
            else if(e.getSource() == rubberButton)
            {
                drawArea.rubber();
            }
            else if(e.getSource() == settingsButton)
            {
                SettingFrame settings = new SettingFrame();
                if(settings.getMicAvalibility())
                {
                    setMicAvalible(true);
                    newUser.setText("Użytkownik: " + nickname + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")");
                    newUser.setIcon(micIcon);
                    mainFrame.repaint();
                }
                System.out.println(isMicAvalibleForUser);
            }
            else if(e.getSource() == sendInviteButton)
            {
                String invite = "Adres serwera: "+getIpAddress()+" | Kod dostępu do sesji: "+getAuthCode();
                StringSelection stringSelection = new StringSelection(invite);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                JOptionPane.showMessageDialog(mainFrame,"Utworzono i skopiowano do schowka twoje zaproszenie.\n"+invite,"Zaproś",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    };

    void addStaticUsers()
    {
        newUser1 = new JLabel("Użytkownik: " + "Edek2112" + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", micIcon, JLabel.LEFT);
        newUser2 = new JLabel("Użytkownik: " + "twojastara13" + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", micIcon, JLabel.LEFT);
        newUser3 = new JLabel("Użytkownik: " + "hejnał222222" + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", noMicIcon, JLabel.LEFT);
        newUser4 = new JLabel("Użytkownik: " + "Patointeligencja" + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", micIcon, JLabel.LEFT);
        newUser5 = new JLabel("Użytkownik: " + "nexos" + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", micIcon, JLabel.LEFT);
        newUser6 = new JLabel("Użytkownik: " + "RockAlone" + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", noMicIcon, JLabel.LEFT);
        newUser7 = new JLabel("Użytkownik: " + "ROJOV13" + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", micIcon, JLabel.LEFT);
        newUser8 = new JLabel("Użytkownik: " + "sebixus" + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", micIcon, JLabel.LEFT);
        newUser9 = new JLabel("Użytkownik: " + "Tomuś Szwagruś" + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", noMicIcon, JLabel.LEFT);
        newUser10 = new JLabel("Użytkownik: " + "Ziemniak" + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", micIcon, JLabel.LEFT);
        newUser11 = new JLabel("Użytkownik: " + "Adamek98" + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", micIcon, JLabel.LEFT);
        newUser12 = new JLabel("Użytkownik: " + "Pobidyński sprawko" + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", noMicIcon, JLabel.LEFT);
        newUser13 = new JLabel("Użytkownik: " + "HeniekProducentKredek" + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", noMicIcon, JLabel.LEFT);
        newUser14 = new JLabel("Użytkownik: " + "SiwyZiomal" + " (godzina połączenia z sesją: " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ")", micIcon, JLabel.LEFT);

        usersPanel.add(newUser1);
        usersPanel.add(newUser2);
        usersPanel.add(newUser3);
        usersPanel.add(newUser4);
        usersPanel.add(newUser5);
        usersPanel.add(newUser6);
        usersPanel.add(newUser7);
        usersPanel.add(newUser8);
        usersPanel.add(newUser9);
        usersPanel.add(newUser10);
        usersPanel.add(newUser11);
        usersPanel.add(newUser12);
        usersPanel.add(newUser13);
        usersPanel.add(newUser14);
    }
    void resizePane(JPanel chatPanel, JPanel usersPanel, DrawArea drawArea, JTextArea messeageArea, JScrollPane scrollUserPanel, JScrollPane scrollChatPanel, JButton textButton,JButton colorButton,
                    JButton clearBtn,JButton rubberButton, JButton settingsButton,JButton sendButton,
                    JButton sendFileButton, JButton sendInviteButton, JLabel authCodeLabel, JLabel addressLabel)
    {
        double width = Resources.screenSize.getWidth();
        double height = Resources.screenSize.getHeight();
        System.out.println(width+" x "+height);

        if(width==1366.0 && height==768.0)
        {
            textButton.setBounds(20,620,100,30);
            colorButton.setBounds(130,620,30,30);
            clearBtn.setBounds(180,620,100,30);
            rubberButton.setBounds(290,620,100,30);
            settingsButton.setBounds(400,620,100,30);
            sendInviteButton.setBounds(810,600,150,40);
            sendFileButton.setBounds(1120,490,100,35);
            sendButton.setBounds(1000,490,100,35);

            addressLabel.setBounds(600,620,200,20);
            authCodeLabel.setBounds(600,600,200,20);

            chatPanel.setBounds(1000,20,320,390);
            chatPanel.setSize(320,390);
            chatPanel.setMinimumSize(new Dimension(315, 390));
            chatPanel.setMaximumSize(new Dimension(325, 390));

            scrollChatPanel.setBounds(1000,20,320,390);
            scrollChatPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollChatPanel.setViewportView(chatPanel);

            messeageArea.setBounds(1000,420,320,50);
            messeageArea.setMinimumSize(new Dimension(315, 50));
            messeageArea.setMaximumSize(new Dimension(325, 50));

            usersPanel.setMinimumSize(new Dimension(315, 140));
            usersPanel.setMaximumSize(new Dimension(325, 140));
            usersPanel.setBounds(1000,550,320,400);
            usersPanel.setSize(320,400);

            scrollUserPanel.setBounds(1000,550,320,140);
            scrollUserPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollUserPanel.setViewportView(usersPanel);

            drawArea.setPreferredSize(new Dimension(910,570));
            drawArea.setBounds(20,20,910,570);
            drawArea.setMinimumSize(new Dimension(905, 570));
            drawArea.setMaximumSize(new Dimension(915, 570));
        }
        else if(width==1600.0 && height==900.0)
        {
            textButton.setBounds(10,750,60,30);
            colorButton.setBounds(90,750,30,30);
            clearBtn.setBounds(140,750,60,30);
            rubberButton.setBounds(220,750,60,30);
            settingsButton.setBounds(300,750,60,30);

            chatPanel.setPreferredSize(new Dimension(375, 460));
            chatPanel.setMinimumSize(new Dimension(370, 460));
            chatPanel.setMaximumSize(new Dimension(380, 460));

            usersPanel.setPreferredSize(new Dimension(375, 170));
            usersPanel.setMinimumSize(new Dimension(370, 170));
            usersPanel.setMaximumSize(new Dimension(380, 170));

            drawArea.setPreferredSize(new Dimension(1060,670));
            drawArea.setMinimumSize(new Dimension(1055, 670));
            drawArea.setMaximumSize(new Dimension(1065, 670));
        }
        else if(width==1920.0 && height==1080.0)
        {
            textButton.setBounds(10,900,75,40);
            colorButton.setBounds(100,900,40,40);
            clearBtn.setBounds(180,900,75,40);
            rubberButton.setBounds(270,900,75,40);
            settingsButton.setBounds(355,900,75,40);

            chatPanel.setPreferredSize(new Dimension(385, 550));

            chatPanel.setMinimumSize(new Dimension(380, 550));
            chatPanel.setMaximumSize(new Dimension(390, 550));

            usersPanel.setPreferredSize(new Dimension(450, 200));
            usersPanel.setBounds(1000,50,450,200);
            usersPanel.setMinimumSize(new Dimension(445, 200));
            usersPanel.setMaximumSize(new Dimension(455, 200));

            sendFileButton.setBounds(2050,700,100,50);
            sendButton.setBounds(1900,700,100,50);

            scrollChatPanel.setBounds(1000,50,450,550);
            chatPanel.setBounds(1000,50,450,550);
            chatPanel.setSize(450,550);

            scrollUserPanel.setBounds(1000,790,450,200);
            usersPanel.setBounds(1000,790,450,200);
            usersPanel.setSize(450,200);

            drawArea.setPreferredSize(new Dimension(720,800));
            drawArea.setMinimumSize(new Dimension(715, 800));
            drawArea.setMaximumSize(new Dimension(725, 800));
        }

        else if(width==2560.0 && height==1080.0)
        {
            textButton.setBounds(50,900,100,40);
            colorButton.setBounds(160,900,40,40);
            clearBtn.setBounds(210,900,100,40);
            rubberButton.setBounds(320,900,100,40);
            settingsButton.setBounds(430,900,100,40);
            sendFileButton.setBounds(2050,700,100,50);
            sendButton.setBounds(1900,700,100,50);
            sendInviteButton.setBounds(1540,950,160,40);

            authCodeLabel.setBounds(1540,910,200,30);
            addressLabel.setBounds(1540,880,160,30);

            chatPanel.setMinimumSize(new Dimension(595, 550));
            chatPanel.setMaximumSize(new Dimension(605, 550));
            chatPanel.setBounds(1900,50,600,550);
            chatPanel.setSize(600,550);

            scrollChatPanel.setBounds(1900,50,600,550);
            scrollChatPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollChatPanel.setViewportView(chatPanel);

            messeageArea.setBounds(1900,625,600,50);
            messeageArea.setMinimumSize(new Dimension(595, 50));
            messeageArea.setMaximumSize(new Dimension(605, 50));

            usersPanel.setBounds(1900,790,600,200);
            usersPanel.setSize(600,200);
            usersPanel.setMinimumSize(new Dimension(595, 200));
            usersPanel.setMaximumSize(new Dimension(605, 200));

            scrollUserPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollUserPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scrollUserPanel.setBounds(1900,790,600,200);
            scrollUserPanel.setMinimumSize(new Dimension(595,200));
            scrollUserPanel.setMaximumSize(new Dimension(605,200));

            drawArea.setBounds(50,50,1700,800);
            drawArea.setPreferredSize(new Dimension(1700,800));
            drawArea.setMinimumSize(new Dimension(1690, 800));
            drawArea.setMaximumSize(new Dimension(1710, 800));
        }
    }

    public void setMicAvalible(boolean state)
    {
        isMicAvalibleForUser=state;
    }
}
