/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package module;

import sound.formats.AudioSampleData;
import sound.formats.GenericEnvelopeSpec;

/**
 *
 * @author eddy6
 */
public interface ISampleParseable extends ISynthParseable {
    
    // get transposition note
    public byte getTranspositionNote();
    
    // get tuningOffset
    public byte getFineTuning();
    
    // get the sample rate of the sample
    public int getC5Speed();
    
    // get the bitrate of the sample
    public short getBitRate();
    
    // get signness of the sample
    public boolean isSigned();
    
    // get whether sample is floating point
    public boolean isFloating();
    
    // check if compressed
    public boolean isCompressed();
    
    // check if stereo
    public boolean isStereo();
    
    // get sample length
    public int getSampleLength();
    
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
    public AudioSampleData getAudioSampleData();
}
