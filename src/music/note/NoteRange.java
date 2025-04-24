/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music.note;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static music.note.NoteFrequencyConstants.*;

/**
 *
 * @author Edward Jenkins
 */
public abstract class NoteRange implements ITunable {
    
    // instance variables
    public String[] normalNotes;
    public String[] alturnativeNotes;
    public String[] quarterToneNotes;
    // defines the frequency for A4
    private double tuningFrequency;   
    // stores the note name and index of said note
    private HashMap<String, Integer> noteMap;
    // stores the frequencies of the notes
    private double[] noteFrequencies;
    // stores the ratio used for getting the note index from the frequency
    private double lowestFrequencyRatio;
    // is using quarter tone tuning
    private boolean quarterToneTuned;

    // All args constructor
    public NoteRange(String[] normalNotes, String[] alturnativeNotes, 
            String[] quarterToneNotes, double tuningFrequency) {
        this.normalNotes = normalNotes;
        this.alturnativeNotes = alturnativeNotes;
        this.quarterToneNotes = quarterToneNotes;
        this.tuningFrequency = tuningFrequency;
        noteMap = new HashMap<>();
        noteFrequencies = new double[128];
        try {
            createNoteMap();
            lowestFrequencyRatio = 1.0 / getFrequency(0);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // no arg constructor
    public NoteRange(String[] normalNotes, String[] alturnativeNotes, 
            String[] quarterToneNotes) {
        this(normalNotes, alturnativeNotes, quarterToneNotes, 440);
    }
    
    // getters
    public HashMap getNoteMap() {
        return this.noteMap;
    }

    protected double[] getNoteFrequencies() {
        return noteFrequencies;
    }

    protected double getTuningFrequency() {
        return tuningFrequency;
    }

    // createNoteMap method
    public void createNoteMap() throws IOException {

        // methods varialbes
        int i, j, noteIndex = 0;
        double currentFrequency = tuningFrequency;

        // loop to populate hashmap
        // normal number loop
        for (i = -2; i < 9;) {

            // note name loop
            for (j = 0; j < normalNotes.length; j++, noteIndex++) {

                // increment i at every C
                if (j == 0) {
                    i++;
                }

                // put current index in note map
                noteMap.put((normalNotes[j] + i), noteIndex);

                // exit once  note is G_9
                if (i == 9 && j == 7) {
                    break;
                }

            }
        }
        
        // alternative (note index shold be 0)
        noteIndex = 128;
        
        for (i = -2; i < 9;) {

            // note name loop
            for (j = 0; j < alturnativeNotes.length; j++, noteIndex++) {

                // increment i at every C
                if (j == 0) {
                    i++;
                }

                // put current index in note map
                noteMap.put((alturnativeNotes[j] + i), noteIndex);

                // exit once  note is G_9
                if (i == 9 && j == 7) {
                    break;
                }

            }
        }

        // loops for setting the frequencies
        // A4 and above
        for (i = 69; i < noteFrequencies.length; i++) {

            // put current frequency in note frequencies array
            noteFrequencies[i] = currentFrequency;

            // get next equally tempered semitone frequency
            currentFrequency *= TUNING_RATIO;
        }

        double downTuneRatio = Math.pow(TUNING_RATIO, -1);

        currentFrequency = tuningFrequency;

        // below A4
        for (i = 68; i > -1; i--) {
            
            // get next equally tempered semitone frequency
            currentFrequency *= downTuneRatio;

            // put current frequency in note frequencies array
            noteFrequencies[i] = currentFrequency;
        }
    }

    

    public int getPlayNoteIndex(String note) {
        return noteMap.get(note) & 0b1111111;
    }
    
    public int getWritableNoteIndex(String note) {
        return noteMap.get(note);
    }

    public double getFrequency(String note) {
        int noteIndex = noteMap.get(note);
        double frequency = noteFrequencies[noteIndex];
        return frequency;
    }
    
    public double getFrequency(int noteIndex) {
        double frequency = 0;
        if (noteIndex < noteFrequencies.length) {
            frequency = noteFrequencies[noteIndex];
        }
        return frequency;
    }

    public String getNote(int noteIndex) {

        String returnValue = "";

        // iterate to get the note name
        for (Map.Entry<String, Integer> map : noteMap.entrySet()) {
            if (map.getValue().equals(noteIndex)) {
                returnValue = map.getKey();
            }
        }

        return returnValue;
    }
    
    @Override
    public double getOffsetFrequency(byte note) {
        // frequency of new note
        double returnFrequency = getFrequency(0) 
                * Math.pow(TUNING_RATIO, note);
        
        return returnFrequency;
    }
    
    @Override
    public double getNoteIndexRatio(double frequency) {
        return Math.log(frequency * lowestFrequencyRatio) * LOG_TUNING_RATIO;
    }
}
