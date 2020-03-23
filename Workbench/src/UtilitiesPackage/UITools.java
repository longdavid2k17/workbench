package UtilitiesPackage;

import javax.swing.*;
import java.awt.*;

public class UITools
{
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();

    public void resizeAdminUI(JPanel chatPanel, JPanel usersPanel, JPanel drawArea, JTextArea messeageArea, JScrollPane scrollUserPanel, JScrollPane scrollChatPanel, JButton textButton, JButton colorButton,
                    JButton clearBtn, JButton rubberButton, JButton settingsButton, JButton sendButton,
                    JButton sendFileButton, JButton sendInviteButton, JLabel authCodeLabel, JLabel addressLabel, JButton reciveFileButton)
    {
        System.out.println(width+" x "+height);

        if(width==1366.0 && height==768.0)
        {
            textButton.setBounds(20,620,120,30);
            colorButton.setBounds(150,620,30,30);
            clearBtn.setBounds(190,620,120,30);
            rubberButton.setBounds(320,620,120,30);
            settingsButton.setBounds(450,620,140,30);
            sendInviteButton.setBounds(810,650,165,40);

            reciveFileButton.setBounds(1230,490,130,35);
            sendFileButton.setBounds(1100,490,120,35);
            sendButton.setBounds(990,490,100,35);

            addressLabel.setBounds(600,670,200,20);
            authCodeLabel.setBounds(600,650,200,20);

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
        else if(width==1680.0 && height==1050.0)
        {
            textButton.setBounds(10,900,75,40);
            colorButton.setBounds(100,900,40,40);
            clearBtn.setBounds(170,900,120,40);
            rubberButton.setBounds(300,900,120,40);
            settingsButton.setBounds(430,900,150,40);
            sendFileButton.setBounds(1225,700,120,50);
            sendButton.setBounds(1100,700,100,50);
            reciveFileButton.setBounds(1385,700,130,50);

            sendInviteButton.setBounds(900,900,160,40);
            authCodeLabel.setBounds(700,900,200,30);
            addressLabel.setBounds(700,920,160,30);

            chatPanel.setPreferredSize(new Dimension(385, 600));
            chatPanel.setMinimumSize(new Dimension(380, 600));
            chatPanel.setMaximumSize(new Dimension(390, 600));
            chatPanel.setBounds(1100,50,500,600);
            chatPanel.setSize(500,550);
            scrollChatPanel.setBounds(1100,50,500,600);

            usersPanel.setPreferredSize(new Dimension(450, 200));
            usersPanel.setMinimumSize(new Dimension(445, 200));
            usersPanel.setMaximumSize(new Dimension(455, 200));
            usersPanel.setBounds(1100,790,500,200);
            usersPanel.setSize(500,200);
            scrollUserPanel.setBounds(1100,790,500,200);

            drawArea.setBounds(50,50,1000,800);
            drawArea.setPreferredSize(new Dimension(1000,800));
            drawArea.setMinimumSize(new Dimension(995, 800));
            drawArea.setMaximumSize(new Dimension(1005, 800));
        }
        else if(width==1920.0 && height==1080.0)
        {
            textButton.setBounds(10,900,75,40);
            colorButton.setBounds(100,900,40,40);
            clearBtn.setBounds(170,900,120,40);
            rubberButton.setBounds(300,900,120,40);
            settingsButton.setBounds(430,900,150,40);
            sendFileButton.setBounds(1535,700,120,50);
            sendButton.setBounds(1400,700,100,50);
            reciveFileButton.setBounds(1700,700,130,50);

            sendInviteButton.setBounds(1200,900,160,40);
            authCodeLabel.setBounds(1000,900,200,30);
            addressLabel.setBounds(1000,920,160,30);

            chatPanel.setPreferredSize(new Dimension(385, 600));
            chatPanel.setMinimumSize(new Dimension(380, 600));
            chatPanel.setMaximumSize(new Dimension(390, 600));
            chatPanel.setBounds(1400,50,450,600);
            chatPanel.setSize(450,550);
            scrollChatPanel.setBounds(1400,50,450,600);

            usersPanel.setPreferredSize(new Dimension(450, 200));
            usersPanel.setMinimumSize(new Dimension(445, 200));
            usersPanel.setMaximumSize(new Dimension(455, 200));
            usersPanel.setBounds(1400,790,450,200);
            usersPanel.setSize(450,200);
            scrollUserPanel.setBounds(1400,790,450,200);

            drawArea.setBounds(50,50,1300,800);
            drawArea.setPreferredSize(new Dimension(1300,800));
            drawArea.setMinimumSize(new Dimension(1295, 800));
            drawArea.setMaximumSize(new Dimension(1305, 800));
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

    public void resizeClientUI(JPanel chatPanel, JPanel drawArea, JTextArea messeageArea, JScrollPane scrollChatPanel, JButton settingsButton, JButton sendButton,
                       JButton sendFileButton,JButton reciveFileButton)
    {
        System.out.println(width+" x "+height);

        if(width==1366.0 && height==768.0)
        {
            settingsButton.setBounds(50,600,130,30);
            reciveFileButton.setBounds(1220,650,130,35);
            sendFileButton.setBounds(1090,650,120,35);
            sendButton.setBounds(980,650,100,35);

            chatPanel.setBounds(1000,20,320,540);
            chatPanel.setSize(320,540);
            chatPanel.setMinimumSize(new Dimension(315, 540));
            chatPanel.setMaximumSize(new Dimension(325, 540));

            scrollChatPanel.setBounds(1000,20,320,540);
            scrollChatPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollChatPanel.setViewportView(chatPanel);

            messeageArea.setBounds(1000,590,320,50);
            messeageArea.setMinimumSize(new Dimension(315, 50));
            messeageArea.setMaximumSize(new Dimension(325, 50));

            drawArea.setPreferredSize(new Dimension(910,570));
            drawArea.setBounds(20,20,910,570);
            drawArea.setMinimumSize(new Dimension(905, 570));
            drawArea.setMaximumSize(new Dimension(915, 570));
        }
        else if(width==1600.0 && height==900.0)
        {
            settingsButton.setBounds(50,750,60,30);
            sendFileButton.setBounds(1225,700,120,50);
            sendButton.setBounds(1100,700,100,50);
            reciveFileButton.setBounds(1385,700,130,50);

            chatPanel.setPreferredSize(new Dimension(385, 600));
            chatPanel.setMinimumSize(new Dimension(380, 600));
            chatPanel.setMaximumSize(new Dimension(390, 600));
            chatPanel.setBounds(1100,50,500,600);
            chatPanel.setSize(500,550);
            scrollChatPanel.setBounds(1100,50,500,600);

            drawArea.setBounds(50,50,1000,800);
            drawArea.setPreferredSize(new Dimension(1000,800));
            drawArea.setMinimumSize(new Dimension(995, 800));
            drawArea.setMaximumSize(new Dimension(1005, 800));
        }
        else if(width==1680.0 && height==1050.0)
        {
            settingsButton.setBounds(50,900,150,40);
            sendFileButton.setBounds(1265,900,120,50);
            sendButton.setBounds(1140,900,100,50);
            reciveFileButton.setBounds(1425,900,130,50);

            chatPanel.setMinimumSize(new Dimension(380, 700));
            chatPanel.setMaximumSize(new Dimension(390, 700));
            chatPanel.setBounds(1100,50,500,700);
            chatPanel.setSize(500,700);

            scrollChatPanel.setBounds(1100,50,500,700);
            scrollChatPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollChatPanel.setViewportView(chatPanel);

            messeageArea.setBounds(1100,800,500,50);
            messeageArea.setMinimumSize(new Dimension(500, 50));
            messeageArea.setMaximumSize(new Dimension(500, 50));

            drawArea.setBounds(50,50,1000,800);
            drawArea.setPreferredSize(new Dimension(1000,800));
            drawArea.setMinimumSize(new Dimension(995, 800));
            drawArea.setMaximumSize(new Dimension(1005, 800));
        }
        else if(width==1920.0 && height==1080.0)
        {
            settingsButton.setBounds(20,900,150,40);
            sendFileButton.setBounds(1535,950,120,50);
            sendButton.setBounds(1400,950,100,50);
            reciveFileButton.setBounds(1700,950,130,50);

            chatPanel.setMinimumSize(new Dimension(380, 800));
            chatPanel.setMaximumSize(new Dimension(390, 800));
            chatPanel.setBounds(1400,50,450,800);
            chatPanel.setSize(450,800);

            scrollChatPanel.setBounds(1400,50,450,800);
            scrollChatPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollChatPanel.setViewportView(chatPanel);

            messeageArea.setBounds(1400,870,600,50);
            messeageArea.setMinimumSize(new Dimension(595, 50));
            messeageArea.setMaximumSize(new Dimension(605, 50));

            drawArea.setBounds(50,50,1300,800);
            drawArea.setPreferredSize(new Dimension(720,800));
            drawArea.setMinimumSize(new Dimension(715, 800));
            drawArea.setMaximumSize(new Dimension(725, 800));
        }

        else if(width==2560.0 && height==1080.0)
        {
            settingsButton.setBounds(50,900,150,40);
            sendFileButton.setBounds(2020,950,160,50);
            sendButton.setBounds(1900,950,100,50);
            reciveFileButton.setBounds(2200,950,130,50);

            chatPanel.setMinimumSize(new Dimension(595, 800));
            chatPanel.setMaximumSize(new Dimension(605, 800));
            chatPanel.setBounds(1900,50,600,800);
            chatPanel.setSize(600,800);

            scrollChatPanel.setBounds(1900,50,600,800);
            scrollChatPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollChatPanel.setViewportView(chatPanel);

            messeageArea.setBounds(1900,870,600,50);
            messeageArea.setMinimumSize(new Dimension(595, 50));
            messeageArea.setMaximumSize(new Dimension(605, 50));

            drawArea.setBounds(50,50,1700,800);
            drawArea.setPreferredSize(new Dimension(1700,800));
            drawArea.setMinimumSize(new Dimension(1690, 800));
            drawArea.setMaximumSize(new Dimension(1710, 800));
        }
    }
}