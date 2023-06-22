/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music.note;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static music.note.NoteFrequencyConstants.TUNING_FREQUENCY;
import static music.note.NoteFrequencyConstants.TUNING_RATIO;

/**
 *
 * @author Edward Jenkins
 */
public class NoteRange {

    // costants
    public static final String[] NOTES = {"C_", "C#", "D_",
        "D#", "E_", "F_", "F#", "G_", "G#", "A_", "A#", "B_"};
    

    // instance variables
    private double tuningFrequency;             // defines the frequency for A4
    private HashMap<String, Integer> noteMap;   // stores the note "A_4" and index of said note
    private double[] noteFrequencies;           // stores the frequencies of the notes

    // All args constructor
    public NoteRange(double tuningFrequency) {
        this.tuningFrequency = tuningFrequency;
        noteMap = new HashMap<>();
        noteFrequencies = new double[122];
        try {
            createNoteMap();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // no arg constructor
    public NoteRange() {
        this(TUNING_FREQUENCY);
    }

    // createNoteMap method
    public void createNoteMap() throws IOException {

        // methods varialbes
        int i, j, noteIndex = 0;
        double currentFrequency = tuningFrequency
                * Math.pow(TUNING_RATIO, -69);

        // loop to populate hashmap
        // Number loop
        for (i = -2; i < 9;) {

            // note name loop
            for (j = 0; j < NOTES.length; j++, noteIndex++) {

                // increment i at every C
                if (j == 0) {
                    i++;
                }

                // put current index in note map
                noteMap.put((NOTES[j] + i), noteIndex);
                
                // put current frequency in note frequencies array
                noteFrequencies[noteIndex] = currentFrequency;

                // get next equally tempered semitone frequency
                currentFrequency *= TUNING_RATIO;
                
                // exit once  note is C9
                if (i == 9 && j == 0) {
                    break;
                }

            }
        }
    }

    // getters
    public HashMap getNoteMap() {
        return this.noteMap;
    }
    
    public int getNoteIndex(String note) {
        return noteMap.get(note);
    }

    public double getFrequency(String note) {
        int noteIndex = noteMap.get(note);
        double frequency = noteFrequencies[noteIndex];
        return frequency;
    }
    
    public String getNote(int noteIndex) {
        
        String returnValue = "";
        
        // iterate to get the note name
        for(Map.Entry<String, Integer> map : noteMap.entrySet() ) {
            if (map.getValue().equals(noteIndex)) {
                returnValue = map.getKey();
            }
        }
        
        return returnValue;
    }

}
