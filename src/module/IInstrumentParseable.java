/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module;

import sound.sample.player.IToneAssignable;

/**
 *
 * @author eddy6
 */
public interface IInstrumentParseable extends IToneAssignable {
    
    public String getInstrumentName();
    
    public String getDosFileName();
    
    public short getFadeOut();

    public short getPitchPanSeparation();

    public byte getPitchPanCentre();
    
    public double getNormalisedGlobalVolume();

    public double getNormalisedPanValue();
    
    public int getGlobalVolume();

    public int getPanValue();
    
    public boolean isPanning();

    public byte getRandomVolumeVariation();

    public byte getRandomPanningVariation();
    
    public byte getRandomCutoffVariation();
    
    public byte getRandomResonanceVariation();
    
    public boolean isFiltered();
    
    public short getInitialFilterCutoff();
    
    public boolean isUsingResonance();

    public short getInitialFilterResonance();

    public short getMidiChannel();

    public byte getMidiProgram();

    public short getMidiBank();
    
    public boolean isSingleSampleMapped();
    
    public byte[] getNoteSampleTableColumns();
    
    public boolean isSampleInstanceMapped();
    
    public short[][] getNoteSampleKeyboardTable();
    
    //public NodePoint[] getVolumeEnvelopePoints();
    
    //public NodePoint[] getPanEnvelopePoints();
    
    //public NodePoint[] getPitchEnvelopePoints();
    
    public ISynthParseable[] getSamples();
}
