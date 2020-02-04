import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

public class SettingFrame extends JFrame
{
    JFrame frame;
    JLabel micLabel,volumeLabel, audioDevices;
    JProgressBar micLevel;
    JSlider micVolume;
    JComboBox audioDevicesBox;
    private TargetDataLine mic = null;
    private MicTester micTester;
    static boolean isMicAvalible=true;

    private class MicTester extends Thread{
        private TargetDataLine mic = null;
        public MicTester()
        {

        }
        @Override
        public void run() {

            try {
                AudioFormat af = SoundPacket.defaultFormat;
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, null);
                mic = (TargetDataLine) (AudioSystem.getLine(info));
                mic.open(af);
                mic.start();
                SettingFrame.setMicAvalibility(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane,"Microphone not detected.\nPress OK to close this window", "Error",JOptionPane.ERROR_MESSAGE);
            }
            for (;;) {
                Utils.sleep(10);
                if(mic.available()>0){
                    SettingFrame.setMicAvalibility(true);
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

        private void close(){
            if(mic!=null) mic.close();
            stop();
        }
    }
    boolean getMicAvalibility()
    {
        return isMicAvalible;
    }
    static void setMicAvalibility(boolean value)
    {
        isMicAvalible=value;
    }
    SettingFrame()
    {
        micTester=new MicTester();
        micTester.start();

        frame = new JFrame("Workbench - ustawienia");
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.validate();

        micLabel = new JLabel("Dźwięk");
        micLabel.setBounds(10,10,50,20);
        micLabel.setVisible(true);
        frame.add(micLabel);

        micLevel = new JProgressBar();
        micLevel.setBackground(Color.LIGHT_GRAY);
        micLevel.setOrientation(SwingConstants.HORIZONTAL);
        micLevel.setValue(0);
        micLevel.setMinimum(0);
        micLevel.setMaximum(100);
        micLevel.setBounds(110,20,150,5);
        frame.add(micLevel);

        volumeLabel = new JLabel("Głośność");
        volumeLabel.setBounds(10,60,80,20);
        volumeLabel.setVisible(true);
        frame.add(volumeLabel);

        micVolume = new JSlider(SwingConstants.HORIZONTAL, 50, 300, 50);
        micVolume.setVisible(true);
        micVolume.setBackground(Color.LIGHT_GRAY);
        micVolume.addChangeListener(e -> micVolStateChanged(e));
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
        audioDevicesBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        });
        frame.add(audioDevicesBox);
    }
    private void micVolStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_micVolStateChanged
        MicThread.amplification=((double)(micVolume.getValue()))/100.0;
    }
}
