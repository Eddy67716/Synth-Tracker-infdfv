/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module;

import io.IReadable;
import io.IWritable;
import java.io.IOException;
import module.it.format.NodePoint;

/**
 *
 * @author Edward Jenkins
 */
public interface IInstrument {
    
    public boolean read() throws IOException, IllegalArgumentException;
    
    public boolean read(IReadable reader) throws IOException, 
            IllegalArgumentException; 
    
    public boolean write(IWritable writer) throws Exception;
    
    public String getInstrumentName();
    
    public String getDosFileName();
    
    public byte getNewNoteAction();

    public byte getDuplicateCheckType();

    public byte getDuplicateCheckAction();
    
    public int getFadeOut();

    public byte getPitchPanSeparation();

    public byte getPitchPanCentre();
    
    public short getGlobalVolume();

    public byte getPanValue();
    
    public boolean isPanning();

    public byte getRandomVolumeVariation();

    public byte getRandomPanningVariation();
    
    public short getInitialFilterCutoff();

    public short getInitialFilterResonance();

    public short getMidiChannel();

    public short getMidiProgram();

    public short getMidiBank();
    
    public short[][] getNoteSampleKeyboardTable();
    
    public NodePoint[] getVolumeEnvelopePoints();
    
    public NodePoint[] getPanEnvelopePoints();
    
    public NodePoint[] getPitchEnvelopePoints();

    public void setDosFileName(String dosFileName);

    public void setDuplicateNoteCheck(boolean duplicateNoteCheck);

    public void setNewNoteAction(byte newNoteAction);

    public void setDuplicateCheckType(byte duplicateCheckType);

    public void setDuplicateCheckAction(byte duplicateCheckAction);

    public void setFadeOut(int fadeOut);

    public void setPitchPanSeparation(byte pitchPanSeparation);

    public void setPitchPanCentre(byte pitchPanCentre);

    public void setGlobalVolume(short globalVolume);

    public void setPanValue(byte panValue);
    
    public void setPanning(boolean panning);

    public void setRandomVolumeVariation(byte randomVolumeVariation);

    public void setRandomPanningVariation(byte randomPanningVariation);

    public void setNumberOfSamples(byte numberOfSamples);

    public void setInstrumentName(String instrumentName);

    public void setInitialFilterCutoff(short initialFilterCutoff);

    public void setInitialFilterResonance(short initialFilterResonance);

    public void setMidiChannel(short midiChannel);

    public void setMidiProgram(short midiProgram);

    public void setMidiBank(short midiBank);
    
}
