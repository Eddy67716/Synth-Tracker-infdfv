/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.midi;

import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import static javax.sound.midi.Sequence.SMPTE_24;
import module.IInstrument;
import module.IModuleFile;

/**
 *
 * @author eddy6
 */
public class MidiExporter {

    // instance variables
    private IModuleFile modFile;
    private Sequence midiSequence;
    private InstrumentTrackDataCache trackDataCache;
    private short currentTempo;
    private byte currentRowTickRate;
    private long currentTick;

    // constructor
    public MidiExporter(IModuleFile modFile, String midiName,
            boolean surpressEchos, boolean multiPortFile)
            throws InvalidMidiDataException {
        midiSequence = new Sequence(Sequence.PPQ, 24);
        List<IInstrument> instrumentList = modFile.getIInstruments();
        trackDataCache = new InstrumentTrackDataCache(modFile,
                multiPortFile);
        for (IInstrument instrument : instrumentList) {
            midiSequence.createTrack();
        }
    }

    public void exportFile() throws InvalidMidiDataException {
        setupInitialEvents();
    }

    private void setupInitialEvents() throws InvalidMidiDataException {
        int i = 0;
        for (IInstrument instrument : modFile.getIInstruments()) {

            // tracks
            byte[] trackName = instrument.getInstrumentName()
                    .getBytes(StandardCharsets.US_ASCII);
            MidiMessage trackNameMessage = new MetaMessage(0x3, trackName,
                    trackName.length);
            MidiEvent trackNameEvent
                    = new MidiEvent(trackNameMessage, currentTick);
            midiSequence.getTracks()[i].add(trackNameEvent);

            // assign track channels
            // assign programs and banks
            i++;
        }

        // assign initial tempo
        // assign initial row tick rate
    }

    private void streamPlayEvents() {

        // method variables
        int currentTick = 0;
        int currentPattern = 0;
        int currentRow = 0;
        int currentTickRate;
    }
}
