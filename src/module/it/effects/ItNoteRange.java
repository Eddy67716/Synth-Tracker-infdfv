/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.it.effects;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import music.note.NoteRange;

/**
 *
 * @author Edward Jenkins
 */
public class ItNoteRange extends NoteRange {

    // costants
    public static final double DEF_TUNING_FREQUENCY = 440;
    public static final String[] NOTES = {"C_", "C#", "D_",
        "D#", "E_", "F_", "F#", "G_", "G#", "A_", "A#", "B_"};

    // All args constructor
    public ItNoteRange(double tuningFrequency) {
        super(NOTES, NOTES, NOTES, tuningFrequency);
    }

    // no arg constructor
    public ItNoteRange() {
        this(DEF_TUNING_FREQUENCY);
    }

    // createNoteMap method
    @Override
    public void createNoteMap() throws IOException {

        // methods varialbes
        int i, j, noteIndex = 0;
        double currentFrequency = getTuningFrequency()
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
                getNoteMap().put((NOTES[j] + i), noteIndex);

                // exit once  note is C9
                if (i == 9 && j == 0) {
                    break;
                }

            }
        }
    }

    // getters
    @Override
    public int getWritableNoteIndex(String note) {

        int returnInt = 0;

        switch (note) {
            case "=":
                returnInt = 255;
                break;
            case "^":
                returnInt = 254;
                break;
            case "~":
                returnInt = 253;
                break;
            default:
                try {
                    returnInt = (int) getNoteMap().get(note);
                } catch (NullPointerException e) {
                    returnInt = 0;
                }
                break;
        }

        return returnInt;
    }

    public double getFrequency(String note) {
        int noteIndex = (int) getNoteMap().get(note);
        return this.getFrequency(noteIndex);
    }

    @Override
    public String getNote(int noteIndex) {

        // force to unsigned value
        noteIndex &= 0xff;

        String returnValue = "";

        if (noteIndex == 0) {
            returnValue = "...";
        } else if (noteIndex > 0 && noteIndex <= 120) {
            // iterate to get the note name
            returnValue = super.getNote(noteIndex);
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
