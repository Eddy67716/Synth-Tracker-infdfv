/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module.IT.Format;

import Module.IInstrument;
import io_stuff.ReadMethods;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Edward Jenkins
 */
public class ITInstrument implements IInstrument{

    // constants
    public static final String INSTRUMENT_CODE = "IMPI";

    // instance variables
    private String fileName;                        // name of file
    private long offsetToInstrument;        // where to start reading the file
    private boolean oldFormat;              // used to define which instrument type is used
    // if > 0x200, use new format
    private String testCode;                // test code with default code
    private String dosFileName;             // file name
    // old format
    private short flags;                    // flags for old format
    private boolean volumeEnvelopeEnabled;
    private boolean volumeLoopEnabled;
    private boolean sustainVolumeLoopEnabled;
    private short volumeLoopStart;          // node of volume start
    private short volumeLoopEnd;            // node
    private short sustainLoopStart;
    private short sustainLoopEnd;
    private boolean duplicateNoteCheck;      // check for duplicate notes
    private short[] volumeEnvelopeNodes;    // stored in instrument if old format
    // new format
    private byte newNoteAction;             // command on note
    private byte duplicateCheckType;        // duplication check
    private byte duplicateCheckAction;      // action on duplication
    private int fadeOut;                    // not fade out
    private byte pitchPanSeparation;        // sepperation
    private byte pitchPanCentre;            // centre of pitch pan
    private short globalVolume;             // global vomume
    private short defaultPan;               // default pan
    private byte randomVolumeVariation;     // percentage
    private byte randomPanningVariation;    // percentage
    private int trackerVersion;             // only used in instrument files
    private byte numberOfSamples;           // samples used in instrument
    private String instrumentName;          // 26 characters including null
    private short initialFilterCutoff;      // filter cuttoff;
    private short initialFilterResonance;   // resonance
    private short midiChannel;              // used with MIDI
    private short midiProgram;              // as above
    private int midiBank;                   // --------
    private short[][] noteSampleKeyboardTable;      // stores sample mapping
    private EnvelopeLayout volumeEnvelope;  // stores volume envelope
    private EnvelopeLayout panEnvelope;     // stores pan envelope
    private EnvelopeLayout pitchFilterEnvelope; // stores pitch/filter envelope

    // constructor
    public ITInstrument(String fileName, long offsetToInstrument,
            boolean oldFormat) {
        this.fileName = fileName;
        this.offsetToInstrument = offsetToInstrument;
        this.oldFormat = oldFormat;
    }

    // getters
    public String getFileName() {
        return fileName;
    }

    public long getOffsetToInstument() {
        return offsetToInstrument;
    }

    public String getTestCode() {
        return testCode;
    }

    public String getDosFileName() {
        return dosFileName;
    }

    public boolean isOldFormat() {
        return oldFormat;
    }

    public short getFlags() {
        return flags;
    }

    public short getVolumeLoopStart() {
        return volumeLoopStart;
    }

    public short getVolumeLoopEnd() {
        return volumeLoopEnd;
    }

    public short getSustainLoopStart() {
        return sustainLoopStart;
    }

    public short getSustainLoopEnd() {
        return sustainLoopEnd;
    }

    public boolean isDuplicateNoteCheck() {
        return duplicateNoteCheck;
    }

    public short[] getVolumeEnvelopeNodes() {
        return volumeEnvelopeNodes;
    }

    public byte getNewNoteAction() {
        return newNoteAction;
    }

    public byte getDuplicateCheckType() {
        return duplicateCheckType;
    }

    public byte getDuplicateCheckAction() {
        return duplicateCheckAction;
    }

    public int getFadeOut() {
        return fadeOut;
    }

    public byte getPitchPanSeparation() {
        return pitchPanSeparation;
    }

    public byte getPitchPanCentre() {
        return pitchPanCentre;
    }

    public short getGlobalVolume() {
        return globalVolume;
    }

    public short getDefaultPan() {
        return defaultPan;
    }

    public byte getRandomVolumeVariation() {
        return randomVolumeVariation;
    }

    public byte getRandomPanningVariation() {
        return randomPanningVariation;
    }

    public int getTrackerVersion() {
        return trackerVersion;
    }

    public byte getNumberOfSamples() {
        return numberOfSamples;
    }

    @Override
    public String getInstrumentName() {
        return instrumentName;
    }

    public short getInitialFilterCutoff() {
        return initialFilterCutoff;
    }

    public short getInitialFilterResonance() {
        return initialFilterResonance;
    }

    public short getMidiChannel() {
        return midiChannel;
    }

    public short getMidiProgram() {
        return midiProgram;
    }

    public int getMidiBank() {
        return midiBank;
    }

    public short[][] getNoteSampleKeyboardTable() {
        return noteSampleKeyboardTable;
    }

    public EnvelopeLayout getVolumeEnvelope() {
        return volumeEnvelope;
    }

    public EnvelopeLayout getPanEnvelope() {
        return panEnvelope;
    }

    public EnvelopeLayout getPitchFilterEnvelope() {
        return pitchFilterEnvelope;
    }

    public boolean read() throws Exception {

        // read methods
        ReadMethods rm = new ReadMethods(fileName, true);
        
        // set input stream to read at offset
        rm.skipBytes(offsetToInstrument);

        // success boolean
        boolean success = true;

        // header code
        testCode = rm.getByteString(4);

        if (!(testCode.equals(INSTRUMENT_CODE))) {
            throw new IOException("Header is not valid! ");
        }

        // dos file name
        dosFileName = rm.getByteString(12);

        // skip reserved byte
        rm.getByte();

        // figure out the if the format is the old or new one
        if (oldFormat) {

            // read old instrument
            // flags
            flags = rm.getUByteAsShort();

            // use volume envelope
            volumeEnvelopeEnabled = (flags & 1) == 1;

            // use volume loop
            volumeLoopEnabled = ((flags >>> 1) & 1) == 1;

            // use sustain volume loop
            sustainVolumeLoopEnabled = ((flags >>> 2) & 1) == 1;

            // volume loop start
            volumeLoopStart = rm.getUByteAsShort();

            // volume loop end
            volumeLoopEnd = rm.getUByteAsShort();

            // sustain loop start
            sustainLoopStart = rm.getUByteAsShort();

            // sustain loop end
            sustainLoopEnd = rm.getUByteAsShort();

            // skip two bytes
            rm.skipBytes(2);

            // fade out
            fadeOut = rm.getUShortAsInt();

            // new note action
            newNoteAction = rm.getByte();

            // duplicate note check
            duplicateNoteCheck = rm.getBoolean();

            // tracker version
            trackerVersion = rm.getUShortAsInt();

            // number of samples
            numberOfSamples = rm.getByte();

            // instrument name
            instrumentName = rm.getByteString(26);

            // skip 6 bytes
            rm.skipBytes(6);

            // note sample keyboard table
            noteSampleKeyboardTable = new short[240][2];

            // loop and add note sample keyboard tables
            for (int i = 0; i < 240; i++) {

                // note
                noteSampleKeyboardTable[i][0] = rm.getByte();

                // sample
                noteSampleKeyboardTable[i][1] = rm.getByte();
            }

            // volume envelope
        } else {

            // read new instrument
            // new note actions
            newNoteAction = rm.getByte();

            // duplicate check type
            duplicateCheckType = rm.getByte();

            // duplicate check action
            duplicateCheckAction = rm.getByte();

            // fade out
            fadeOut = rm.getUShortAsInt();

            // pitch pan separiation
            pitchPanSeparation = rm.getByte();

            // pitch pan centre
            pitchPanCentre = rm.getByte();

            // global volume
            globalVolume = rm.getUByteAsShort();

            // default pan
            defaultPan = rm.getByte();;

            // random volume variation
            randomVolumeVariation = rm.getByte();;

            // random panning variation
            randomPanningVariation = rm.getByte();;

            // tracker version
            trackerVersion = rm.getUShortAsInt();

            // number of samples
            numberOfSamples = rm.getByte();;

            // skip one byte
            rm.skipBytes(1);

            // instrument name
            instrumentName = rm.getByteString(26);

            // initial filter cutoff
            initialFilterCutoff = rm.getByte();;

            // initial filter resonance
            initialFilterResonance = rm.getByte();;

            // midi channel
            midiChannel = rm.getUByteAsShort();

            // midi program
            midiProgram = rm.getUByteAsShort();

            // midi bank
            midiBank = rm.getUShortAsInt();

            // note sample keyboard table
            noteSampleKeyboardTable = new short[120][2];

            // loop and add note sample keyboard tables
            for (int i = 0; i < 120; i++) {

                // note
                noteSampleKeyboardTable[i][0] = rm.getByte();;

                // sample
                noteSampleKeyboardTable[i][1] = rm.getByte();;
            }

            // 3 envelopes volume, pan and pitch/LPF
            boolean volumeEnvelopeRead, panEnvelopeRead, 
                    pitchFilterEnvelopeRead;
            
            // volume
            volumeEnvelope = new EnvelopeLayout(rm);
            
            volumeEnvelopeRead = volumeEnvelope.readEnvelope();
            
            if (!volumeEnvelopeRead) {
                throw new IOException("Volume envelope error");
            }
            
            // get read method back from volume envelope
            //rm = volumeEnvelope.getRM();
            
            // pan
            panEnvelope = new EnvelopeLayout(rm);
            
            panEnvelopeRead = panEnvelope.readEnvelope();
            
            if (!panEnvelopeRead) {
                throw new IOException("Pan envelope error");
            }
            
            // get read method back from pan envelope
            //rm = panEnvelope.getRM();
            
            // pitch filter
            pitchFilterEnvelope = new EnvelopeLayout(rm);
            
            pitchFilterEnvelopeRead = pitchFilterEnvelope.readEnvelope();
            
            if (!pitchFilterEnvelopeRead) {
                throw new IOException("Pitch filter envelope error");
            }
            
            // get read method back from pitch filter envelope
            //rm = pitchFilterEnvelope.getRM();
        }

        return success;
    }
    
    public boolean write() throws Exception {
        return true;
    }

    @Override
    public String toString() {

        // string builder
        StringBuilder sb = new StringBuilder();

        // output header
        sb.append("\nInstrunment[Header: ");
        sb.append(testCode);

        // output DOS filename
        sb.append("\nDOS filename: ");
        sb.append(dosFileName);

        if (oldFormat) {

        } else {

            // output new note action
            sb.append("\nNew note action: ");
            sb.append(newNoteAction);

            // output duplicate check type
            sb.append("\nDuplicate check type: ");
            sb.append(duplicateCheckType);

            // duplicate check action
            sb.append("\nDuplicate check action: ");
            sb.append(duplicateCheckAction);

            // fade out
            sb.append("\nFade out: ");
            sb.append(fadeOut);

            // pitch pan separiation
            sb.append("\nPitch pan separation: ");
            sb.append(pitchPanSeparation);

            // pitch pan centre
            sb.append("\nPitch pan centre: ");
            sb.append(duplicateCheckType);

            // global volume
            sb.append("\nGlobal Volume: ");
            sb.append(globalVolume);

            // default pan
            sb.append("\nDefault pan: ");
            sb.append(defaultPan);

            // random volume variation
            sb.append("\nRandom volume variation: ");
            sb.append(randomVolumeVariation);

            // random panning variation
            sb.append("\nRandom panning variation: ");
            sb.append(randomPanningVariation);

            // tracker version
            sb.append("\nTracker version: ");
            sb.append(trackerVersion);

            // number of samples
            sb.append("\nNo. of sample: ");
            sb.append(numberOfSamples);

            // instrument name
            sb.append("\nInstrument name: ");
            sb.append(instrumentName);

            // initial filter cutoff
            sb.append("\nInitial filter cutoff: ");
            sb.append(initialFilterCutoff);

            // initial filter resonance
            sb.append("\nInitial filter resonance: ");
            sb.append(initialFilterResonance);

            // midi channel
            sb.append("\nMIDI channel: ");
            sb.append(midiChannel);

            // midi program
            sb.append("\nMIDI program: ");
            sb.append(midiProgram);

            // midi bank
            sb.append("\nMIDI bank: ");
            sb.append(midiBank);

            // note sample keyboard table
            sb.append("\nNote sample Keyboard table: ");

            // loop and add note sample keyboard tables
            for (int i = 0; i < 120; i++) {

                // note
                sb.append("\nNote ");
                sb.append(i);
                sb.append(": ");
                sb.append(noteSampleKeyboardTable[i][0]);

                // sample
                sb.append("\nSample ");
                sb.append(i);
                sb.append(": ");
                sb.append(noteSampleKeyboardTable[i][1]);
            }
            
            // volume envelope
            sb.append("\nVolume envelope: ");
            sb.append(volumeEnvelope.toString());
            
            // pan envelope
            sb.append("\nPan envelope: ");
            sb.append(panEnvelope.toString());
            
            // Pitch filter envelope
            sb.append("\nPitch filter envelope: ");
            sb.append(pitchFilterEnvelope.toString());
        }

        return sb.toString();
    }
}
