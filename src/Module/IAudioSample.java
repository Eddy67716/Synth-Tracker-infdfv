/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Edward Jenkins
 */
public interface IAudioSample {
    
    // for reading samples
    public boolean read() throws IOException, FileNotFoundException;
    
    // for writing samples
    public boolean write() throws IOException, FileNotFoundException;
    
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
    
}
