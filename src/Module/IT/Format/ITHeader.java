/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module.IT.Format;

import io_stuff.ReadMethods;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Edward Jenkins
 */
public class ITHeader {

    // constants
    public static final String HEADER_CODE = "IMPM";  // The IT head tag
    public static final String PATTERN_NAME_CODE = "PNAM"; // the pattern name code
    public static final String CHANNEL_NAME_CODE = "CNAM"; // the channel name code

    // instance variables
    private String fileName;            // name of file
    private ReadMethods rm;             // read methods
    private String testHeader;          // used to check the header is valid
    private String songName;            // Name of song, can't be more tha 26 characters
    private short[] patternHilight;     // Pattern row hilight     
    private int orderNum;               // Number of orders in song
    private int instrumentNum;          // number of instruments
    private int sampleNum;              // number of samples
    private int patternNum;             // number of patterns
    private int createdWithTracker;     // tracker ID
    private int compatibleWithTracker;  // tracker campatibility number
    private int flags;                  // described below
    /**
     * bit 0: Channels, 1 = stereo, 0 = mono; bit 1: volume mix, 1 = no mixing,
     * 0 = volume at mixing time is 0; bit 2: voice source, 1 = instruments, 0 =
     * samples; bit 3: Slides, 1 = Linear slides, 0 = Amiga slides; bit 4:
     * Effects, 1 = old effects, 0 = IT effects; bit 5: 1 = like effect G's
     * memory with effects E and F; bit 6: 1 = use MIDI patch controller; bit 7:
     * 1 = request embedded MIDI configuration *
     */
    private boolean stereo;
    private boolean mix;
    private boolean instrumental;
    private boolean linearSlides;
    private boolean oldEffects;
    private boolean gLinkedWithEFMemory;
    private boolean midiPitchControlled;
    private boolean embeddedMidiConfiguration;
    private int special;
    /**
     * bit 0: 1 = Song message attached bit 1: Embedded edit history 2: Embedded
     * pattern hilight 3: Midi Configuration embedded bits 4-15 reserved
     */
    private boolean songMessageAttached;
    private boolean editHistoryEmbedded;
    private boolean hilightEmbedded;
    private boolean midiConfigurationEmbedded;
    private short globalVolume;             // 0-128
    private short mixVolume;                // 0-128
    private short initialSpeed;             // starting speed in ticks
    private short initialTempo;             // starting tempo in BPM
    private short panSeperation;            // 0-128 128 is maximum seperation
    private short pitchWheelDepth;          // for midi
    private int messageLength;
    private long messageOffset;             // offset in bytes
    private String headerReserved;          // used for version identification
    private String possibleCreationSoftware;    // show what software created this file
    private boolean interpretModPlugMade;       // use for OpenMPT mods.
    private short[] channelPan;             // pan for each channel 0 = left
    private short[] channelVolume;          // volume for each cnannel
    private short[] orders;                 // order of patterns played
    private long[] offsetOfInstruments;     // offset of instruments
    private long[] offsetOfSampleHeaders;   // offset of samples
    private long[] offsetOfPatterns;        // offset of patterns
    private boolean possiblyUnmo3;          // needed when finding out some data
    private String songMessage;             // message
    private int editHistoryParapointer;     // used to check for edit history
    private EditHistoryInfo[] editHistory;  // stores all the edit history dates
    private MidiMacros midiMacros;          // stores MIDI macros;
    private String testString;              // test for pattern or channel names
    private long patternNameLength;         // length of pattern name
    private PatternNames patternNames;      // stores pattern names
    private long channelNameLength;         // length of channel names
    private ChannelNames channelNames;      // channel names

    // constructor
    public ITHeader(String fileName) {
        this.fileName = fileName;
        interpretModPlugMade = false;
    }

    // getters
    public String getFileName() {
        return fileName;
    }

    public String getSongName() {
        return songName;
    }

    public String getTestHeader() {
        return testHeader;
    }

    public short[] getPatternHilight() {
        return patternHilight;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public int getInstrumentNum() {
        return instrumentNum;
    }

    public int getSampleNum() {
        return sampleNum;
    }

    public int getPatternNum() {
        return patternNum;
    }

    public int getCreatedWithTracker() {
        return createdWithTracker;
    }

    public int getCompatibleWithTracker() {
        return compatibleWithTracker;
    }

    public int getFlags() {
        return flags;
    }

    public boolean isStereo() {
        return stereo;
    }

    public boolean isMix() {
        return mix;
    }

    public boolean isInstrumental() {
        return instrumental;
    }

    public boolean isLiniearSlides() {
        return linearSlides;
    }

    public boolean isOldEffects() {
        return oldEffects;
    }

    public boolean isGLinkedWithEFMemory() {
        return gLinkedWithEFMemory;
    }

    public boolean isMidiPitchControlled() {
        return midiPitchControlled;
    }

    public boolean isEmbeddedMidiConfiguration() {
        return embeddedMidiConfiguration;
    }

    public int getSpecial() {
        return special;
    }

    public boolean isSongMessageAttached() {
        return songMessageAttached;
    }

    public boolean isMidiConfigurationEmbedded() {
        return midiConfigurationEmbedded;
    }

    public boolean isEditHistoryEmbedded() {
        return editHistoryEmbedded;
    }

    public boolean isHilightEmbedded() {
        return hilightEmbedded;
    }

    public short getGlobalVolume() {
        return globalVolume;
    }

    public short getMixVolume() {
        return mixVolume;
    }

    public short getInitialSpeed() {
        return initialSpeed;
    }

    public short getInitialTempo() {
        return initialTempo;
    }

    public short getPanSeperation() {
        return panSeperation;
    }

    public short getPitchWheelDepth() {
        return pitchWheelDepth;
    }

    public int getMessageLength() {
        return messageLength;
    }

    public long getMessageOffset() {
        return messageOffset;
    }

    public short[] getChannelPan() {
        return channelPan;
    }

    public short[] getChannelVolume() {
        return channelVolume;
    }

    public short[] getOrders() {
        return orders;
    }

    public long[] getOffsetOfInstruments() {
        return offsetOfInstruments;
    }

    public long[] getOffsetOfSampleHeaders() {
        return offsetOfSampleHeaders;
    }

    public long[] getOffsetOfPatterns() {
        return offsetOfPatterns;
    }

    public int getEditHistoryParapointer() {
        return editHistoryParapointer;
    }

    public EditHistoryInfo[] getEditHistory() {
        return editHistory;
    }

    public String getSongMessage() {
        return this.songMessage;
    }

    public String getPossibleCreationSoftware() {
        return possibleCreationSoftware;
    }

    public boolean isInterpretModPlugMade() {
        return interpretModPlugMade;
    }

    public MidiMacros getMIDIMacros() {
        return midiMacros;
    }

    public long getPatternNameLength() {
        return patternNameLength;
    }

    public PatternNames getPatternNames() {
        return patternNames;
    }

    public long getChannelNameLength() {
        return channelNameLength;
    }

    public ChannelNames getChannelNames() {
        return channelNames;
    }

    // read method
    public boolean read() throws IOException {

        // read methods
        rm = new ReadMethods(fileName, true); // IT format is in Little Endian

        // success boolean
        boolean success = true;

        // header code
        testHeader = rm.getByteString(4);

        if (!(testHeader.equals(HEADER_CODE))) {
            throw new IOException("Header is not valid! ");
        }

        // song name
        songName = rm.getByteString(26);
        
        // pattern highligt
        patternHilight = new short[2];
        patternHilight[0] = rm.getUByteAsShort();
        patternHilight[1] = rm.getUByteAsShort();

        // order number
        orderNum = rm.getUShortAsInt();

        // instrument number
        instrumentNum = rm.getUShortAsInt();

        // sample number
        sampleNum = rm.getUShortAsInt();

        // pattern number
        patternNum = rm.getUShortAsInt();

        // created with tracker
        createdWithTracker = rm.getUShortAsInt();

        // compatible with tracker
        compatibleWithTracker = rm.getUShortAsInt();

        // flags
        flags = rm.getUShortAsInt();

        // set flag values
        // stereo or mono
        stereo = (flags & 1) == 1;

        // mix
        mix = ((flags >>> 1) & 1) == 1;

        // instruments
        instrumental = ((flags >>> 2) & 1) == 1;

        // linear slides
        linearSlides = ((flags >>> 3) & 1) == 1;

        // old effects
        oldEffects = ((flags >>> 4) & 1) == 1;

        // link effect G to E and F
        gLinkedWithEFMemory = ((flags >>> 5) & 1) == 1;

        // midi pitch controller
        midiPitchControlled = ((flags >>> 6) & 1) == 1;

        // request embedded MIDI configruation
        embeddedMidiConfiguration = ((flags >>> 7) & 1) == 1;

        // special
        special = rm.getUShortAsInt();

        // set special values
        // song message
        songMessageAttached = (special & 1) == 1;

        // edit history embedded
        editHistoryEmbedded = ((special >>> 1) & 1) == 1;

        // hilight embedded
        hilightEmbedded = ((special >>> 2) & 1) == 1;

        // midi configuration embedded
        midiConfigurationEmbedded = ((special >>> 3) & 1) == 1;

        // global volume
        globalVolume = rm.getUByteAsShort();

        // mix volume
        mixVolume = rm.getUByteAsShort();

        // initial speed
        initialSpeed = rm.getUByteAsShort();

        // initial tempo
        initialTempo = rm.getUByteAsShort();

        // pan seperation
        panSeperation = rm.getUByteAsShort();

        // pitch wheel depth
        pitchWheelDepth = rm.getUByteAsShort();

        // message length
        messageLength = rm.getUShortAsInt();

        // message offset
        messageOffset = rm.getUIntAsLong();

        // read 4 reserved bytes as a string
        headerReserved = rm.getByteString(4);

        // channel pan values
        channelPan = new short[64];

        // extract channel pan bytes
        for (int i = 0; i < channelPan.length; i++) {

            // extract byte
            channelPan[i] = rm.getUByteAsShort();
        }

        // channel volume values
        channelVolume = new short[64];

        // extract channel pan bytes
        for (int i = 0; i < channelVolume.length; i++) {

            // extract byte
            channelVolume[i] = rm.getUByteAsShort();
        }

        // extract orders
        orders = new short[orderNum];

        // extract order bytes
        for (int i = 0; i < orders.length; i++) {

            // extract byte
            orders[i] = rm.getUByteAsShort();
        }

        // extract instrument offsets
        offsetOfInstruments = new long[instrumentNum];

        // extract instruments
        for (int i = 0; i < offsetOfInstruments.length; i++) {

            // extract bytes
            offsetOfInstruments[i] = rm.getUIntAsLong();
        }

        // extract sample header offsets
        offsetOfSampleHeaders = new long[sampleNum];

        // extract samples
        for (int i = 0; i < offsetOfSampleHeaders.length; i++) {

            // extract bytes
            offsetOfSampleHeaders[i] = rm.getUIntAsLong();
        }

        // extract pattern offsets
        offsetOfPatterns = new long[patternNum];

        // extract patterns
        for (int i = 0; i < offsetOfPatterns.length; i++) {

            // extract bytes
            offsetOfPatterns[i] = rm.getUIntAsLong();
        }

        // find first parapointer
        long minPointer = (long) Math.pow(2, 32) - 1;

        // loop instrument pointers
        for (long instrumentPointer : offsetOfInstruments) {
            if (instrumentPointer > 0 && instrumentPointer < minPointer) {
                minPointer = instrumentPointer;
            }
        }
        // loop sample pointers
        for (long samplePointer : offsetOfSampleHeaders) {
            if (samplePointer > 0 && samplePointer < minPointer) {
                minPointer = samplePointer;
            }
        }
        // loop pattern pointers
        for (long patternPointer : offsetOfPatterns) {
            if (patternPointer > 0 && patternPointer < minPointer) {
                minPointer = patternPointer;
            }
        }
        if (songMessageAttached) {

            minPointer = Math.min(minPointer, messageOffset);
        }

        // which tracker possibly made this
        // Impulse tracker
        if (createdWithTracker <= 0x0217) {
            String version = Integer.toHexString(createdWithTracker);
            if (createdWithTracker <= 0x214) {
                version = version.substring(0, 1) + "." + version.substring(1);
            } else {
                version = "2.14p" + (createdWithTracker - 0x214);
            }
            possibleCreationSoftware = "Impulse Tracker " + version;
        } // Schism tracker
        else if ((createdWithTracker & 0xFFF) > 0x050) {
            possibleCreationSoftware = "Schism Tracker";
        }
        // Open MPT
        if ((createdWithTracker & 0xF000) == 0x5000) {

            // Mod Plug
            String version = Integer.toHexString(createdWithTracker & 0x0FFF);
            version = version.substring(0, 1) + "." + version.substring(1);
            possibleCreationSoftware = "OpenMPT " + version;
            if (headerReserved.equals("OMPT")) {
                interpretModPlugMade = true;
            }
        } else if (compatibleWithTracker == 0x888
                || createdWithTracker == 0x888) {

            // OpenMPT 1.17.02.26-1.18
            possibleCreationSoftware = "OpenMPT 1.17.02.26-1.18";
            interpretModPlugMade = true;
        } else if (createdWithTracker == 0x0217
                && compatibleWithTracker == 0x0200
                && headerReserved.equals("")) {
            String channelPanValues = "";
            char achar;
            for (short channelPanValue : channelPan) {
                achar = (char) channelPanValue;
                channelPanValues += achar;
            }
            if (channelPanValues.indexOf((char) 0xFF) == -1) {
                possibleCreationSoftware = "ModPlug Tracker 1.09-1.16";
            } else {
                possibleCreationSoftware = "OpenMPT 1.17";
            }
            interpretModPlugMade = true;
        } else if (createdWithTracker == 0x0214
                && compatibleWithTracker == 0x0202
                && headerReserved.equals("")) {

            // Mod plug b3.3 to 1.09
            possibleCreationSoftware = "ModPlug Tracker b3.3-1.09";
            interpretModPlugMade = true;
        } else if (createdWithTracker == 0x0300
                && compatibleWithTracker == 0x0300
                && headerReserved.equals("")
                && orderNum == 256
                && panSeperation == 128
                && pitchWheelDepth == 0) {

            // rare OpenMPT 1.17.02.20
            possibleCreationSoftware = "OpenMPT 1.17.02.20";
            interpretModPlugMade = true;
        }
        // UNMO3
        if (compatibleWithTracker == 0x0214
                && (createdWithTracker == 0x0214 || createdWithTracker == 0)
                && (patternHilight[0] == 0 && patternHilight[1] == 0)
                && pitchWheelDepth == 0 && headerReserved.equals("")
                && !midiConfigurationEmbedded
                && !embeddedMidiConfiguration) {
            this.possiblyUnmo3 = true;

            if (instrumentNum == 0 && sampleNum > 0
                    && rm.getFilePosition() * offsetOfSampleHeaders.length + 2
                    <= minPointer) {
                boolean oldUNMO = true;
                for (int i = 0; i < sampleNum; i++) {
                    if (rm.getInt() != 0) {
                        oldUNMO = false;
                        rm.setFilePosition(rm.getFilePosition() - (4 + i * 4));
                    }
                }
                if (oldUNMO) {
                    possibleCreationSoftware = "UNMO3 <= 2.4";
                }
            }
            if (createdWithTracker == 0) {
                possibleCreationSoftware = "UNMO3 0/1";
            }
        }

        // check if there really is embedded editing
        if (editHistoryEmbedded) {

            // read first parapointer
            editHistoryParapointer = rm.getUShortAsInt();

            if (rm.available() >= editHistoryParapointer * 8
                    && (rm.getFilePosition() + editHistoryParapointer 
                    * 8 <= minPointer)) {

                int fatDate, fatTime;
                long runTime;

                editHistory = new EditHistoryInfo[editHistoryParapointer];

                // extract history
                for (int i = 0; i < editHistory.length; i++) {
                    // fat date
                    fatDate = rm.getUShortAsInt();

                    // fat time
                    fatTime = rm.getUShortAsInt();

                    // run time
                    runTime = rm.getUIntAsLong();

                    editHistory[i]
                            = new EditHistoryInfo(fatDate, fatTime, runTime);
                }

                if (possiblyUnmo3 && editHistoryParapointer == 0) {
                    if (editHistoryEmbedded) {
                        possibleCreationSoftware = "UNMO3 <= 2.4.0.1";
                    } else {
                        possibleCreationSoftware = "UNMO3";
                    }
                }

            } else {
                rm.setFilePosition(rm.getFilePosition() - 2);
            }
        } else if (possiblyUnmo3 && special <= 1) {

            // read first to check it is 0
            if (rm.getShort() == 0) {
                editHistoryParapointer = 0;
                possibleCreationSoftware = "UNMO3 <= 2.4";
            } else {
                rm.setFilePosition(rm.getFilePosition() - 2);
                possibleCreationSoftware = "CheeseTracker";
            }
        }

        // midi macro
        if (embeddedMidiConfiguration || midiConfigurationEmbedded) {

            // read MIDI macros
            String[] global, parametric, fixed;

            // read global macros
            global = new String[9];

            for (int i = 0; i < global.length; i++) {

                // extract string
                global[i] = rm.getByteString(32);
            }

            // read global macros
            parametric = new String[16];

            for (int i = 0; i < parametric.length; i++) {

                // extract string
                parametric[i] = rm.getByteString(32);
            }

            // read global macros
            fixed = new String[128];

            for (int i = 0; i < fixed.length; i++) {

                // extract string
                fixed[i] = rm.getByteString(32);
            }

            if (createdWithTracker >= 0x214) {
                midiMacros = new MidiMacros(global, parametric, fixed);
            }
        }

        // test string for PNAM
        testString = rm.getByteString(4);

        // check for pattern names
        if (testString.equals(PATTERN_NAME_CODE)) {

            // read length
            patternNameLength = rm.getUIntAsLong();

            // set string array to hold pattern names
            String[] patternNameStrings
                    = new String[(int) (patternNameLength / 32)];

            // get the strings
            for (int i = 0; i < patternNameStrings.length; i++) {

                // extract channel name
                patternNameStrings[i] = rm.getByteString(32);
            }

            // set channelNames object
            patternNames = new PatternNames(patternNameStrings);
        } else {

            // reset back 4 spaces
            rm.setFilePosition(rm.getFilePosition() - 4);
        }

        // test string for CNAM
        testString = rm.getByteString(4);

        // check for channel names
        if (testString.equals(CHANNEL_NAME_CODE)) {

            // read length
            channelNameLength = rm.getUIntAsLong();

            // set string array to hold channel names
            String[] channelNameStrings
                    = new String[(int) (channelNameLength / 20)];

            // get the strings
            for (int i = 0; i < channelNameStrings.length; i++) {

                // extract channel name
                channelNameStrings[i] = rm.getByteString(20);
            }

            // set channelNames object
            channelNames = new ChannelNames(channelNameStrings);
        } else {

            // reset back 4 spaces
            rm.setFilePosition(rm.getFilePosition() - 4);
        }

        // check for plugin information
        //TODO
        
        // read the message
        if (songMessageAttached) {
            
            rm.setFilePosition(messageOffset);

            // message
            songMessage = rm.getByteString(messageLength);
        }

        // successfully read header
        return success;
    }

    // To String
    @Override
    public String toString() {

        // string builder
        StringBuilder aStringBuilder = new StringBuilder();

        aStringBuilder.append("Header [");

        // read header information
        aStringBuilder.append("\nHeader Code: ");
        aStringBuilder.append(getTestHeader());

        // read song name
        aStringBuilder.append("\nSong name: ");
        aStringBuilder.append(getSongName());

        // hilighted pattern
        aStringBuilder.append("\nPattern hilight: ");
        aStringBuilder.append(getPatternHilight());

        // order number
        aStringBuilder.append("\nOrder number: ");
        aStringBuilder.append(getOrderNum());

        // instrument number
        aStringBuilder.append("\nInstrument No. ");
        aStringBuilder.append(getInstrumentNum());

        // sample number
        aStringBuilder.append("\nSample No. ");
        aStringBuilder.append(getSampleNum());

        // pattern number
        aStringBuilder.append("\nPattern No. ");
        aStringBuilder.append(getPatternNum());

        // flags
        aStringBuilder.append("\nFlags: ");

        // created with tracker
        aStringBuilder.append("\nCreated with Tracker: ");
        aStringBuilder.append(getCreatedWithTracker());

        // compatible with tracker
        aStringBuilder.append("\nCompatible with Tracker: ");
        aStringBuilder.append(getCompatibleWithTracker());

        // mix
        aStringBuilder.append("\nMix? ");
        aStringBuilder.append(isMix());

        // instruments
        aStringBuilder.append("\nInstruments? ");
        aStringBuilder.append(isInstrumental());

        // linear slides
        aStringBuilder.append("\nLiniar slides? ");
        aStringBuilder.append(isLiniearSlides());

        // old effects
        aStringBuilder.append("\nUse old effects? ");
        aStringBuilder.append(isOldEffects());

        // link effect G to E and F
        aStringBuilder.append("\nLinke G to E and F? ");
        aStringBuilder.append(isGLinkedWithEFMemory());

        // midi pitch controller
        aStringBuilder.append("\nMIDI pitch controller? ");
        aStringBuilder.append(isMidiPitchControlled());

        // request embedded MIDI configruation
        aStringBuilder.append("\nRequest embedded MIDI configuration? ");
        aStringBuilder.append(isEmbeddedMidiConfiguration());

        // special
        aStringBuilder.append("\nSpecial: ");

        // Message attached
        aStringBuilder.append("\nMessage attached? ");
        aStringBuilder.append(isSongMessageAttached());

        // midi configurration embedded
        aStringBuilder.append("\nMIDI configuration embedded? ");
        aStringBuilder.append(isMidiConfigurationEmbedded());

        // global volume
        aStringBuilder.append("\nGlobal volume: ");
        aStringBuilder.append(getGlobalVolume());

        // mix volume
        aStringBuilder.append("\nMix volume: ");
        aStringBuilder.append(getMixVolume());

        // initial speed
        aStringBuilder.append("\nInitial speed: ");
        aStringBuilder.append(getInitialSpeed());

        // initial tempo
        aStringBuilder.append("\nInitial tempo: ");
        aStringBuilder.append(getInitialTempo());

        // pan seperation
        aStringBuilder.append("\nPan seperation: ");
        aStringBuilder.append(getPanSeperation());

        // pitch wheel depth
        aStringBuilder.append("\nPitch wheel depth: "
        );
        aStringBuilder.append(getPitchWheelDepth());

        // message length
        aStringBuilder.append("\nMessage length: ");
        aStringBuilder.append(getMessageLength());

        // message offset
        aStringBuilder.append("\nMessage Offset: ");
        aStringBuilder.append(getMessageOffset());

        // pan values
        aStringBuilder.append("\nPan values");

        short[] channelPanValues = getChannelPan();

        for (int i = 0; i < channelPanValues.length; i++) {
            aStringBuilder.append("\nChannel: ");
            aStringBuilder.append(i);
            aStringBuilder.append("\tPan: ");
            aStringBuilder.append(channelPanValues[i]);
        }

        // volume values
        aStringBuilder.append("\nVolume values");

        short[] volumePanValues = getChannelVolume();

        for (int i = 0; i < channelPanValues.length; i++) {
            aStringBuilder.append("\nChannel: ");
            aStringBuilder.append(i);
            aStringBuilder.append("\tVolume: ");
            aStringBuilder.append(channelPanValues[i]);
        }

        // orders
        aStringBuilder.append("\nOrders");

        short[] orders = getOrders();

        for (int i = 0; i < orders.length; i++) {
            aStringBuilder.append("\nOrder: ");
            aStringBuilder.append(i);
            aStringBuilder.append("\tPattern: ");
            aStringBuilder.append(orders[i]);
        }

        // Instrument offsets
        aStringBuilder.append("\nInstruments");

        long[] instrumentOffsets = getOffsetOfInstruments();

        for (int i = 0; i < instrumentOffsets.length; i++) {
            aStringBuilder.append("\nInstrument: ");
            aStringBuilder.append(i);
            aStringBuilder.append("\tOffset: ");
            aStringBuilder.append(instrumentOffsets[i]);
        }

        // Sample offsets
        aStringBuilder.append("\nSamples");

        long[] sampleOffsets = getOffsetOfSampleHeaders();

        for (int i = 0; i < sampleOffsets.length; i++) {
            aStringBuilder.append("\nSample: ");
            aStringBuilder.append(i);
            aStringBuilder.append("\tOffset: ");
            aStringBuilder.append(sampleOffsets[i]);
        }

        // pattern offsets
        aStringBuilder.append("\nPatterns");

        long[] patternOffsets = offsetOfPatterns;

        for (int i = 0; i < patternOffsets.length; i++) {
            aStringBuilder.append("\nPattern: ");
            aStringBuilder.append(i);
            aStringBuilder.append(" \tOffset: ");
            aStringBuilder.append(patternOffsets[i]);
        }

        // possible tracker version
        aStringBuilder.append("\nPossibly made with: ");
        aStringBuilder.append(possibleCreationSoftware);

        // interpret mod plug made
        aStringBuilder.append("\nInterpret mod made? ");
        aStringBuilder.append(interpretModPlugMade);

        // editHistory
        aStringBuilder.append("\nEdit History: ");
        if (editHistory != null) {
            for (EditHistoryInfo history : this.editHistory) {
                aStringBuilder.append("\n");
                aStringBuilder.append(history);
            }
        } else {
            aStringBuilder.append("\nnull");
        }

        // midi macros
        aStringBuilder.append("\n");
        aStringBuilder.append(midiMacros);

        // pattern names
        aStringBuilder.append("\nPattern Names: ");
        aStringBuilder.append(patternNames);

        // channel names
        aStringBuilder.append("\nChannel Names: ");
        aStringBuilder.append(channelNames);

        // Song message
        aStringBuilder.append("\nSong message: ");
        aStringBuilder.append(this.songMessage);

        aStringBuilder.append("]");

        return aStringBuilder.toString();
    }
}
