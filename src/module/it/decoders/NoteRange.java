/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.it.decoders;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Edward Jenkins
 */
public class NoteRange {

    // costants
    public static final double DEF_TUNING_FREQUENCY = 440;
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
        this(DEF_TUNING_FREQUENCY);
    }

    // createNoteMap method
    public void createNoteMap() throws IOException {

        // methods varialbes
        int i, j, noteIndex = 0;
        double currentFrequency = tuningFrequency
                * Math.pow(Math.pow(2, 1.0 / 12.0), -69);

        // loop to populate hashmap
        // Number loop
        for (i = -1; i < 10;) {

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
                currentFrequency *= Math.pow(2, 1.0 / 12.0);

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
        
        // force to unsigned value
        noteIndex &= 0xff;

        String returnValue = "";

        if (noteIndex == 0) {
            returnValue = "...";
        } else if (noteIndex > 0 && noteIndex <= 120) {
            // iterate to get the note name
            for (Map.Entry<String, Integer> map : noteMap.entrySet()) {
                if (map.getValue().equals(noteIndex - 1)) {
                    returnValue = map.getKey();
                }
            }
        } else if (noteIndex == 254) {
            returnValue = "^^_";
        } else if (noteIndex == 255) {
            returnValue = "==_";
        } else {
            returnValue = "~~_";
        }

        return returnValue;
    }

}
