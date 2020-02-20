package UserInterfaces;

import UtilitiesPackage.MicThread;
import UtilitiesPackage.SoundPacket;
import UtilitiesPackage.Utils;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SettingsUI extends JFrame
{
    private JFrame frame;
    private JLabel micLabel,volumeLabel, audioDevices;
    private JProgressBar micLevel;
    private JSlider micVolume;
    private JComboBox audioDevicesBox;
    private TargetDataLine mic = null;
    private MicTester micTester;
    static boolean isMicAvalible=true;

    Border border = BorderFactory.createLineBorder(Color.BLACK);

    private class MicTester extends Thread
    {
        private TargetDataLine mic = null;
        public MicTester()
        {

        }
        @Override
        public void run()
        {
            try
            {
                AudioFormat af = SoundPacket.defaultFormat;
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, null);
                mic = (TargetDataLine) (AudioSystem.getLine(info));
                mic.open(af);
                mic.start();
                SettingsUI.setMicAvalibility(true);
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(rootPane,"Nie podłączono mikrofonu!\nCzat głosowy nie będzie dla Ciebie dostępny!", "Nie podłączono mikrofonu!",JOptionPane.ERROR_MESSAGE);
            }
            for (;;)
            {
                Utils.sleep(10);
                if(mic.available()>0){
                    SettingsUI.setMicAvalibility(true);
                    byte[] buff=new byte[SoundPacket.defaultDataLenght];
                    mic.read(buff,0,buff.length);
                    long tot=0;
                    for(int i=0;i<buff.length;i++) tot+=MicThread.amplification*Math.abs(buff[i]);
                    tot*=2.5;
                    tot/=buff.length;
                    micLevel.setValue((int)tot);
                }
                else
                {
                    //SettingFrame.setMicAvalibility(false);
                }
            }
        }

        private void close()
        {
            if(mic!=null) mic.close();
            stop();
        }
    }
    boolean getMicAvalibility()
    {
        return isMicAvalible;
    }
    public static void setMicAvalibility(boolean value)
    {
        isMicAvalible=value;
    }
    SettingsUI()
    {
        micTester=new MicTester();
        micTester.start();

        frame = new JFrame("Ustawienia");
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(222,240,252));
        frame.validate();

        micLabel = new JLabel("Dźwięk");
        micLabel.setBounds(10,10,50,20);
        micLabel.setVisible(true);
        frame.add(micLabel);

        micLevel = new JProgressBar();
        micLevel.setBackground(new Color(222,240,252));
        micLevel.setOrientation(SwingConstants.HORIZONTAL);
        micLevel.setValue(0);
        micLevel.setMinimum(0);
        micLevel.setMaximum(100);
        micLevel.setForeground(Color.RED);
        //micLevel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        micLevel.setBounds(110,20,150,5);
        frame.add(micLevel);

        volumeLabel = new JLabel("Głośność");
        volumeLabel.setBounds(10,60,80,20);
        volumeLabel.setVisible(true);
        frame.add(volumeLabel);

        micVolume = new JSlider(SwingConstants.HORIZONTAL, 50, 300, 50);
        micVolume.setVisible(true);
        micVolume.setBackground(new Color(222,240,252));
        micVolume.addChangeListener(e -> micVolStateChanged(e));
        micVolume.setForeground(Color.RED);
        micVolume.setBounds(110,60,150,20);
        frame.add(micVolume);

        audioDevices = new JLabel("Mikrofon");
        audioDevices.setBounds(10,100,90,20);
        audioDevices.setVisible(true);
        frame.add(audioDevices);

        audioDevicesBox = new JComboBox();
        Mixer.Info[] audioSystem = AudioSystem.getMixerInfo();
        int i =0;
        while(audioSystem!=null&& i<audioSystem.length){
            audioDevicesBox.addItem(audioSystem[i]);
            i++;
        }
        audioDevicesBox.setBounds(110,100,160,30);
        audioDevicesBox.setSelectedIndex(0);
        audioDevicesBox.setVisible(true);
        audioDevicesBox.setBackground(new Color(222,240,252));
        audioDevicesBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        });
        frame.add(audioDevicesBox);
    }
    private void micVolStateChanged(javax.swing.event.ChangeEvent evt)
    {
        //GEN-FIRST:event_micVolStateChanged
        MicThread.amplification=((double)(micVolume.getValue()))/100.0;
    }
}
