/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.it.format;

import module.IPattern;
import module.it.decoders.NoteRange;
import static module.it.decoders.effectDecoders.*;
import io.Reader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edward Jenkins
 */
public class ITPattern implements IPattern {

    // constants
    public static final byte MAX_CHANNELS = 64;
    public static final byte MAX_COLUMNS = 5;
    public static final byte KNOWN_PATTERN_LENGTH = 8;

    // instance variables
    private NoteRange noteRange;
    private String fileName;
    private Reader reader;
    private long offsetToPattern;
    private String name;
    // length of packed data
    private int length;                 
    // rows data
    private short rows;                 
    // data packed
    private byte[] packedData;          
    // [row][channel][0-5 note-effect]
    private byte[][][] unpackedData;
    // used to keep track of packe reading    
    private int packedDataIndex;
    // number of channels used
    private byte numberOfChannels;
    // total number of channels in file
    private byte globalChannels;
    // previous mask variable per channel
    private short[] previousChannelMask;
    //[channels][note-effect]
    private byte[][] previousValues;   

    // constructor
    public ITPattern(String fileName, long offsetToPattern) {
        this.fileName = fileName;
        this.offsetToPattern = offsetToPattern;
        noteRange = new NoteRange();
    }

    // getters
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public byte[][][] getUnpackedData() {
        return unpackedData;
    }

    @Override
    public byte getNumberOfChannels() {
        return numberOfChannels;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    // read method
    public boolean read() throws IOException, FileNotFoundException,
            IllegalArgumentException {

        // method variables
        boolean success = true;

        // read methods
        reader = new Reader(fileName, true);

        // set to offset
        reader.skipBytes(offsetToPattern);

        if (offsetToPattern == 0) {
            return true;
        }

        // pattern length
        length = reader.getUShortAsInt();

        // check length
        if (length > 0xffff) {
            throw new IllegalArgumentException("Length can be no more than 64 "
                    + "kilobytes. ");
        }

        // pattern rows
        rows = reader.getShort();

        if (rows > 200) {
            throw new IllegalArgumentException("Row count can be no more than "
                    + "200. ");
        }

        // skip four bytes
        reader.skipBytes(4);

        // packed data
        packedData = reader.getBytes(length);
        packedDataIndex = 0;

        calculateNumberOfChannels();

        unpack();

        return success;
    }

    // length
    public int length() {
        int length = KNOWN_PATTERN_LENGTH;

        length += packedData.length;

        return length;
    }

    // unpack method
    public void unpack() {

        // method variables
        byte channel;
        short channelVariable, maskVariable;

        int row;     // i is index of packed data array

        // iniialise unpacked data
        unpackedData = new byte[rows][numberOfChannels][5];
        
        // initialise all volume values to 0xff
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < numberOfChannels; j++) {
                unpackedData[i][j][2] = (byte)0xff;
            }
        }

        // initialise previous mask aray
        previousChannelMask = new short[numberOfChannels];

        // initialise previous values
        previousValues = new byte[numberOfChannels][MAX_COLUMNS];

        // loop through rows
        for (row = 0; row < rows; row++) {

            // read first row channel variable
            channelVariable = (short) (0xff & getPackedDataByte());

            while (channelVariable != 0) {

                // get channel ID
                channel = (byte) ((channelVariable - 1) & 63);

                // if bit 7 is 1
                if ((channelVariable & 128) == 128) {

                    // read mask variable
                    maskVariable = (short) (0xff & getPackedDataByte());
                    previousChannelMask[channel] = maskVariable;
                } else {

                    // use previous mask variable
                    maskVariable = previousChannelMask[channel];
                }

                // get available note if bit 0 is on
                if ((maskVariable & 1) == 1) {
                    unpackedData[row][channel][0] = getPackedDataByte();

                    short noteValue = (short) (0xff
                            & unpackedData[row][channel][0]);

                    // up by one to make editing easier
                    if (noteValue >= 120
                            && noteValue < 253) {
                        unpackedData[row][channel][0] = (byte) 253;
                    } else if (noteValue < 120) {
                        unpackedData[row][channel][0]++;
                    }

                    previousValues[channel][0]
                            = unpackedData[row][channel][0];
                }

                // use last note value if bit 4 is on
                if ((maskVariable & 16) == 16) {
                    unpackedData[row][channel][0]
                            = previousValues[channel][0];
                }

                // get available instrument if bit 1 is on
                if ((maskVariable & 2) == 2) {
                    unpackedData[row][channel][1] = getPackedDataByte();
                    previousValues[channel][1]
                            = unpackedData[row][channel][1];
                }

                // use last instrument value if bit 5 is on
                if ((maskVariable & 32) == 32) {
                    unpackedData[row][channel][1]
                            = previousValues[channel][1];
                }

                // get available volume command if bit 2 is on
                if ((maskVariable & 4) == 4) {
                    unpackedData[row][channel][2] = getPackedDataByte();
                    previousValues[channel][2]
                            = unpackedData[row][channel][2];
                }

                // use last volume value if bit 6 is on
                if ((maskVariable & 64) == 64) {
                    unpackedData[row][channel][2]
                            = previousValues[channel][2];
                }

                // get available effect and value if bit 3 is on
                if ((maskVariable & 8) == 8) {
                    unpackedData[row][channel][3] = getPackedDataByte();
                    previousValues[channel][3]
                            = unpackedData[row][channel][3];
                    unpackedData[row][channel][4] = getPackedDataByte();
                    previousValues[channel][4]
                            = unpackedData[row][channel][4];
                }

                // use last effect value if bit 6 is on
                if ((maskVariable & 128) == 128) {
                    unpackedData[row][channel][3]
                            = previousValues[channel][3];
                    unpackedData[row][channel][4]
                            = previousValues[channel][4];
                }

                // read next channel variable
                channelVariable = (short) (0x00ff & getPackedDataByte());
            }
        }
    }

    public byte[] pack() throws ArrayIndexOutOfBoundsException {

        // packed data
        List<Byte> dataPacker = new ArrayList<>();

        // mask variables
        short channelVariable, maskVariable;

        // initialise write values
        byte[][] writeValues = new byte[MAX_CHANNELS][MAX_COLUMNS];

        // initialise previous mask aray
        previousChannelMask = new short[MAX_CHANNELS];

        // initialise previous values
        previousValues = new byte[MAX_CHANNELS][MAX_COLUMNS];

        // loop through rows
        for (int row = 0; row < rows; row++) {

            // looop through channel columns
            for (int channel = 0; channel < numberOfChannels; channel++) {

                channelVariable = 0;
                maskVariable = 0;

                // check each unpacked byte is a value and set flags accordingly
                for (int i = 0; i < MAX_COLUMNS - 1; i++) {
                    if (i != 2 && unpackedData[row][channel][i] != 0
                            || i == 2 && unpackedData[row][channel][i] != -1) {

                        // check note is not the same as last note
                        if (row != 0 && unpackedData[row][channel][i]
                                == previousValues[channel][i]) {
                            maskVariable |= (1 << i) << 4;
                        } else {
                            maskVariable |= 1 << i;
                        }
                    }
                }

                // append all valid bytes
                // set write note if bit 0 is on
                if ((maskVariable & 1) == 1) {
                    writeValues[channel][0] = unpackedData[row][channel][0];

                    // down by one if a note
                    if (writeValues[channel][0] < 121
                            && writeValues[channel][0] > 0) {
                        writeValues[channel][0]--;
                    }

                    previousValues[channel][0]
                            = writeValues[channel][0];
                }

                // set write instrument if bit 1 is on
                if ((maskVariable & 2) == 2) {
                    writeValues[channel][1] = unpackedData[row][channel][1];
                    previousValues[channel][1]
                            = writeValues[channel][1];
                }

                // set write volume command if bit 2 is on
                if ((maskVariable & 4) == 4) {
                    writeValues[channel][2] = unpackedData[row][channel][2];
                    previousValues[channel][2]
                            = writeValues[channel][2];
                }

                // set write effect and value if bit 3 is on
                if ((maskVariable & 8) == 8) {
                    writeValues[channel][3] = unpackedData[row][channel][3];
                    previousValues[channel][3]
                            = writeValues[channel][3];
                    writeValues[channel][4] = unpackedData[row][channel][4];
                    previousValues[channel][4]
                            = writeValues[channel][4];
                }

                // write channel to packed data
                if (maskVariable != 0) {
                    
                    // set channel variable
                    channelVariable = (short) (channel + 1);
                    
                    if (row != 0 && maskVariable == previousChannelMask[channel]) {

                        dataPacker.add((byte) channelVariable);
                    } else {
                        
                        // set channel variable to read new byte
                        channelVariable |= 128;
                        dataPacker.add((byte) channelVariable);
                        dataPacker.add((byte) maskVariable);

                        // set previous mask variable
                        previousChannelMask[channel] = maskVariable;
                    }
                }

                // write write vales to packed data
                // write note if bit 0 is on
                if ((maskVariable & 1) == 1) {
                    dataPacker.add(writeValues[channel][0]);
                }

                // write instrument if bit 1 is on
                if ((maskVariable & 2) == 2) {
                    dataPacker.add(writeValues[channel][1]);
                }

                // write volume command if bit 2 is on
                if ((maskVariable & 4) == 4) {
                    dataPacker.add(writeValues[channel][2]);
                }

                // write effect and value if bit 3 is on
                if ((maskVariable & 8) == 8) {
                    dataPacker.add(writeValues[channel][3]);
                    dataPacker.add(writeValues[channel][4]);
                }
                
                if (channel == numberOfChannels - 1) {
                    dataPacker.add((byte)0);
                }
            }
        }

        if (dataPacker.size() > 64000) {

            while (dataPacker.size() > 6400) {
                dataPacker.remove(dataPacker.size() - 1);
            }

            throw new ArrayIndexOutOfBoundsException("Warning some data could"
                    + " not be saved due to exceeding max pattern length");
        }

        byte[] packedData = new byte[dataPacker.size()];
        
        for (int i = 0; i < packedData.length; i++) {
            
            // build array from array list
            packedData[i] = dataPacker.get(i);
        }
        
        this.packedData = packedData;
        
        this.length = this.packedData.length;
        
        return packedData;
    }

    private byte getPackedDataByte() {

        byte dataToReturn = packedData[packedDataIndex];
        packedDataIndex++;
        if (packedDataIndex >= packedData.length) {
            packedDataIndex = 0;
        }
        return dataToReturn;
    }

    private void calculateNumberOfChannels() {

        // method variables
        byte channel, highestChannel = 0;
        short channelVariable, maskVariable;

        // initialise previous mask aray
        previousChannelMask = new short[MAX_CHANNELS];

        int row, i;     // i is index of packed data array

        // loop through rows
        for (row = 0; row < rows; row++) {

            // read channel variable
            channelVariable = (short) (0x00ff & getPackedDataByte());

            while (channelVariable != 0) {

                // get channel ID
                channel = (byte) ((channelVariable - 1) & 63);

                if ((channelVariable & 128) == 128) {

                    // read mask variable
                    maskVariable = (short) (0x00ff & getPackedDataByte());
                    previousChannelMask[channel] = maskVariable;
                } else {
                    maskVariable = previousChannelMask[channel];
                }

                // check channel
                if (channel > highestChannel) {
                    highestChannel = channel;
                }

                // skip note
                if ((maskVariable & 1) == 1) {
                    getPackedDataByte();
                }

                // skip instrument
                if ((maskVariable & 2) == 2) {
                    getPackedDataByte();
                }

                // skip volume command
                if ((maskVariable & 4) == 4) {
                    getPackedDataByte();
                }

                // skip command and value
                if ((maskVariable & 8) == 8) {
                    getPackedDataByte();
                    getPackedDataByte();
                }

                // read next channel variable
                channelVariable = (short) (0x00ff & getPackedDataByte());
            }
        }

        packedDataIndex = 0;
        numberOfChannels = (byte) (highestChannel + 1);
    }

    // to stirng
    @Override
    public String toString() {

        // string builder
        StringBuilder sb = new StringBuilder();

        // start
        sb.append("Pattern[");

        // length
        sb.append("\nLength: ");
        sb.append(length);

        // rows
        sb.append("\nRows: ");
        sb.append(rows);

        // channels
        sb.append("\nNumber of channels: ");
        sb.append(numberOfChannels);

        // unpacked data
        sb.append("\nData: \n");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < numberOfChannels; j++) {
                for (int k = 0; k < MAX_COLUMNS; k++) {
                    switch (k) {
                        case 0:
                            sb.append(noteRange.getNote(unpackedData[i][j][k]));
                            break;
                        case 1:
                            sb.append(String.format("%02d", unpackedData[i][j][k]));
                            break;
                        case 2:
                            sb.append(decodeVolumeByte(unpackedData[i][j][k]));
                            break;
                        case 3:
                            sb.append(decodeEffectByte(unpackedData[i][j][k]));
                            break;
                        case 4:
                            sb.append(String.format("%02X", unpackedData[i][j][k]));
                            break;
                        default:
                            sb.append(unpackedData[i][j][k]);
                            break;
                    }
                    sb.append("");
                }
                sb.append("\t");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
