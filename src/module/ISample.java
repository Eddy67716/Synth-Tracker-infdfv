/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package module;

import io.ISavableFile;

/**
 *
 * @author Edward Jenkins
 */
public interface ISample extends ISavableFile {
    
    // get the name of the sample
    public String getSampleName();
    
    // get the file name of the sample
    public String getDOSFileName();
    
    // get default volume
    public short getDefaultVolume();
    
    // get global volume
    public byte getGlobalVolume();
    
    // get pan value
    public byte getPanValue();
    
    // is panning
    public boolean isPanning();
    
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
    
    // set default volume
    public void setDefaultVolume(short defaultVolume);
    
    // set global volume
    public void setGlobalVolume(byte globalVolume);
    
    // set pan value
    public void setPanValue(byte panValue);
    
    // set panning
    public void setPanning(boolean panning);
    
    // set vibrato speed
    public void setVibratoSpeed(double vibratoSpeed);
    
    // set vibrato depth
    public void setFullVibratoDepth(double vibratoDepth);
    
    // set vibrato waveform
    public void setVibratoWaveform(byte vibratoWaveform);
    
    // set vibrato delay
    public void setVibratoDelay(double vibratoDelay);
}
