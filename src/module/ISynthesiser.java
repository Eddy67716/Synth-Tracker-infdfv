/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package module;


/**
 *
 * @author Edward Jenkins
 */
public interface ISynthesiser extends ISynthParseable {
    
    // set the name of the sample
    public void setSampleName(String sampleName);
    
    // set the file name of the sample
    public void setDosFileName(String dosFileName);
    
    // set default volume
    public void setNormalisedDefaultVolume(double defaultVolume);
    
    // set global volume
    public void setNormalisedGlobalVolume(double globalVolume);
    
    // set pan value
    public void setNormalisedPanValue(double panValue);
    
    // set default volume
    public void setDefaultVolume(int defaultVolume);
    
    // set global volume
    public void setGlobalVolume(int globalVolume);
    
    // set pan value
    public void setPanValue(int panValue);
    
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
