package UserInterfaces;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.StringTokenizer;

public class ConfigurationUI extends JFrame
{
    JFrame typeActionFrame;
    JLabel infoLabel, ipAdressLabel, codeAreaLabel;
    JCheckBox adminCheckBox, clientCheckBox;
    JTextArea codeArea;
    JFormattedTextField ipAdressArea;
    JButton startButton;
    boolean isAdmin;
    String internalIp;
    Border border = BorderFactory.createLineBorder(Color.BLACK);

    public ConfigurationUI() throws IOException
    {
        createUI();
        InetAddress inet = InetAddress.getLocalHost();
        internalIp = inet.getHostAddress();
    }

    void createUI()
    {
        typeActionFrame = new JFrame("Skonfiguruj sesję");
        typeActionFrame.setSize(300,350);
        typeActionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        typeActionFrame.setResizable(false);
        typeActionFrame.setVisible(true);
        typeActionFrame.setLayout(null);
        typeActionFrame.setLocationRelativeTo(null);
        typeActionFrame.getContentPane().setBackground(new Color(222,240,252));
        typeActionFrame.validate();
        typeActionFrame.setIconImage(new ImageIcon(getClass().getResource("/Resources/app_icon.png")).getImage());

        infoLabel = new JLabel("Tryb programu");
        infoLabel.setBounds(90,15,150,30);
        infoLabel.setVisible(true);
        typeActionFrame.add(infoLabel);

        ipAdressLabel = new JLabel("Adres IP serwera");
        ipAdressLabel.setBounds(10,100,100,40);
        ipAdressLabel.setVisible(true);
        ipAdressLabel.setBackground(Color.white);
        typeActionFrame.add(ipAdressLabel);

        ipAdressArea = new JFormattedTextField();
        ipAdressArea.setEditable(true);
        ipAdressArea.setInputVerifier(new IPTextFieldVerifier());
        ipAdressArea.setBackground(Color.white);
        ipAdressArea.setText(internalIp);
        ipAdressArea.setBounds(140,100,120,40);
        ipAdressArea.setVisible(true);
        ipAdressArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        typeActionFrame.add(ipAdressArea);

        codeAreaLabel = new JLabel("Kod dostępu:");
        codeAreaLabel.setBounds(10,160,120,40);
        codeAreaLabel.setVisible(true);
        codeAreaLabel.setBackground(Color.white);
        typeActionFrame.add(codeAreaLabel);

        codeArea = new JTextArea();
        codeArea.setEditable(true);
        codeArea.setBounds(140,160,120,40);
        codeArea.setVisible(true);
        codeArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        codeArea.setBackground(Color.white);
        typeActionFrame.add(codeArea);


        startButton = new JButton("Uruchom");
        startButton.setVisible(true);
        startButton.setBackground(Color.white);
        startButton.setBounds(75,240,150,40);
        startButton.setBorder(new RoundedBorder(30));
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.setOpaque(false);
        typeActionFrame.add(startButton);
        startButton.addActionListener(actionListener);

        clientCheckBox = new JCheckBox("Klient");
        clientCheckBox.setEnabled(true);
        clientCheckBox.setSelected(false);
        clientCheckBox.setBackground(new Color(222,240,252));
        clientCheckBox.setVisible(true);
        clientCheckBox.setBounds(190,50,150,30);
        typeActionFrame.add(clientCheckBox);
        clientCheckBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(clientCheckBox.isSelected())
                {
                    adminCheckBox.setEnabled(false);
                    isAdmin=false;
                    ipAdressArea.setText("");
                    ipAdressLabel.setText("Podaj IP serwera");
                    ipAdressArea.setEditable(true);
                    startButton.setText("Połącz");
                    codeAreaLabel.setText("Kod dostępu");
                }
                else
                    adminCheckBox.setEnabled(true);
            }
        });

        adminCheckBox = new JCheckBox("Administrator");
        adminCheckBox.setEnabled(true);
        adminCheckBox.setSelected(false);
        adminCheckBox.setVisible(true);
        adminCheckBox.setBackground(new Color(222,240,252));
        adminCheckBox.setBounds(10,50,150,30);
        typeActionFrame.add(adminCheckBox);
        adminCheckBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(adminCheckBox.isSelected())
                {
                    adminCheckBox.setEnabled(true);
                    clientCheckBox.setEnabled(false);
                    isAdmin=true;
                    ipAdressArea.setEditable(false);
                    ipAdressArea.setText(internalIp);
                    startButton.setText("Uruchom sesję");
                    codeAreaLabel.setText("Ustaw kod dostępu");
                }
                else
                    clientCheckBox.setEnabled(true);
            }
        });
        typeActionFrame.repaint();
    }

    ActionListener actionListener = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == startButton)
            {
                if(clientCheckBox.isSelected()&&!adminCheckBox.isSelected())
                {
                        if(!codeArea.getText().isEmpty())
                        {
                            if(!ipAdressArea.getText().equals("000.000.000.000"))
                            {
                                try
                                {
                                    ClientUI client = new ClientUI(ipAdressArea.getText());
                                    typeActionFrame.dispose();
                                }
                                catch (IOException e1)
                                {
                                    e1.printStackTrace();
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(typeActionFrame,"Błąd! Niepoprawny adres IP!","Błąd",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(typeActionFrame,"Błąd! Puste pola!","Błąd",JOptionPane.ERROR_MESSAGE);
                        }
                }
                else if(adminCheckBox.isSelected()&&!clientCheckBox.isSelected())
                {
                    if(!codeArea.getText().isEmpty())
                    {
                        if(!ipAdressArea.getText().equals("000.000.000.000"))
                        {
                            try
                            {
                                AdministratorUI admin = new AdministratorUI(ipAdressArea.getText(), codeArea.getText(), 9876);
                                typeActionFrame.dispose();
                            }
                            catch (IOException e1)
                            {
                                e1.printStackTrace();
                            }

                        }
                        else
                        {
                            JOptionPane.showMessageDialog(typeActionFrame,"Błąd! Niepoprawny adres IP!","Błąd",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(typeActionFrame,"Błąd! Puste pola!","Błąd",JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                    JOptionPane.showMessageDialog(typeActionFrame,"Pola są niewypełnione!","Błąd",JOptionPane.ERROR_MESSAGE);
            }
        }
    };
}
class IPTextFieldVerifier extends InputVerifier
{
    public boolean verify(JComponent input)
    {
        if (input instanceof JFormattedTextField)
        {
            JFormattedTextField ftf = (JFormattedTextField) input;
            JFormattedTextField.AbstractFormatter formatter = ftf.getFormatter();
            if (formatter != null)
            {
                String text = ftf.getText();
                StringTokenizer st = new StringTokenizer(text, ".");
                while (st.hasMoreTokens())
                {
                    int value = Integer.parseInt((String) st.nextToken());
                    if (value < 0 || value > 255)
                    {
                        input.setInputVerifier(null);
                        JOptionPane.showMessageDialog(new Frame(), "Niepoprawny adres IP", "Błąd!", JOptionPane.ERROR_MESSAGE);
                        input.setInputVerifier(this);
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }
}
class RoundedBorder implements Border
{
    private int radius;

    RoundedBorder(int radius)
    {
        this.radius = radius;
    }

    public Insets getBorderInsets(Component c)
    {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    public boolean isBorderOpaque()
    {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
    {
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}
