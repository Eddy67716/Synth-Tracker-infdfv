/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module.IT.Format;

import Module.IPattern;
import Module.IT.Decoders.NoteRange;
import static Module.IT.Decoders.effectDecoders.*;
import io_stuff.ReadMethods;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Edward Jenkins
 */
public class ITPattern implements IPattern{

    // constants
    public static final byte MAX_CHANNELS = 64;
    public static final byte MAX_COLUMNS = 5;

    // instance variables
    private NoteRange noteRange;
    private String fileName;
    private ReadMethods rm;
    private long offsetToPattern;
    private String name;
    private int length;                 // length of packed data
    private short rows;                 // rows data
    private byte[] packedData;          // data packed
    private byte[][][] unpackedData;    // [row][channel][0-5 note-effect]
    private int packedDataIndex;        // used to keep track of packe reading
    private byte numberOfChannels;      // number of channels used
    private short[] previousChannelMask;  // previous mask variable per channel
    private byte[][] previousValues;   //[channels][note-effect]

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
    public boolean read() throws IOException, FileNotFoundException {

        // method variables
        boolean success = true;

        // read methods
        rm = new ReadMethods(fileName, true);

        // set to offset
        rm.skipBytes(offsetToPattern);

        if (offsetToPattern == 0) {
            return true;
        }

        // pattern length
        length = rm.getUShortAsInt();

        // check length
        if (length > 64000) {
            throw new IOException("Length can be no more than 64 kilobytes. ");
        }

        // pattern rows
        rows = rm.getShort();

        if (rows > 200) {
            throw new IOException("Row count can be no more than 200. ");
        }

        // skip four bytes
        rm.skipBytes(4);

        // packed data
        packedData = new byte[length];
        packedData = rm.getBytes(packedData.length);
        packedDataIndex = 0;

        calculateNumberOfChannels();

        unpack();

        return success;
    }

    // unpack method
    public void unpack() {

        // method variables
        byte channel;
        short channelVariable, maskVariable;

        int row, i;     // i is index of packed data array

        // iniialise unpacked data
        unpackedData = new byte[rows][numberOfChannels][5];

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
                        unpackedData[row][channel][0] = (byte)253;
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
                    unpackedData[row][channel][1] = getPackedDataByte();;
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
                    unpackedData[row][channel][2] = getPackedDataByte();;
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
                    unpackedData[row][channel][3] = getPackedDataByte();;
                    previousValues[channel][3]
                            = unpackedData[row][channel][3];
                    unpackedData[row][channel][4] = getPackedDataByte();;
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
