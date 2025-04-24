/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package module;

import file.ISaveableFile;
import sound.formats.GenericEnvelopeSpec;

/**
 *
 * @author eddy6
 */
public interface ISynthParseable extends ISaveableFile  {
    
    // get the name of the sample
    public String getSampleName();
    
    // get the file name of the sample
    public String getDosFileName();
    
    // get the sample cache
    //public SynthCache getSampleCache();
    
    // get default volume
    public double getNoralisedDefaultVolume();
    
    // get global volume
    public double getNormalisedGlobalVolume();
    
    // get pan value
    public double getNormalisedPanValue();
    
    // get default volume
    public int getDefaultVolume();
    
    // get global volume
    public int getGlobalVolume();
    
    // get pan value
    public int getPanValue();
    
    // is panning
    public boolean isPanning();
    
    // get starting note
    public byte getStartingNote();
    
    // get ending note
    public byte getEndingNote();
    
    // get starting velocity
    public byte getStartingVelocity();
    
    // get ending velocity
    public byte getEndingVelocity();
    
    // get vibrato speed
    public double getFullVibratoSpeed();
    
    // get vibrato depth
    public double getFullVibratoDepth();
    
    // get vibrato waveform
    public byte getVibratoWaveform();
    
    // get vibrato delay
    public double getVibratoDelay();
    
    // get the envelope spec
    public GenericEnvelopeSpec getGenericEnvelopeSpec();
}
