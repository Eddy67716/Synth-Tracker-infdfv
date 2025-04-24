/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_FLOAT;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Edward Jenkins
 */
public class AudioSetup {

    // constants
    public static final byte CHANNELS = 2;

    // instance variables
    private int sampleRate;
    private int channels;
    private AudioFormat audioFormat;
    private SourceDataLine sourceDataLine;

    public AudioSetup(int sampleRate, int channels)
            throws LineUnavailableException {

        this.sampleRate = sampleRate;
        this.channels = channels;

        getBestAudioFormat();

    }

    // getters
    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    public SourceDataLine getSourceDataLine() {
        return sourceDataLine;
    }

    private void getBestAudioFormat() throws LineUnavailableException {

        // get highest possible quality bitrate for system
        int highestBitRate = 16;

        AudioFormat currentFormat = new AudioFormat(new Encoding("PCM_SIGNED"), (float) sampleRate, highestBitRate,
                channels, highestBitRate / 8 * channels, sampleRate, true);

        Line.Info desired = new Line.Info(SourceDataLine.class);
        Line.Info[] infos = AudioSystem.getSourceLineInfo(desired);

        for (Line.Info info: infos) {
            if (info instanceof DataLine.Info) {
                AudioFormat[] forms = ((DataLine.Info) info).getFormats();

                // use floating point encoding if available
                for (AudioFormat format : forms) {
                    if (format.getSampleSizeInBits() > highestBitRate
                            && format.isBigEndian()
                            && format.getChannels() == channels
                            && format.getEncoding() == PCM_FLOAT) {
                        currentFormat = format;
                        highestBitRate = format.getSampleSizeInBits();
                    }
                }

                // if no floating point available use PCM signed encoding
                if (currentFormat.getEncoding() != PCM_FLOAT) {
                    // reset bitrate and try again
                    highestBitRate = 16;
                    for (AudioFormat format : forms) {
                        if (format.getSampleSizeInBits() > highestBitRate
                                && format.isBigEndian()
                                && format.getChannels() == channels) {
                            currentFormat = format;
                            highestBitRate = format.getSampleSizeInBits();
                        }
                    }
                }
            }
        }

        audioFormat = new AudioFormat(currentFormat.getEncoding(),
                sampleRate,
                currentFormat.getSampleSizeInBits(),
                currentFormat.getChannels(),
                currentFormat.getFrameSize(),
                sampleRate * channels,
                currentFormat.isBigEndian());
        System.out.println(audioFormat);
        sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
    }

}
