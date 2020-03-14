package UtilitiesPackage;

import javax.swing.*;
import java.awt.*;
import DrawArea.DrawArea;

public class UITools
{
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public void resizeAdminUI(JPanel chatPanel, JPanel usersPanel, JPanel drawArea, JTextArea messeageArea, JScrollPane scrollUserPanel, JScrollPane scrollChatPanel, JButton textButton, JButton colorButton,
                    JButton clearBtn, JButton rubberButton, JButton settingsButton, JButton sendButton,
                    JButton sendFileButton, JButton sendInviteButton, JLabel authCodeLabel, JLabel addressLabel, JButton reciveFileButton)
    {
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        System.out.println(width+" x "+height);

        if(width==1366.0 && height==768.0)
        {
            textButton.setBounds(20,620,100,30);
            colorButton.setBounds(130,620,30,30);
            clearBtn.setBounds(180,620,100,30);
            rubberButton.setBounds(290,620,100,30);
            settingsButton.setBounds(400,620,100,30);
            sendInviteButton.setBounds(810,600,150,40);
            reciveFileButton.setBounds(1230,490,130,35);
            sendFileButton.setBounds(1100,490,120,35);
            sendButton.setBounds(990,490,100,35);

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
            textButton.setBounds(50,900,70,40);
            colorButton.setBounds(135,900,40,40);
            clearBtn.setBounds(190,900,120,40);
            rubberButton.setBounds(320,900,120,40);
            settingsButton.setBounds(450,900,150,40);
            reciveFileButton.setBounds(2200,700,130,50);
            sendFileButton.setBounds(2020,700,160,50);
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

    public void resizeClientUI(JPanel chatPanel, JPanel usersPanel, JPanel drawArea, JTextArea messeageArea, JScrollPane scrollUserPanel, JScrollPane scrollChatPanel, JButton settingsButton, JButton sendButton,
                       JButton sendFileButton,JButton openToolsButton,JButton reciveFileButton)
    {
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        System.out.println(width+" x "+height);

        if(width==1366.0 && height==768.0)
        {
            settingsButton.setBounds(50,620,100,30);
            openToolsButton.setBounds(160,620,100,30);
            reciveFileButton.setBounds(1230,490,130,35);
            sendFileButton.setBounds(1100,490,120,35);
            sendButton.setBounds(990,490,100,35);

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
            settingsButton.setBounds(50,750,60,30);
            openToolsButton.setBounds(120,750,100,30);

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
            settingsButton.setBounds(50,900,75,40);
            openToolsButton.setBounds(130,900,100,30);

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
            settingsButton.setBounds(50,900,150,40);
            openToolsButton.setBounds(210,900,150,40);
            sendFileButton.setBounds(2020,700,160,50);
            sendButton.setBounds(1900,700,100,50);
            reciveFileButton.setBounds(2200,700,130,50);

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
}