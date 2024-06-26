/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.it.format;

import io.IReadable;
import io.IWritable;
import io.Reader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import module.EditHistoryTime;

/**
 *
 * @author Edward Jenkins
 */
public class ITHeader extends EditHistoryTime {

    // constants
    // The IT head tag
    public static final String HEADER_CODE = "IMPM";
    // the pattern name code
    public static final String PATTERN_NAME_CODE = "PNAM";
    // the channel name code
    public static final String CHANNEL_NAME_CODE = "CNAM";
    // then known header length
    public static final short KNOWN_HEADER_LENGTH = 192;
    // length of each edit history    
    public static final byte EDIT_HISTORY_LENGTH = 8;
    // length of MIDI macros
    public static final short MIDI_MACROS_LENGTH = 4896;
    // length of pattern names
    public static final byte PATTERN_NAME_LENGTH = 32;
    // length of channel names
    public static final byte CHANNEL_NAME_LENGTH = 20;
    // synth tracker create
    public static final short ST_COMPATIBLE = 0x3000;
    // flags
    public static final int STEREO_FLAG = 0b1;
    public static final int MIX_FLAG = 0b10;
    public static final int INSTRUMENTAL_FLAG = 0b100;
    public static final int LINEAR_SLIDE_FLAG = 0b1000;
    public static final int OLD_EFFECTS_FLAG = 0b10000;
    public static final int GEF_LINK_FLAG = 0b100000;
    public static final int MIDI_CONTROLLER_FLAG = 0b1000000;
    public static final int EMBEDDED_MIDI_CONFIG_FLAG = 0b10000000;
    public static final int EXTENDED_FILTER_FLAG = 0b100000000;
    // special flags
    public static final int SONG_MESSAGE_FLAG = 0b1;
    public static final int EMBEDDED_EDIT_HISTORY_FLAG = 0b10;
    public static final int EMBEDDED_HILIGHT_FLAG = 0b100;
    public static final int MIDI_CONFIG_EMBEDDED_FLAG = 0b1000;

    // instance variables
    // name of file
    private String fileName;
    // used to check the header is valid
    private String testHeader;
    // Name of song, can't be more tha 26 characters
    private String songName;
    // Pattern row hilight   
    private short[] patternHilight;
    // Number of orders in song
    private int orderNum;
    // number of instruments
    private int instrumentNum;
    // number of samples
    private int sampleNum;
    // number of patterns
    private int patternNum;
    // tracker ID
    private int createdWithTracker;
    // tracker campatibility number
    private int compatibleWithTracker;
    /**
     * bit 0: Channels, 1 = stereo, 0 = mono; bit 1: volume mix, 1 = no mixing,
     * 0 = volume at mixing time is 0; bit 2: voice source, 1 = instruments, 0 =
     * samples; bit 3: Slides, 1 = Linear slides, 0 = Amiga slides; bit 4:
     * Effects, 1 = old effects, 0 = IT effects; bit 5: 1 = like effect G's
     * memory with effects E and F; bit 6: 1 = use MIDI patch controller; bit 7:
     * 1 = request embedded MIDI configuration In Open MPT bit 15 is extended
     * filter range;
     */
    private int flags;
    private boolean stereo;
    private boolean mix;
    private boolean instrumental;
    private boolean linearSlides;
    private boolean oldEffects;
    private boolean gLinkedWithEFMemory;
    private boolean midiPitchControlled;
    private boolean embeddedMidiConfiguration;
    private boolean filterRangeExtended;
    /**
     * bit 0: 1 = Song message attached bit 1: Embedded edit history 2: Embedded
     * pattern hilight 3: Midi Configuration embedded bits 4-15 reserved
     *
     */
    private int special;
    private boolean songMessageAttached;
    private boolean editHistoryEmbedded;
    private boolean hilightEmbedded;
    private boolean midiConfigurationEmbedded;
    // 0-128
    private short globalVolume;
    // 0-128
    private short mixVolume;
    // starting speed in ticks
    private short initialSpeed;
    // starting tempo in BPM
    private short initialTempo;
    // 0-128 128 is maximum seperation
    private short panSeperation;
    // for midi
    private short pitchWheelDepth;
    // length of message
    private int messageLength;
    // offset in bytes
    private long messageOffset;
    // used for version identification
    private String headerReserved;
    // show what software created this file
    private String possibleCreationSoftware;
    // use for OpenMPT mods.    
    private boolean interpretModPlugMade;
    // pan for each channel 0 = left, 64 = right and 100 is surround
    private short[] channelPan;
    // volume for each cnannel (0-64)
    private short[] channelVolume;
    // order of patterns played
    private short[] orders;
    // offset of instruments
    private long[] offsetOfInstruments;
    // offset of samples
    private long[] offsetOfSampleHeaders;
    // offset of patterns
    private long[] offsetOfPatterns;
    // needed when finding out some data
    private boolean possiblyUnmo3;
    // message
    private String songMessage;
    // formatted message
    private String formattedMessage;
    // used to check for edit history
    private int editHistoryCount;
    // stores all the edit history dates
    private EditHistoryEvent[] editHistoryEvents;
    // stores MIDI macros
    private MidiMacros midiMacros;
    // test for pattern or channel names
    private String testString;
    // length of pattern name
    private long patternNameLength;
    // stores pattern names
    private PatternNames patternNames;
    // length of channel names
    private long channelNameLength;
    // channel names
    private ChannelNames channelNames;

    // constructor
    public ITHeader() {
        super();
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

    public boolean isFilterRangeExtended() {
        return filterRangeExtended;
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

    public int getEditHistoryCount() {
        return editHistoryCount;
    }

    public EditHistoryEvent[] getEditHistoryEvents() {
        return editHistoryEvents;
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

    // setters
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setPatternHilight(short[] patternHilight) {
        this.patternHilight = patternHilight;
    }

    public void setInstrumentNum(int instrumentNum) {
        this.instrumentNum = instrumentNum;
    }

    public void setSampleNum(int sampleNum) {
        this.sampleNum = sampleNum;
    }

    public void setCreatedWithTracker(int createdWithTracker) {
        this.createdWithTracker = createdWithTracker;
    }

    public void setCompatibleWithTracker(int compatibleWithTracker) {
        this.compatibleWithTracker = compatibleWithTracker;
    }

    public void setStereo(boolean stereo) {
        this.stereo = stereo;
    }

    public void setMix(boolean mix) {
        this.mix = mix;
    }

    public void setInstrumental(boolean instrumental) {
        this.instrumental = instrumental;
    }

    public void setLinearSlides(boolean linearSlides) {
        this.linearSlides = linearSlides;
    }

    public void setgLinkedWithEFMemory(boolean gLinkedWithEFMemory) {
        this.gLinkedWithEFMemory = gLinkedWithEFMemory;
    }

    public void setEmbeddedMidiConfiguration(boolean embeddedMidiConfiguration) {
        this.embeddedMidiConfiguration = embeddedMidiConfiguration;
    }

    public void setFilterRangeExtended(boolean filterRangeExtended) {
        this.filterRangeExtended = filterRangeExtended;
    }

    public void setSongMessageAttached(boolean songMessageAttached) {
        this.songMessageAttached = songMessageAttached;
    }

    public void setEditHistoryEmbedded(boolean editHistoryEmbedded) {
        this.editHistoryEmbedded = editHistoryEmbedded;
    }

    public void setHilightEmbedded(boolean hilightEmbedded) {
        this.hilightEmbedded = hilightEmbedded;
    }

    public void setGlobalVolume(short globalVolume) {
        this.globalVolume = globalVolume;
    }

    public void setMixVolume(short mixVolume) {
        this.mixVolume = mixVolume;
    }

    public void setInitialSpeed(short initialSpeed) {
        this.initialSpeed = initialSpeed;
    }

    public void setInitialTempo(short initialTempo) {
        this.initialTempo = initialTempo;
    }

    public void setHeaderReserved(String headerReserved) {
        this.headerReserved = headerReserved;
    }

    public void setOrders(short[] orders) {
        this.orders = orders;
    }

    public void setOffsetOfInstruments(long[] offsetOfInstruments) {
        this.offsetOfInstruments = offsetOfInstruments;
    }

    public void setOffsetOfSampleHeaders(long[] offsetOfSampleHeaders) {
        this.offsetOfSampleHeaders = offsetOfSampleHeaders;
    }

    public void setOffsetOfPatterns(long[] offsetOfPatterns) {
        this.offsetOfPatterns = offsetOfPatterns;
    }

    public void setSongMessage(String songMessage) {
        this.songMessage = songMessage;
    }

    public void setFormattedMessage(String formattedMessage) {
        this.formattedMessage = formattedMessage;
        if (formattedMessage.contains("\n")) {
            songMessage = formattedMessage.replaceAll("\n", "CR");
        }
    }

    public void setEditHistoryEvents(EditHistoryEvent[] editHistoryEvents) {
        this.editHistoryEvents = editHistoryEvents;
    }

    // read method
    public boolean read(IReadable reader) throws IOException,
            IllegalArgumentException {

        // success boolean
        boolean success = true;

        // header code
        testHeader = reader.getByteString(4);

        if (!(testHeader.equals(HEADER_CODE))) {
            throw new IllegalArgumentException("Header is not valid! ");
        }

        // song name
        songName = reader.getByteString(26);

        // pattern highligt
        patternHilight = new short[2];
        patternHilight[0] = reader.getUByteAsShort();
        patternHilight[1] = reader.getUByteAsShort();

        // order number
        orderNum = reader.getUShortAsInt();

        // instrument number
        instrumentNum = reader.getUShortAsInt();

        // sample number
        sampleNum = reader.getUShortAsInt();

        // pattern number
        patternNum = reader.getUShortAsInt();

        // created with tracker
        createdWithTracker = reader.getUShortAsInt();

        // compatible with tracker
        compatibleWithTracker = reader.getUShortAsInt();

        // flags
        flags = reader.getUShortAsInt();

        // set flag values
        // stereo or mono
        stereo = (flags & STEREO_FLAG) == STEREO_FLAG;

        // mix
        mix = (flags & MIX_FLAG) == MIX_FLAG;

        // instruments
        instrumental = (flags & INSTRUMENTAL_FLAG) == INSTRUMENTAL_FLAG;

        // linear slides
        linearSlides = (flags & LINEAR_SLIDE_FLAG) == LINEAR_SLIDE_FLAG;

        // old effects
        oldEffects = (flags & OLD_EFFECTS_FLAG) == OLD_EFFECTS_FLAG;

        // link effect G to E and F
        gLinkedWithEFMemory = (flags & GEF_LINK_FLAG) == GEF_LINK_FLAG;

        // midi pitch controller
        midiPitchControlled = (flags & MIDI_CONTROLLER_FLAG)
                == MIDI_CONTROLLER_FLAG;

        // request embedded MIDI configruation
        embeddedMidiConfiguration = (flags & EMBEDDED_MIDI_CONFIG_FLAG)
                == EMBEDDED_MIDI_CONFIG_FLAG;

        // extended filter range
        filterRangeExtended = (flags & EXTENDED_FILTER_FLAG)
                == EXTENDED_FILTER_FLAG;

        // special
        special = reader.getUShortAsInt();

        // set special values
        // song message
        songMessageAttached = (special & SONG_MESSAGE_FLAG)
                == SONG_MESSAGE_FLAG;

        // edit history embedded
        editHistoryEmbedded = (special & EMBEDDED_EDIT_HISTORY_FLAG)
                == EMBEDDED_EDIT_HISTORY_FLAG;

        // hilight embedded
        hilightEmbedded = (special & EMBEDDED_HILIGHT_FLAG)
                == EMBEDDED_HILIGHT_FLAG;

        // midi configuration embedded
        midiConfigurationEmbedded = (special & MIDI_CONFIG_EMBEDDED_FLAG)
                == MIDI_CONFIG_EMBEDDED_FLAG;

        // global volume
        globalVolume = reader.getUByteAsShort();

        // mix volume
        mixVolume = reader.getUByteAsShort();

        // initial speed
        initialSpeed = reader.getUByteAsShort();

        // initial tempo
        initialTempo = reader.getUByteAsShort();

        // pan seperation
        panSeperation = reader.getUByteAsShort();

        // pitch wheel depth
        pitchWheelDepth = reader.getUByteAsShort();

        // message length
        messageLength = reader.getUShortAsInt();

        // message offset
        messageOffset = reader.getUIntAsLong();

        // read 4 reserved bytes as a string
        headerReserved = reader.getByteString(4);

        // channel pan values
        channelPan = new short[64];

        // extract channel pan bytes
        for (int i = 0; i < channelPan.length; i++) {

            // extract byte
            channelPan[i] = reader.getUByteAsShort();
        }

        // channel volume values
        channelVolume = new short[64];

        // extract channel pan bytes
        for (int i = 0; i < channelVolume.length; i++) {

            // extract byte
            channelVolume[i] = reader.getUByteAsShort();
        }

        // extract orders
        orders = new short[orderNum];

        // extract order bytes
        for (int i = 0; i < orders.length; i++) {

            // extract byte
            orders[i] = reader.getUByteAsShort();
        }

        // extract instrument offsets
        offsetOfInstruments = new long[instrumentNum];

        // extract instruments
        for (int i = 0; i < offsetOfInstruments.length; i++) {

            // extract bytes
            offsetOfInstruments[i] = reader.getUIntAsLong();
        }

        // extract sample header offsets
        offsetOfSampleHeaders = new long[sampleNum];

        // extract samples
        for (int i = 0; i < offsetOfSampleHeaders.length; i++) {

            // extract bytes
            offsetOfSampleHeaders[i] = reader.getUIntAsLong();
        }

        // extract pattern offsets
        offsetOfPatterns = new long[patternNum];

        // extract patterns
        for (int i = 0; i < offsetOfPatterns.length; i++) {

            // extract bytes
            offsetOfPatterns[i] = reader.getUIntAsLong();
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
                    && reader.getFilePosition() * offsetOfSampleHeaders.length + 2
                    <= minPointer) {
                boolean oldUNMO = true;
                for (int i = 0; i < sampleNum; i++) {
                    if (reader.getInt() != 0) {
                        oldUNMO = false;
                        reader.setFilePosition(reader.getFilePosition() - (4 + i * 4));
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
            editHistoryCount = reader.getUShortAsInt();

            if (reader.available() >= editHistoryCount * 8
                    && (reader.getFilePosition() + editHistoryCount
                    * 8 <= minPointer)) {

                int fatDate, fatTime;
                long runTime;

                editHistoryEvents = new EditHistoryEvent[editHistoryCount];

                // extract history
                for (int i = 0; i < editHistoryEvents.length; i++) {
                    // fat date
                    fatDate = reader.getUShortAsInt();

                    // fat time
                    fatTime = reader.getUShortAsInt();

                    // run time
                    runTime = reader.getUIntAsLong();

                    editHistoryEvents[i]
                            = new EditHistoryEvent(fatDate, fatTime, runTime);
                }

                if (possiblyUnmo3 && editHistoryCount == 0) {
                    if (editHistoryEmbedded) {
                        possibleCreationSoftware = "UNMO3 <= 2.4.0.1";
                    } else {
                        possibleCreationSoftware = "UNMO3";
                    }
                }

            } else {
                reader.setFilePosition(reader.getFilePosition() - 2);
            }
        } else if (possiblyUnmo3 && special <= 1) {

            // read first to check it is 0
            if (reader.getShort() == 0) {
                editHistoryCount = 0;
                possibleCreationSoftware = "UNMO3 <= 2.4";
            } else {
                reader.setFilePosition(reader.getFilePosition() - 2);
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
                global[i] = reader.getByteString(32);
            }

            // read global macros
            parametric = new String[16];

            for (int i = 0; i < parametric.length; i++) {

                // extract string
                parametric[i] = reader.getByteString(32);
            }

            // read global macros
            fixed = new String[128];

            for (int i = 0; i < fixed.length; i++) {

                // extract string
                fixed[i] = reader.getByteString(32);
            }

            if (createdWithTracker >= 0x214) {
                midiMacros = new MidiMacros(global, parametric, fixed);
            }
        }

        // test string for PNAM
        testString = reader.getByteString(4);

        // check for pattern names
        if (testString.equals(PATTERN_NAME_CODE)) {

            // read length
            patternNameLength = reader.getUIntAsLong();

            // set string array to hold pattern names
            String[] patternNameStrings
                    = new String[(int) (patternNameLength
                    / PATTERN_NAME_LENGTH)];

            // get the strings
            for (int i = 0; i < patternNameStrings.length; i++) {

                // extract channel name
                patternNameStrings[i] 
                        = reader.getByteString(PATTERN_NAME_LENGTH);
            }

            // set patternNames object
            patternNames = new PatternNames(patternNameStrings);
        } else {

            // reset back 4 spaces
            reader.setFilePosition(reader.getFilePosition() - 4);
        }

        // test string for CNAM
        testString = reader.getByteString(4);

        // check for channel names
        if (testString.equals(CHANNEL_NAME_CODE)) {

            // read length
            channelNameLength = reader.getUIntAsLong();

            // set string array to hold channel names
            String[] channelNameStrings
                    = new String[(int) (channelNameLength 
                    / CHANNEL_NAME_LENGTH)];

            // get the strings
            for (int i = 0; i < channelNameStrings.length; i++) {

                // extract channel name
                channelNameStrings[i] 
                        = reader.getByteString(CHANNEL_NAME_LENGTH);
            }

            // set channelNames object
            channelNames = new ChannelNames(channelNameStrings);
        } else {

            // reset back 4 spaces
            reader.setFilePosition(reader.getFilePosition() - 4);
        }

        // check for plugin information
        //TODO
        // read the message
        if (songMessageAttached) {

            reader.setFilePosition(messageOffset);

            // message
            songMessage = reader.getByteString(messageLength);

            if (songMessage.contains("CR")) {
                formattedMessage = songMessage.replaceAll("CR", "\n");
            } else if (songMessage.contains("LF")) {
                formattedMessage = songMessage.replaceAll("LF", "\n");
                possibleCreationSoftware = "ChibiTracker";
            }
        }

        // successfully read header
        return success;
    }

    // write method
    public boolean write(IWritable writer) throws IOException {

        // success boolean
        boolean success = true;

        // header code
        writer.writeByteString(HEADER_CODE);

        // song name
        writer.writeByteString(songName);

        // pattern highligt
        writer.writeByte((byte) patternHilight[0]);
        writer.writeByte((byte) patternHilight[1]);

        // order number
        writer.writeShort((short) orderNum);

        // instrument number
        writer.writeShort((short) instrumentNum);

        // sample number
        writer.writeShort((short) sampleNum);

        // pattern number
        writer.writeShort((short) patternNum);

        // created with tracker
        createdWithTracker = ST_COMPATIBLE;

        writer.writeShort((short) createdWithTracker);

        // compatible with tracker
        writer.writeShort((short) compatibleWithTracker);

        // flags
        flags = 0;

        // set flag values
        // stereo or mono
        flags |= (stereo) ? 0b1 : 0;

        // mix
        flags |= (mix) ? 0b10 : 0;

        // instruments
        flags |= (instrumental) ? 0b100 : 0;

        // linear slides
        flags |= (linearSlides) ? 0b1000 : 0;

        // old effects
        flags |= (oldEffects) ? 0b10000 : 0;

        // link effect G to E and F
        flags |= (gLinkedWithEFMemory) ? 0b100000 : 0;

        // midi pitch controller
        flags |= (midiPitchControlled) ? 0b1000000 : 0;

        // request embedded MIDI configruation
        flags |= (embeddedMidiConfiguration) ? 0b10000000 : 0;

        // extended filter range
        flags |= (filterRangeExtended) ? 0x8000 : 0;

        // write flags
        writer.writeShort((short) flags);

        // special
        special = 0;

        // set special values
        // song message
        special |= (songMessageAttached) ? 0b1 : 0;

        // edit history embedded
        special |= (editHistoryEmbedded) ? 0b10 : 0;

        // hilight embedded
        special |= (hilightEmbedded) ? 0b100 : 0;

        // midi configuration embedded
        special |= (midiConfigurationEmbedded) ? 0b1000 : 0;

        // write special
        writer.writeShort((short) special);

        // global volume
        writer.writeByte((byte) globalVolume);

        // mix volume
        writer.writeByte((byte) mixVolume);

        // initial speed
        writer.writeByte((byte) initialSpeed);

        // initial tempo
        writer.writeByte((byte) initialTempo);

        // pan seperation
        writer.writeByte((byte) panSeperation);

        // pitch wheel depth
        writer.writeByte((byte) pitchWheelDepth);

        // message length
        writer.writeShort((short) messageLength);

        // message offset
        writer.writeInt((int) messageOffset);

        // read 4 reserved bytes as a string
        writer.writeByteString(headerReserved);

        // write channel pan bytes
        for (int i = 0; i < channelPan.length; i++) {

            // write byte
            writer.writeByte((byte) channelPan[i]);
        }

        // write channel pan bytes
        for (int i = 0; i < channelVolume.length; i++) {

            // write byte
            writer.writeByte((byte) channelVolume[i]);
        }

        // write order bytes
        for (int i = 0; i < orders.length; i++) {

            // write byte
            writer.writeByte((byte) orders[i]);
        }

        // write instrument offsets
        for (int i = 0; i < offsetOfInstruments.length; i++) {

            // write bytes
            writer.writeInt((int) offsetOfInstruments[i]);
        }

        // write sample offsets
        for (int i = 0; i < offsetOfSampleHeaders.length; i++) {

            // write bytes
            writer.writeInt((int) offsetOfSampleHeaders[i]);
        }

        // write pattern offsets
        for (int i = 0; i < offsetOfPatterns.length; i++) {

            // write bytes
            writer.writeInt((int) offsetOfPatterns[i]);
        }

        // write edit histories
        if (editHistoryEmbedded) {

            // write the amount of edit history events
            writer.writeShort((short) editHistoryCount);

            // write the events
            for (EditHistoryEvent event : editHistoryEvents) {

                // fat date
                writer.writeShort((short) event.getFatDate());

                // fat time
                writer.writeShort((short) event.getFatTime());

                // run time
                writer.writeInt((int) event.getRunTime());
            }
        }

        // write midi macros
        if (embeddedMidiConfiguration || midiConfigurationEmbedded) {

            // read MIDI macros
            String[] global, parametric, fixed;

            // read global macros
            global = midiMacros.getGlobalMacros();

            for (String globalSingle : global) {

                // append string
                writer.writeByteString(globalSingle);
            }

            // read global macros
            parametric = midiMacros.getParametricMacros();

            for (String parametricSingle : parametric) {

                // append string
                writer.writeByteString(parametricSingle);
            }

            // read global macros
            fixed = midiMacros.getFixedMacros();

            for (String fixedSingle : fixed) {

                // append string
                writer.writeByteString(fixedSingle);
            }
        }

        // check for pattern names
        if (patternNames != null) {

            // write pattern code
            writer.writeByteString(PATTERN_NAME_CODE);

            // write length
            patternNameLength = patternNames.getPatternNameStuff().length
                    * PATTERN_NAME_LENGTH;

            writer.writeInt((int) patternNameLength);

            // write strings
            for (String patternNameString
                    : patternNames.getPatternNameStuff()) {

                // extract channel name
                writer.writeByteString(patternNameString);
            }
        }

        // check for channel names
        if (channelNames != null) {

            // write channel code
            writer.writeByteString(CHANNEL_NAME_CODE);

            // write length
            channelNameLength = channelNames.getChannelNames().length
                    * CHANNEL_NAME_LENGTH;

            writer.writeInt((int) channelNameLength);

            // write strings
            for (String channelNameString
                    : channelNames.getChannelNames()) {

                // extract channel name
                writer.writeByteString(channelNameString);
            }
        }

        // write plugin information
        //TODO
        // write the message
        if (songMessageAttached) {

            writer.writeByteString(songMessage);
        }

        // successfully read header
        return success;
    }

    public int getPreMessageLength() {

        // known length
        int length = KNOWN_HEADER_LENGTH;

        // order count
        length += this.orderNum;

        // instrument offset lengths
        length += instrumentNum * 4;

        // sample offset lengths
        length += sampleNum * 4;

        // pattern number
        length += patternNum * 4;

        // edit histories
        if (this.editHistoryEvents != null) {
            length += 2 + (editHistoryEvents.length * 8);
        }

        // MIDI macros
        if (embeddedMidiConfiguration || midiConfigurationEmbedded) {
            length += MIDI_MACROS_LENGTH;
        }

        // pattern naems
        if (this.patternNames != null) {
            length += 8 + patternNames.getPatternNameStuff().length
                    * PATTERN_NAME_LENGTH;
        }

        // channel names
        if (this.channelNames != null) {
            length += 8 + channelNames.getChannelNames().length
                    * CHANNEL_NAME_LENGTH;
        }

        // plugins
        // TODO
        return length;
    }

    public int length() {

        int length = getPreMessageLength();

        if (songMessageAttached) {

            this.messageLength = songMessage.length();
        }

        length += messageLength;

        return length;
    }

    public void buildNewEditHistory() {

        // build a new edit history based on the time elapsed between
        // creation and saving
        // get edit time in nanoseconds
        long editTime = System.nanoTime() - getNsTime();

        // convert to seconds
        double secondEditTime = editTime * 0.000000001;

        // convert to DOS timer ticks
        long dosTicks = (long) Math.round(secondEditTime * 18.2);

        // create new edit history
        EditHistoryEvent latestEdit = new EditHistoryEvent(
                this.getDateTime(), dosTicks);

        // build a temp array for the extra event
        EditHistoryEvent[] events
                = new EditHistoryEvent[editHistoryEvents.length + 1];

        System.arraycopy(editHistoryEvents, 0, events, 0,
                editHistoryEvents.length);

        // append new edit history
        events[events.length - 1] = latestEdit;

        // set to temp array
        editHistoryEvents = events;

        // update count
        editHistoryCount = editHistoryEvents.length;

        // update the message offset
        updateMessageOffset();
    }

    public void updateMessageOffset() {

        messageOffset = getPreMessageLength();
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
        if (editHistoryEvents != null) {
            for (EditHistoryEvent history : this.editHistoryEvents) {
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
