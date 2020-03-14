package UserInterfaces;

import DrawArea.DrawArea;
import UtilitiesPackage.UITools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientTools extends JFrame
{
    JFrame toolsFrame;
    private JButton clearBtn, textButton, colorButton, rubberButton, reciveFileButton;
    private DrawArea drawAreaPointer;


    ClientTools(DrawArea drawAreaPointer)
    {
        this.drawAreaPointer = drawAreaPointer;
        createUI();
    }

    void createUI()
    {
        toolsFrame = new JFrame("Przybornik");
        toolsFrame.setSize(540, 150);
        toolsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        toolsFrame.setResizable(true);
        toolsFrame.setVisible(true);
        toolsFrame.setLayout(null);
        toolsFrame.setAlwaysOnTop(true);
        toolsFrame.setLocationRelativeTo(null);
        toolsFrame.getContentPane().setBackground(new Color(222,240,252));
        toolsFrame.validate();

        clearBtn = new JButton("Wyczyść");
        clearBtn.addActionListener(actionListener);
        clearBtn.setBounds(20,30,150,50);
        clearBtn.setBorder(new RoundedBorder(30));
        clearBtn.setFocusPainted(false);
        clearBtn.setOpaque(false);
        clearBtn.setForeground(Color.BLACK);
        clearBtn.setBackground(Color.white);

        textButton = new JButton("T");
        textButton.addActionListener(actionListener);
        textButton.setBorder(new RoundedBorder(30));
        textButton.setFocusPainted(false);
        textButton.setBounds(180,30,100,50);
        textButton.setOpaque(false);
        textButton.setForeground(Color.BLACK);
        textButton.setBackground(Color.white);

        rubberButton = new JButton("Gumka");
        rubberButton.addActionListener(actionListener);
        rubberButton.setBorder(new RoundedBorder(30));
        rubberButton.setFocusPainted(false);
        rubberButton.setBounds(290,30,150,50);
        rubberButton.setForeground(Color.BLACK);
        rubberButton.setBackground(Color.white);
        rubberButton.setOpaque(false);

        colorButton = new JButton();
        colorButton.setToolTipText("Zmień kolor czcionki");
        colorButton.setBounds(450,30,50,50);
        colorButton.setBackground(Color.black);
        colorButton.addActionListener(actionListener);

        toolsFrame.add(colorButton);
        toolsFrame.add(textButton);
        toolsFrame.add(clearBtn);
        toolsFrame.add(rubberButton);
    }

    ActionListener actionListener = new ActionListener()
    {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearBtn)
            {
                drawAreaPointer.clear();
            }
            else if (e.getSource() == textButton)
            {
                drawAreaPointer.text();
            }
            else if (e.getSource() == rubberButton)
            {
                drawAreaPointer.rubber();
            }
            else if (e.getSource() == colorButton)
            {
                drawAreaPointer.setColor(toolsFrame);
                colorButton.setBackground(drawAreaPointer.getColor());
            }
        }
    };
}
