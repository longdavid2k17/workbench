import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.StringTokenizer;

public class TypeAction extends JFrame
{
    JFrame typeActionFrame;
    JLabel infoLabel, ipAdressLabel, codeAreaLabel, nicknameLabel, yourIPLbl;
    JCheckBox adminCheckBox, clientCheckBox;
    boolean isAdmin;
    JTextArea codeArea, nicknameArea, yourIPAdressArea;
    JFormattedTextField ipAdressArea;
    JButton startButton;
    String ip;


    TypeAction() throws IOException, ParseException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));

        ip = in.readLine(); //you get the IP as a String
        //System.out.println(ip);
        createUI();
        ipAdressArea.setText(ip);
    }

    void createUI() throws ParseException
    {
        typeActionFrame = new JFrame("Workbench");
        typeActionFrame.setSize(300,450);
        typeActionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        typeActionFrame.setResizable(false);
        typeActionFrame.setVisible(true);
        typeActionFrame.setLayout(null);
        typeActionFrame.setLocationRelativeTo(null);
        typeActionFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
        typeActionFrame.validate();

        infoLabel = new JLabel("Wybierz tryb pracy programu");
        infoLabel.setBounds(30,10,200,30);
        infoLabel.setVisible(true);
        typeActionFrame.add(infoLabel);

        ipAdressLabel = new JLabel("Adres IP serwera");
        ipAdressLabel.setBounds(10,100,100,40);
        ipAdressLabel.setVisible(true);
        ipAdressLabel.setBackground(Color.white);
        typeActionFrame.add(ipAdressLabel);

        MaskFormatter formatter = new MaskFormatter("###.###.###.###");
        formatter.setPlaceholderCharacter('0');

        ipAdressArea = new JFormattedTextField(formatter);
        ipAdressArea.setEditable(true);
        ipAdressArea.setInputVerifier(new IPTextFieldVerifier());
        ipAdressArea.setBackground(Color.white);
        ipAdressArea.setBounds(140,100,120,40);
        ipAdressArea.setVisible(true);
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
        codeArea.setBackground(Color.white);
        typeActionFrame.add(codeArea);

        nicknameLabel = new JLabel("Nickname:");
        nicknameLabel.setBounds(10,220,100,40);
        nicknameLabel.setVisible(true);
        nicknameLabel.setBackground(Color.white);
        typeActionFrame.add(nicknameLabel);

        nicknameArea = new JTextArea();
        nicknameArea.setEditable(true);
        nicknameArea.setBounds(140,220,120,40);
        nicknameArea.setVisible(true);
        nicknameArea.setBackground(Color.white);
        typeActionFrame.add(nicknameArea);

        yourIPLbl = new JLabel("Twój adres IP:");
        yourIPLbl.setBounds(10,270,100,20);
        yourIPLbl.setVisible(false);
        yourIPLbl.setBackground(Color.white);
        typeActionFrame.add(yourIPLbl);

        yourIPAdressArea = new JTextArea();
        yourIPAdressArea.setEditable(false);
        yourIPAdressArea.setText(ip);
        yourIPAdressArea.setBounds(140,270,120,20);
        yourIPAdressArea.setVisible(false);
        yourIPAdressArea.setBackground(Color.white);
        typeActionFrame.add(yourIPAdressArea);

        startButton = new JButton("Uruchom");
        startButton.setVisible(true);
        startButton.setBackground(Color.white);
        startButton.setBounds(75,350,150,40);
        typeActionFrame.add(startButton);
        startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(clientCheckBox.isSelected()&&!adminCheckBox.isSelected())
                {
                    if(!nicknameArea.getText().isEmpty())
                    {
                        if(!codeArea.getText().isEmpty())
                        {
                            if(!ipAdressArea.getText().equals("000.000.000.000"))
                            {
                                try
                                {
                                    new UI(ip, codeArea.getText(), nicknameArea.getText(), 1);
                                }
                                catch (IOException e1)
                                {
                                    e1.printStackTrace();
                                }
                                typeActionFrame.dispose();
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
                        JOptionPane.showMessageDialog(typeActionFrame,"Błąd! Puste pola!","Błąd",JOptionPane.ERROR_MESSAGE);
                }
                else if(adminCheckBox.isSelected()&&!clientCheckBox.isSelected())
                {
                    if(!codeArea.getText().isEmpty())
                    {
                        if(!ipAdressArea.getText().equals("000.000.000.000"))
                        {
                            try
                            {
                                new UI(ipAdressArea.getText(), codeArea.getText(), "Administrator", 2);
                            }
                            catch (IOException e1)
                            {
                                e1.printStackTrace();
                            }
                            typeActionFrame.dispose();
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
                    JOptionPane.showMessageDialog(typeActionFrame,"Pola są niewypełnione","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        });



        clientCheckBox = new JCheckBox("Klient");
        clientCheckBox.setEnabled(true);
        clientCheckBox.setSelected(false);
        clientCheckBox.setBackground(Color.lightGray);
        clientCheckBox.setVisible(true);
        clientCheckBox.setBounds(190,50,150,30);
        typeActionFrame.add(clientCheckBox);
        clientCheckBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clientCheckBox.isSelected())
                {
                    adminCheckBox.setEnabled(false);
                    isAdmin=false;
                    nicknameArea.setText("");
                    ipAdressArea.setText("");
                    ipAdressLabel.setText("Podaj IP serwera");
                    ipAdressArea.setEditable(true);
                    nicknameArea.setEditable(true);
                    startButton.setText("Połącz");
                    yourIPLbl.setVisible(false);
                    yourIPAdressArea.setVisible(false);
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
        adminCheckBox.setBackground(Color.lightGray);
        adminCheckBox.setBounds(10,50,150,30);
        typeActionFrame.add(adminCheckBox);
        adminCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(adminCheckBox.isSelected())
                {
                    adminCheckBox.setEnabled(true);
                    clientCheckBox.setEnabled(false);
                    isAdmin=true;
                    yourIPLbl.setVisible(true);
                    yourIPAdressArea.setVisible(true);
                    ipAdressArea.setEditable(true);
                    ipAdressArea.setText("");
                    nicknameArea.setText("Administrator");
                    nicknameArea.setEditable(false);
                    startButton.setText("Uruchom sesję");
                    codeAreaLabel.setText("Ustaw kod dostępu");
                }
                else
                    clientCheckBox.setEnabled(true);
            }
        });
        typeActionFrame.repaint();
    }
}
class IPTextFieldVerifier extends InputVerifier
{
    public boolean verify(JComponent input)
    {
        if (input instanceof JFormattedTextField)
        {
            JFormattedTextField ftf = (JFormattedTextField) input;
            JFormattedTextField.AbstractFormatter formatter = ftf.getFormatter();
            if (formatter != null) {
                String text = ftf.getText();
                StringTokenizer st = new StringTokenizer(text, ".");
                while (st.hasMoreTokens()) {
                    int value = Integer.parseInt((String) st.nextToken());
                    if (value < 0 || value > 255) {
                        // to prevent recursive calling of the
                        // InputVerifier, set it to null and
                        // restore it after the JOptionPane has
                        // been clicked.
                        input.setInputVerifier(null);
                        JOptionPane.showMessageDialog(new Frame(), "Malformed IP Address!", "Error",
                                JOptionPane.ERROR_MESSAGE);
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
