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
import sound.sample.player.IToneAssignable;

/**
 *
 * @author Edward Jenkins
 */
public interface IInstrument extends IToneAssignable {
    
    public String getInstrumentName();
    
    public String getDosFileName();
    
    public short getFadeOut();

    public short getPitchPanSeparation();

    public byte getPitchPanCentre();
    
    public short getGlobalVolume();

    public byte getPanValue();
    
    public boolean isPanning();

    public byte getRandomVolumeVariation();

    public byte getRandomPanningVariation();
    
    public short getInitialFilterCutoff();

    public short getInitialFilterResonance();

    public short getMidiChannel();

    public byte getMidiProgram();

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

    public void setSampleNumber(short sampleNumber);

    public void setInstrumentName(String instrumentName);

    public void setInitialFilterCutoff(short initialFilterCutoff);

    public void setInitialFilterResonance(short initialFilterResonance);

    public void setMidiChannel(short midiChannel);

    public void setMidiProgram(byte midiProgram);

    public void setMidiBank(short midiBank);
}
