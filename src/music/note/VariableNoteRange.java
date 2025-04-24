/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package music.note;

import static music.note.NoteFrequencyConstants.LOG_TUNING_RATIO;
import static music.note.NoteFrequencyConstants.TUNING_RATIO;

/**
 *
 * @author Edward Jenkins
 */
public class VariableNoteRange implements ITunable {
    
    // instance variabels
    private double octaveRatio;
    private double centreFrequency;
    private byte centreNote;
    private double lowestFrequency;
    private double lowestFrequencyRatio;
    
    // constructor
    public VariableNoteRange(double octaveRatio, double centreFrequency,
            byte centreNote) {
        this.octaveRatio = octaveRatio;
        this.centreFrequency = centreFrequency;
        this.centreNote = centreNote;
        lowestFrequency = this.centreFrequency
                * Math.pow(TUNING_RATIO, -centreNote);
        lowestFrequencyRatio = 1.0 / lowestFrequency;
    }
    
    public VariableNoteRange(double centreFrequency, byte centreNote) {
        this(0, centreFrequency, centreNote);
    }
    
    @Override
    public double getOffsetFrequency(byte note) {
        // frequency of new note
        double returnFrequency = lowestFrequency 
                * Math.pow(TUNING_RATIO, note);
        
        return returnFrequency;
    }
    
    @Override
    public double getNoteIndexRatio(double frequency) {
        double noteIndex =  Math.log(frequency * lowestFrequencyRatio) 
                * LOG_TUNING_RATIO;
        
        return noteIndex;
    }
}
