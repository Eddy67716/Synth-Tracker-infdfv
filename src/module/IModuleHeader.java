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
    public String getArtistName();
    
    // get pettern highlight
    public short[] getPatternHilight();
    
    // is stereo
    public boolean isStereo();
    
    // is using linear slides
    public boolean isUsingLinearSlides();
    
    // is using IT effects
    public boolean isUsingOldEffects();
    
    // max global volume value
    public int getMaxGlobalVolume();
    
    // normalised global volume
    public double getNormalisedGlobalVolume();
    
    // global volume
    public short getGlobalVolume();
    
    // max global volume value
    public int getMaxMixVolume();
    
    // normalised mix value
    public double getNormalisedMixVolume();
    
    // mix volume
    public short getMixVolume();
    
    // max pan separation value
    public int getMaxPanSeparation();
    
    // normalised pan separation
    public double getNormalisedPanSeparation();
    
    // pan separation
    public short getPanSeparation();
    
    // initial speed
    public short getInitialSpeed();

    // initial tempo
    public short getInitialTempo();
    
    // channel count
    public byte getChannelCount();
    
    // sample count
    public short getSampleCount();
    
    // instrumentCount
    public short getInstrumentCount();
    
    // created tracker
    public int getCreatedWithTracker();
    
    // possible creation software
    public String getPossibleCreatedSoftware();

    // lowest compatible tracker
    public int getCompatibleWithTracker();
    
    // lowest compatible software string
    public String getLowestCompatibleSoftware();
    
    // order count
    public short getOrderCount();
    
    // pattern count
    public short getPatternCount();
    
    // get the message
    public String getMessage();
    
    // set song name
    public void setSongName(String songName);
    
    // set author name
    public void setAritistName(String authourName);
    
    // set stereo
    public void setStereo(boolean stereo);
    
    // set using linear slides
    public void setUsingLinearSlides(boolean usingLinearSlides);
    
    // set using IT effects
    public void setUsingOldEffects(boolean usingOldEffects);
    
    // set global volume
    public void setGlobalVolume(short globalVolume);
    
    // set mix value
    public void setNormalisedGlobalVolume(double globalVolume);
    
    // set unnormalisedMixVoume
    public void setMixVolume(short mixVolume);
    
    // set mix value
    public void setNormalisedMixVolume(double mixVolume);
    
    // set unnormalisedMixVoume
    public void setPanSeparation(short panSeparation);
    
    // set mix value
    public void setNormalisedPanSeparation(double panSeparation);
    
    // set initial speed
    public void setInitialSpeed(short initialSpeed);

    // set initial tempo
    public void setInitialTempo(short initialTempo);
    
    // set channel count
    public void setChannelCount(byte channelCount);
    
    // set sample count
    public void setSampleCount(short sampleCount);
    
    // set instrumentCount
    public void setInstrumentCount(short instrumentCount);
    
    // set compatible tracker
    public void setCompatibleWithTracker(int compatibleWithTracker);
    
    // set order count
    public void setOrderCount(short orderCount);
    
    // set pattern count
    public void setPatternCount(short patternCount);
    
    // set the message
    public void setMessage(String message);
}
