/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package music.note;

/**
 *
 * @author Edward Jenkins
 */
public class EngDutNoteRange extends NoteRange {
    
    // constants
    public static final String[] NOTES = {"C_", "C#", "D_",
        "D#", "E_", "F_", "F#", "G_", "G#", "A_", "A#", "B_"};
    public static final String[] ALT_NOTES = {"B#", "Db", "C*",
        "Eb", "Fb", "E#", "Gb", "F*", "Ab", "BB", "Bb", "Cb"};
    public static final String[] QUARTER_TONE_NOTES = {"Ch", "Cs", "Dh",
        "Ds", "Eh", "Fh", "Fs", "Gh", "Gs", "Ah", "As", "Bh"};

    public EngDutNoteRange(double tuningFrequency) {
        super(NOTES, ALT_NOTES, QUARTER_TONE_NOTES, tuningFrequency);
    }
    
    public EngDutNoteRange() {
        super(NOTES, ALT_NOTES, QUARTER_TONE_NOTES);
    }
}
