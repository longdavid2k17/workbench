package VoiceChatPackage;

import UserInterfaces.SettingsUI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPOutputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * reads data from microphone and sends it to the server
 *
 * @author dosse
 */
public class MicThread extends Thread
{
    public static double amplification = 1.0;
    private ObjectOutputStream toServer;
    private TargetDataLine mic;

    public MicThread(ObjectOutputStream toServer) throws LineUnavailableException
    {
        this.toServer = toServer;
        AudioFormat af = SoundPacket.defaultFormat;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, null);
        mic = (TargetDataLine) (AudioSystem.getLine(info));
        mic.open(af);
        mic.start();
    }

    @Override
    public void run()
    {
        for (;;)
        {
            if (mic.available() >= SoundPacket.defaultDataLenght)
            {
                SettingsUI.setMicAvalibility(true);
                byte[] buff = new byte[SoundPacket.defaultDataLenght];
                while (mic.available() >= SoundPacket.defaultDataLenght)
                {
                    mic.read(buff, 0, buff.length);
                }
                try
                {
                   long tot = 0;
                    for (int i = 0; i < buff.length; i++)
                    {
                        buff[i] *= amplification;
                        tot += Math.abs(buff[i]);
                    }
                    tot *= 2.5;
                    tot /= buff.length;
                    Message m = null;
                    if (tot == 0)
                    {
                        m = new Message(-1, -1, new SoundPacket(null));

                    }
                    else
                    {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        GZIPOutputStream go = new GZIPOutputStream(baos);
                        go.write(buff);
                        go.flush();
                        go.close();
                        baos.flush();
                        baos.close();
                        m = new Message(-1, -1, new SoundPacket(baos.toByteArray()));  //create message for server, will generate chId and timestamp from this computer's IP and this socket's port
                    }
                    toServer.writeObject(m);
                }
                catch (IOException ex)
                {
                    stop();
                }
            }
            else
            {
                SettingsUI.setMicAvalibility(false);
                Utils.sleep(10);
            }
        }
    }
}

