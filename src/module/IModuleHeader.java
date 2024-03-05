/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package module;

/**
 *
 * @author Edward Jenkins
 */
public interface IModuleHeader {
    
    // get song name
    public String getSongName();
    
    // set author name
    public String getAuthorName();
    
    // get pettern highlight
    public short[] getPatternHilight();
    
    // is stereo
    public boolean isStereo();
    
    // is using linear slides
    public boolean isUsingLinearSlides();
    
    // is using IT effects
    public boolean isUsingOldEffects();
    
    // global volume
    public short getGlobalVolume();
    
    // mix value
    public double getMixVolume();
    
    // channel count
    public byte getChannelCount();
    
    // sample count
    public short getSampleCount();
    
    // instrumentCount
    public short getInstrumentCount();
    
    // order count
    public short getOrderCount();
    
    // pattern count
    public short getPatternCount();
    
    // get the message
    public String getMessage();
    
    // set song name
    public void setSongName(String songName);
    
    // set author name
    public void setAuthorName(String authourName);
    
    // set stereo
    public void setStereo(boolean stereo);
    
    // set using linear slides
    public void setUsingLinearSlides(boolean usingLinearSlides);
    
    // set using IT effects
    public void setUsingOldEffects(boolean usingOldEffects);
    
    // set global volume
    public void setGlobalVolume(short globalVolume);
    
    // set mix value
    public void setMixVolume(double mixVolume);
    
    // set channel count
    public void setChannelCount(byte channelCount);
    
    // set sample count
    public void setSampleCount(short sampleCount);
    
    // set instrumentCount
    public void setInstrumentCount(short instrumentCount);
    
    // set order count
    public void setOrderCount(short orderCount);
    
    // set pattern count
    public void setPatternCount(short patternCount);
    
    // set the message
    public void setMessage(String message);
}
