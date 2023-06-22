/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module;

import io.ISavableFile;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Edward Jenkins
 */
public interface IAudioSample extends ISavableFile {
    
    // get the name of the sample
    public String getSampleName();
    
    // get the file name of the sample
    public String getDOSFileName();
    
    // get the sample rate of the sample
    public int getC5Speed();
    
    // get the bitrate of the sample
    public short getBitRate();
    
    // get signness of the sample
    public boolean isSigned();
    
    // check if stereo
    public boolean isStereo();
    
    // get sample length
    public int getSampleLength();
    
    // get default volume
    public short getDefaultVolume();
    
    // get global volume
    public byte getGlobalVolume();
    
    // get pan value
    public byte getPanValue();
    
    // is panning
    public boolean isPanning();
    
    // is looped
    public boolean isLooped();
    
    // is sustaint looped
    public boolean isSustainLooped();
    
    // is looped
    public boolean isPingPongLooped();
    
    // is sustaint looped
    public boolean isPingPongSustainLooped();
    
    // get loop beginning
    public long getLoopBeginning();
    
    // get loop beginning
    public long getLoopEnd();
    
    // get loop beginning
    public long getSustainLoopBeginning();
    
    // get loop beginning
    public long getSustainLoopEnd();
    
    // get the left channel data
    public double[] getLData();
    
    // get the right channel data
    public double[] getRData();
    
    // get vibrato speed
    public double getFullVibratoSpeed();
    
    // get vibrato depth
    public double getFullVibratoDepth();
    
    // get vibrato waveform
    public byte getVibratoWaveform();
    
    // get vibrato delay
    public double getVibratoDelay();
    
    // set the name of the sample
    public void setSampleName(String sampleName);
    
    // set the file name of the sample
    public void setDOSFileName(String dosFileName);
    
    // set the sample rate of the sample
    public void setC5Speed(int c5Speed);
    
    // set the bitrate of the sample
    public void setBitRate(short bitRate);
    
    // set signness of the sample
    public void setSigned(boolean signed);
    
    // set stereo
    public void setStereo(boolean stereo);
    
    // set default volume
    public void setDefaultVolume(short defaultVolume);
    
    // set global volume
    public void setGlobalVolume(byte globalVolume);
    
    // set pan value
    public void setPanValue(byte panValue);
    
    // set panning
    public void setPanning(boolean panning);
    
    // set looped
    public void setLooped(boolean looped);
    
    // set sustaint looped
    public void setSustainLooped(boolean sustainLooped);
    
    // set looped
    public void setPingPongLooped(boolean pingPongLooped);
    
    // set sustaint looped
    public void setPingPongSustainLooped(boolean pingPongSustainLooped);
    
    // set loop beginning
    public void setLoopBeginning(long loopBeginning);
    
    // set loop end
    public void setLoopEnd(long loopEnd);
    
    // set sustain loop beginning
    public void setSustainLoopBeginning(long sustainLoopBeginning);
    
    // set sustain loop end
    public void setSustainLoopEndlong(long sustainLoopEnd);
    
    // set the left channel data
    public void setLData(double[] lData);
    
    // set the right channel data
    public void setRData(double[] rData);
    
    // set vibrato speed
    public void setFullVibratoSpeed(double vibratoSpeed);
    
    // set vibrato depth
    public void setFullVibratoDepth(double vibratoDepth);
    
    // set vibrato waveform
    public void setVibratoWaveform(byte vibratoWaveform);
    
    // set vibrato delay
    public void setVibratoDelay(double vibratoDelay);
}
