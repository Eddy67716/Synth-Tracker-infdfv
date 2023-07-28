/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.it.format;

import io.IReadable;
import module.IInstrument;
import io.Reader;
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
    public static final short INSTRUMENT_LENGTH = 554;

    // instance variables
    // name of file
    private String fileName;
    // where to start reading the file
    private long offsetToInstrument;
    // used to define which instrument type is used
    private boolean oldFormat;              
    // if > 0x200, use new format
    // test code with default code
    private String testCode;
    // file name
    private String dosFileName;             
    // old format
    // flags for old format
    private short flags;                    
    private boolean volumeEnvelopeEnabled;
    private boolean volumeLoopEnabled;
    private boolean sustainVolumeLoopEnabled;
    // node of volume start
    private short volumeLoopStart;
    // node
    private short volumeLoopEnd;            
    private short sustainLoopStart;
    private short sustainLoopEnd;
    // check for duplicate notes
    private boolean duplicateNoteCheck;
    // stored in instrument if old format
    private short[] volumeEnvelopeNodes;    
    // new format
    // command on note
    private byte newNoteAction;
    // duplication check
    private byte duplicateCheckType;
    // action on duplication
    private byte duplicateCheckAction;
    // note fade out
    private int fadeOut;
    // sepperation
    private byte pitchPanSeparation;
    // centre of pitch pan
    private byte pitchPanCentre;
    // global vomume
    private short globalVolume;
    // default pan
    private short defaultPan;               
    private byte panValue;
    private boolean panning;
    // percentage
    private byte randomVolumeVariation;
    // percentage
    private byte randomPanningVariation;
    // only used in instrument files    
    private int trackerVersion;
    // samples used in instrument
    private byte numberOfSamples;
    // 26 characters including null
    private String instrumentName;
    // filter cuttoff
    private short initialFilterCutoff;
    // resonance
    private short initialFilterResonance;
    // MIDI channel to use
    private short midiChannel;
    // MIDI instrument to use
    private short midiProgram;
    // --------
    private int midiBank;
    // stores sample mapping
    private short[][] noteSampleKeyboardTable;
    // stores volume envelope
    private EnvelopeLayout volumeEnvelope;
    // stores pan envelope
    private EnvelopeLayout panEnvelope;
    // stores pitch/filter envelope
    private EnvelopeLayout pitchFilterEnvelope; 
    // dummy bytes for new format(7 if between 2.0 and 2.14p2 and 8 if otherwise)
    private byte[] dummyBytes;

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

    @Override
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

    @Override
    public byte getNewNoteAction() {
        return newNoteAction;
    }

    @Override
    public byte getDuplicateCheckType() {
        return duplicateCheckType;
    }

    @Override
    public byte getDuplicateCheckAction() {
        return duplicateCheckAction;
    }

    @Override
    public int getFadeOut() {
        return fadeOut;
    }

    @Override
    public byte getPitchPanSeparation() {
        return pitchPanSeparation;
    }

    @Override
    public byte getPitchPanCentre() {
        return pitchPanCentre;
    }

    @Override
    public short getGlobalVolume() {
        return globalVolume;
    }

    public short getDefaultPan() {
        return defaultPan;
    }

    @Override
    public byte getPanValue() {
        return panValue;
    }

    @Override
    public boolean isPanning() {
        return panning;
    }

    @Override
    public byte getRandomVolumeVariation() {
        return randomVolumeVariation;
    }

    @Override
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

    @Override
    public short getInitialFilterCutoff() {
        return initialFilterCutoff;
    }

    @Override
    public short getInitialFilterResonance() {
        return initialFilterResonance;
    }

    @Override
    public short getMidiChannel() {
        return midiChannel;
    }

    @Override
    public short getMidiProgram() {
        return midiProgram;
    }

    @Override
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
    
    @Override
    public NodePoint[] getVolumeEnvelopePoints() {
        return volumeEnvelope.getNodePoints();
    }

    @Override
    public NodePoint[] getPanEnvelopePoints() {
        return panEnvelope.getNodePoints();
    }

    @Override
    public NodePoint[] getPitchEnvelopePoints() {
        return pitchFilterEnvelope.getNodePoints();
    }
    
    // setters
    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setOffsetToInstrument(long offsetToInstrument) {
        this.offsetToInstrument = offsetToInstrument;
    }

    @Override
    public void setDosFileName(String dosFileName) {
        this.dosFileName = dosFileName;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }

    @Override
    public void setDuplicateNoteCheck(boolean duplicateNoteCheck) {
        this.duplicateNoteCheck = duplicateNoteCheck;
    }

    @Override
    public void setNewNoteAction(byte newNoteAction) {
        this.newNoteAction = newNoteAction;
    }

    @Override
    public void setDuplicateCheckType(byte duplicateCheckType) {
        this.duplicateCheckType = duplicateCheckType;
    }

    @Override
    public void setDuplicateCheckAction(byte duplicateCheckAction) {
        this.duplicateCheckAction = duplicateCheckAction;
    }

    @Override
    public void setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
    }

    @Override
    public void setPitchPanSeparation(byte pitchPanSeparation) {
        this.pitchPanSeparation = pitchPanSeparation;
    }

    @Override
    public void setPitchPanCentre(byte pitchPanCentre) {
        this.pitchPanCentre = pitchPanCentre;
    }

    @Override
    public void setGlobalVolume(short globalVolume) {
        this.globalVolume = globalVolume;
    }

    public void setDefaultPan(short defaultPan) {
        this.defaultPan = defaultPan;
    }

    @Override
    public void setPanValue(byte panValue) {
        this.panValue = panValue;
    }

    @Override
    public void setPanning(boolean panning) {
        this.panning = panning;
    }

    @Override
    public void setRandomVolumeVariation(byte randomVolumeVariation) {
        this.randomVolumeVariation = randomVolumeVariation;
    }

    @Override
    public void setRandomPanningVariation(byte randomPanningVariation) {
        this.randomPanningVariation = randomPanningVariation;
    }

    @Override
    public void setNumberOfSamples(byte numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
    }

    @Override
    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    @Override
    public void setInitialFilterCutoff(short initialFilterCutoff) {
        this.initialFilterCutoff = initialFilterCutoff;
    }

    @Override
    public void setInitialFilterResonance(short initialFilterResonance) {
        this.initialFilterResonance = initialFilterResonance;
    }

    @Override
    public void setMidiChannel(short midiChannel) {
        this.midiChannel = midiChannel;
    }

    @Override
    public void setMidiProgram(short midiProgram) {
        this.midiProgram = midiProgram;
    }

    @Override
    public void setMidiBank(int midiBank) {
        this.midiBank = midiBank;
    }

    public boolean read() throws IOException {

        // reader
        IReadable reader = new Reader(fileName, true);
        
        // set input stream to read at offset
        reader.skipBytes(offsetToInstrument);

        // success boolean
        boolean success = true;

        // header code
        testCode = reader.getByteString(4);

        if (!(testCode.equals(INSTRUMENT_CODE))) {
            throw new IOException("Header is not valid! ");
        }

        // dos file name
        dosFileName = reader.getByteString(12);

        // skip reserved byte
        reader.getByte();

        // figure out the if the format is the old or new one
        if (oldFormat) {

            // read old instrument
            // flags
            flags = reader.getUByteAsShort();

            // use volume envelope
            volumeEnvelopeEnabled = (flags & 1) == 1;

            // use volume loop
            volumeLoopEnabled = ((flags >>> 1) & 1) == 1;

            // use sustain volume loop
            sustainVolumeLoopEnabled = ((flags >>> 2) & 1) == 1;

            // volume loop start
            volumeLoopStart = reader.getUByteAsShort();

            // volume loop end
            volumeLoopEnd = reader.getUByteAsShort();

            // sustain loop start
            sustainLoopStart = reader.getUByteAsShort();

            // sustain loop end
            sustainLoopEnd = reader.getUByteAsShort();

            // skip two bytes
            reader.skipBytes(2);

            // fade out
            fadeOut = reader.getUShortAsInt();

            // new note action
            newNoteAction = reader.getByte();

            // duplicate note check
            duplicateNoteCheck = reader.getBoolean();

            // tracker version
            trackerVersion = reader.getUShortAsInt();

            // number of samples
            numberOfSamples = reader.getByte();

            // instrument name
            instrumentName = reader.getByteString(26);

            // skip 6 bytes
            reader.skipBytes(6);

            // note sample keyboard table
            noteSampleKeyboardTable = new short[120][2];

            // loop and add note sample keyboard tables
            for (int i = 0; i < 120; i++) {

                // note
                noteSampleKeyboardTable[i][0] = reader.getByte();

                // sample
                noteSampleKeyboardTable[i][1] = reader.getByte();
            }

            // volume envelope
        } else {

            // read new instrument
            // new note actions
            newNoteAction = reader.getByte();

            // duplicate check type
            duplicateCheckType = reader.getByte();

            // duplicate check action
            duplicateCheckAction = reader.getByte();

            // fade out
            fadeOut = reader.getUShortAsInt();

            // pitch pan separiation
            pitchPanSeparation = reader.getByte();

            // pitch pan centre
            pitchPanCentre = reader.getByte();

            // global volume
            globalVolume = reader.getUByteAsShort();

            // default pan
            defaultPan = reader.getByte();
            
            panValue = (byte) (defaultPan & 127);

            panning = (defaultPan & 128) == 128;

            // random volume variation
            randomVolumeVariation = reader.getByte();

            // random panning variation
            randomPanningVariation = reader.getByte();

            // tracker version
            trackerVersion = reader.getUShortAsInt();

            // number of samples
            numberOfSamples = reader.getByte();

            // skip one byte
            reader.skipBytes(1);

            // instrument name
            instrumentName = reader.getByteString(26);

            // initial filter cutoff
            initialFilterCutoff = reader.getByte();

            // initial filter resonance
            initialFilterResonance = reader.getByte();

            // midi channel
            midiChannel = reader.getUByteAsShort();

            // midi program
            midiProgram = reader.getUByteAsShort();

            // midi bank
            midiBank = reader.getUShortAsInt();

            // note sample keyboard table
            noteSampleKeyboardTable = new short[120][2];

            // loop and add note sample keyboard tables
            for (int i = 0; i < 120; i++) {

                // note
                noteSampleKeyboardTable[i][0] = reader.getByte();

                // sample
                noteSampleKeyboardTable[i][1] = reader.getByte();
            }

            // 3 envelopes volume, pan and pitch/LPF
            boolean volumeEnvelopeRead, panEnvelopeRead, 
                    pitchFilterEnvelopeRead;
            
            // volume
            volumeEnvelope = new EnvelopeLayout();
            
            volumeEnvelopeRead = volumeEnvelope.read(reader);
            
            if (!volumeEnvelopeRead) {
                throw new IOException("Volume envelope error");
            }
            
            // pan
            panEnvelope = new EnvelopeLayout();
            
            panEnvelopeRead = panEnvelope.read(reader);
            
            if (!panEnvelopeRead) {
                throw new IOException("Pan envelope error");
            }
            
            // pitch filter
            pitchFilterEnvelope = new EnvelopeLayout();
            
            pitchFilterEnvelopeRead = pitchFilterEnvelope.read(reader);
            
            if (!pitchFilterEnvelopeRead) {
                throw new IOException("Pitch filter envelope error");
            }
            
            // dummy bytes
            dummyBytes = reader.getBytes(4);
        }

        return success;
    }
    
    public boolean write() throws Exception {
        return true;
    }
    
    public int length() {
        return INSTRUMENT_LENGTH;
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
