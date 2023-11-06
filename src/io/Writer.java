/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import static io.IOMethods.reverseEndian;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edward Jenkins © 2021-2023
 */
public class Writer implements IWritable {

    // constants
    public static final byte[] AND_VALUES = {0, 1, 3, 7, 15, 31, 63, 127};

    // instance variables
    // name of file
    private String fileName;
    // whether to write in little endian
    private boolean littleEndian;
    // the data output stream
    private DataOutputStream dos;
    // the check byte stream used if a portion of the file is needed
    private List<Byte> checkByteStream;
    // add bytes to check byte stream if true
    private boolean buildingCheckByteStream;
    // bytes written
    private long filePosition;
    // extra bits when writing non byte values
    private byte extraBits;
    // how many bits are in extra bits
    private byte extraBitCount;
    // amount of extra offsetted bits
    private byte leadingBits;

    /**
     * The 2-args constructor used to write a file with a file name string.
     *
     * @param fileName The name of the file to write to
     * @param littleEndian Writes little-endian values if true
     * @throws IOException Thrown if file is not found
     */
    public Writer(String fileName, boolean littleEndian)
            throws IOException {
        dos = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream(fileName)));
        this.littleEndian = littleEndian;
        filePosition = dos.size();
        this.fileName = fileName;
    }

    /**
     * The 1-args constructor used to write a file with a file name string. This
     * can only write in big-endian.
     *
     * @param fileName The name of the file to write to
     * @throws IOException Thrown if file is not found
     */
    public Writer(String fileName)
            throws IOException {
        this(fileName, false);
    }

    /**
     * Gets the file's position.
     *
     * @return the file's current position
     */
    @Override
    public long getFilePosition() {
        return filePosition;
    }

    /**
     * Sets endianess of file.
     *
     * @param littleEndian Little-endian if true
     */
    @Override
    public void setLittleEndian(boolean littleEndian) {
        this.littleEndian = littleEndian;
    }

    /**
     * Starts saving a check byte stream that can be used for CRC or other
     * checks.
     */
    @Override
    public void buildCheckByteStream() {
        buildingCheckByteStream = true;
        checkByteStream = new LinkedList<>();
    }

    /**
     * Gets the check byte stream that has been saved.
     *
     * @return the check byte stream
     */
    @Override
    public byte[] getCheckByteStream() {

        // byte arrary
        byte[] returnByteStream = new byte[checkByteStream.size()];

        // build loop
        for (int i = 0; i < returnByteStream.length; i++) {
            returnByteStream[i] = checkByteStream.get(i);
        }

        return returnByteStream;
    }

    /**
     * Resets the check byte stream.
     */
    @Override
    public void resetCheckByteStream() {
        checkByteStream = new LinkedList<>();
    }

    /**
     * End the check byte stream.
     */
    @Override
    public void endCheckByteStream() {
        buildingCheckByteStream = false;
    }

    /**
     * Writes a byte string to file.
     *
     * @param outputString string to output.
     * @throws IOException
     */
    @Override
    public void writeByteString(String outputString) throws IOException {

        if (leadingBits != 0) {
            int stringLength = outputString.length();
            for (int i = 0; i < stringLength; i++) {
                writeByte((byte) outputString.charAt(i));
            }
        } else {
            dos.writeBytes(outputString);
            if (buildingCheckByteStream && checkByteStream != null) {
                int stringLength = outputString.length();
                for (int i = 0; i < stringLength; i++) {
                    checkByteStream.add((byte) outputString.charAt(i));
                }
            }
        }
        filePosition += outputString.length();

    }

    /**
     * Writes a 2-byte char string to file.
     *
     * @param outputString string to output
     * @throws IOException
     */
    @Override
    public void writeCharString(String outputString) throws IOException {

        if (leadingBits != 0) {
            int stringLength = outputString.length();
            for (int i = 0; i < stringLength; i++) {
                writeShort((short) outputString.charAt(i));
            }
        } else {
            dos.writeChars(outputString);
        }
        filePosition += outputString.length() * 2;
    }

    /**
     * Writes a UTF-8 encoded string to file.
     *
     * @param outputString string to encode
     * @throws IOException
     */
    @Override
    public void writeUTF8String(String outputString) throws IOException {

        // method variables
        short stringLength;
        byte[] utfEncodedBytes
                = outputString.getBytes(StandardCharsets.UTF_8);

        stringLength = (short) utfEncodedBytes.length;

        writeShort(stringLength);

        writeBytes(utfEncodedBytes);
    }

    /**
     * Writes a value that is of an arbitrary bit length to file.
     *
     * @param value The value to write
     * @param bits The bit length of value
     * @throws IOException
     */
    @Override
    public void writeArbitraryBitValue(long value, byte bits)
            throws IOException {

        // method variables
        byte bitOffset;
        int bytesToWrite;
        // trailing bits used if byte overflow occures
        byte trailingBits = 0;
        boolean byteOverflowing = false;
        byte bitsToWrite;
        byte writtenBits = 0;
        byte[] writeBytes;

        // extract bytes
        bytesToWrite = bits / 8;
        bitsToWrite = bits;

        // get extra bit offset
        bitOffset = (byte) (bits % 8);

        // making header bits all 0 if signed i.e. highest bit is 1
        if ((value >>> 63 & 1) == 1) {

            // bitshift backwards and forwards to set value to unsigned
            value <<= (64 - bits);
            value >>>= (64 - bits);
        }

        // deal with bit offsetted values
        if (leadingBits != 0) {

            trailingBits = leadingBits;

            leadingBits += bitOffset;

            if (leadingBits == 8) {

                // the bits will byte align
                bytesToWrite++;
                bitOffset = 0;
                byteOverflowing = (bitsToWrite > leadingBits);
            } else if ((leadingBits > 8)
                    || (leadingBits > 0 && leadingBits
                    + bitsToWrite > 8)) {

                // append another byte
                byteOverflowing = true;
                if (leadingBits > 8) {
                    bytesToWrite++;
                }
            }

        } else if (bitOffset != 0) {
            leadingBits = bitOffset;
        }

        // build bytes to write
        writeBytes = new byte[bytesToWrite];

        // write in little endain order
        if (littleEndian) {

            for (int i = 0; i < bytesToWrite; i++) {

                if (bitsToWrite > leadingBits) {

                    if (byteOverflowing) {

                        // set temperary leading bits
                        byte newLeadingBits = (leadingBits - 8 >= 0)
                                ? (byte) (leadingBits - 8) : leadingBits;

                        // do the bitshifting of extra byte overflow bits
                        byte orValue = (byte) (value
                                & AND_VALUES[8 - extraBitCount]);

                        writeBytes[i] = (byte) (extraBits
                                | (orValue << extraBitCount));

                        leadingBits = newLeadingBits;
                        bitOffset = leadingBits;

                    } else {

                        // append byte to writeBytes array
                        writeBytes[i] = (byte) (value >>> writtenBits);
                    }

                    // subtract 8 to bit shift to next byte to write
                    if (byteOverflowing) {
                        bitsToWrite -= (8 - trailingBits);
                        writtenBits = (byte) (8 - trailingBits);
                        byteOverflowing = false;
                    } else {
                        bitsToWrite -= 8;
                        writtenBits += 8;
                    }

                } else if (leadingBits == 8) {

                    // values that are a byte
                    writeBytes[i] = (byte) ((value << extraBitCount)
                            | extraBits);

                    extraBitCount = 0;
                    extraBits = 0;
                    leadingBits = 0;
                } else {

                    // do the bitshifting of extra byte overflow bits
                    // just like when there is a byte overflow
                    byte orValue = (byte) (value
                            & AND_VALUES[8 - extraBitCount]);

                    writeBytes[i] = (byte) (extraBits
                            | (orValue << (extraBitCount)));

                    leadingBits -= 8;
                    bitOffset = leadingBits;
                    bitsToWrite -= (8 - trailingBits);
                    writtenBits = (byte) (8 - trailingBits);
                    byteOverflowing = false;
                }
            }

            // tempererily set little endian to false
            littleEndian = false;

            // write the bytes
            appendBytes(writeBytes, 0, writeBytes.length, true);

            // set little endian back to true
            littleEndian = true;

            // deal with extra bits for later
            if (bitsToWrite > 0 && leadingBits != 0) {

                // initialise leadingBits
                if (leadingBits == bitOffset) {

                    // set extra bits to the leading offset of value
                    extraBits = (byte) ((value >>> writtenBits)
                            & AND_VALUES[bitOffset]);
                    extraBitCount = bitOffset;
                } else {

                    // set extra bits to the leading offset of value
                    extraBits = (byte) (extraBits | ((value
                            & AND_VALUES[bitOffset]) << extraBitCount));
                    extraBitCount = leadingBits;
                }
            }

        } else {

            // write in big endian order
            for (int i = 0; i < bytesToWrite; i++) {

                // values longer than leadingBits
                if (bitsToWrite > leadingBits) {

                    // subtract 8 to bit shift to next byte to write
                    if (byteOverflowing) {
                        bitsToWrite -= (8 - trailingBits);
                        writtenBits = (byte) (8 - trailingBits);
                    } else {
                        bitsToWrite -= 8;
                        writtenBits += 8;
                    }

                    if (byteOverflowing) {

                        // set temperary leading bits
                        byte newLeadingBits = (leadingBits - 8 >= 0)
                                ? (byte) (leadingBits - 8) : leadingBits;

                        // do the bitshifting of extra byte overflow bits
                        byte orValue = (byte) (value >>> (bits - (8
                                - extraBitCount)) & AND_VALUES[8 - extraBitCount]);

                        writeBytes[i] = (byte) ((extraBits << (8 - extraBitCount))
                                | orValue);

                        leadingBits = newLeadingBits;
                        bitOffset = leadingBits;
                        byteOverflowing = false;
                    } else {

                        // append byte to writeBytes array
                        writeBytes[i] = (byte) (value >>> bitsToWrite);
                    }
                } else if (leadingBits == 8) {

                    // values that are a byte
                    writeBytes[i] = (byte) (value | (extraBits << leadingBits
                            - extraBitCount));

                    extraBitCount = 0;
                    extraBits = 0;
                    leadingBits = 0;
                } else {

                    // do the bitshifting of extra byte overflow bits
                    // just like when there is a byte overflow
                    byte orValue = (byte) ((value & 0xff) >>> (bits
                            - (8 - extraBitCount)) & AND_VALUES[8 - extraBitCount]);

                    writeBytes[i] = (byte) ((extraBits << (8 - extraBitCount))
                            | orValue);

                    // subtract 8 from leading bits
                    leadingBits -= 8;
                    bitOffset = leadingBits;
                    byteOverflowing = false;
                }
            }

            // write the bytes
            appendBytes(writeBytes, 0, writeBytes.length, true);

            // deal with extra bits for later
            if (bitsToWrite > 0 && leadingBits != 0) {

                // initialise leadingBits
                if (leadingBits == bitOffset) {

                    // set extra bits to the leading offset of value
                    extraBits = (byte) (value & AND_VALUES[bitOffset]);
                    extraBitCount = bitOffset;
                } else {

                    // set extra bits to the leading offset of value
                    extraBits = (byte) ((extraBits << bitOffset)
                            | (value & AND_VALUES[bitOffset]));
                    extraBitCount = leadingBits;
                }
            }
        }
    }

    /**
     * Writes leading bits if any and byte aligns the file.
     *
     * @throws IOException
     */
    @Override
    public void byteAlign() throws IOException {

        if (leadingBits > 0) {

            // write extra bits
            if (littleEndian) {
                writeByte(extraBits, true);
            } else {
                writeByte((byte) (extraBits << (8 - extraBitCount)), true);
            }

            leadingBits = 0;
            extraBitCount = 0;
            extraBits = 0;
        }
    }

    /**
     * Writes a double value to file.
     *
     * @param value The double to write
     * @throws IOException
     */
    @Override
    public void writeDouble(double value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putDouble(value);
        appendBytes(buffer.array(), 0, 8);
    }

    /**
     * Writes a long value to file.
     *
     * @param value The long to write
     * @throws IOException
     */
    @Override
    public void writeLong(long value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(value);
        appendBytes(buffer.array(), 0, 8);
    }

    /**
     * Writes a float value to file.
     *
     * @param value The float to write
     * @throws IOException
     */
    @Override
    public void writeFloat(float value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putFloat(value);
        appendBytes(buffer.array(), 0, 4);
    }

    /**
     * Writes an integer value to file.
     *
     * @param value The integer to write
     * @throws IOException
     */
    @Override
    public void writeInt(int value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(value);
        appendBytes(buffer.array(), 0, 4);
    }

    /**
     * Writes a 24-bit value to file.
     *
     * @param value The 24-bit value
     * @throws IOException
     */
    @Override
    public void writeIntAsTwentyFourBit(int value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(value);
        appendBytes(buffer.array(), (littleEndian) ? 0 : 1, 3);
    }

    /**
     * Writes a short to file.
     *
     * @param value The short value
     * @throws IOException
     */
    @Override
    public void writeShort(short value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(value);
        appendBytes(buffer.array(), 0, 2);
    }

    /**
     * Writes a byte to file.
     *
     * @param value The byte value
     * @param ignoreOffset Used when we need to ignore byte offset stuff
     * @throws IOException
     */
    private void writeByte(byte value, boolean ignoreOffset) throws IOException {

        if (!ignoreOffset && leadingBits != 0) {

            // bit shift a byte
            value = manageBitOffset(value, leadingBits);
        }

        dos.writeByte(value);
        
        if (buildingCheckByteStream && checkByteStream != null) {
            checkByteStream.add(value);
        }

        filePosition++;
    }

    /**
     * Writes a byte to file.
     *
     * @param value The byte value
     * @throws IOException
     */
    @Override
    public void writeByte(byte value) throws IOException {

        writeByte(value, false);
    }

    /**
     * Writes a boolean value to file
     *
     * @param value the boolean to write
     * @throws IOException
     */
    @Override
    public void writeBoolean(boolean value) throws IOException {

        // value as byte
        byte byteValue = (value) ? (byte) 1 : (byte) 0;

        // write value
        writeByte(byteValue);
    }

    /**
     * Writes an array of bytes to the file
     *
     * @param bytes the bytes to write
     * @throws IOException
     */
    @Override
    public void writeBytes(byte[] bytes) throws IOException {

        for (byte byteToWrite : bytes) {
            writeByte(byteToWrite);
        }
    }

    /**
     * Writes a byte array to the file.
     *
     * @param bytesToAppend the byte array
     * @param start
     * @param end
     * @param ignoreOffset ignore the bit offset
     * @throws IOException
     */
    private void appendBytes(byte[] bytesToAppend, int start, int end,
            boolean ignoreOffset)
            throws IOException {

        // if reverse
        if (littleEndian) {

            // reverse the order of bytes
            bytesToAppend = reverseEndian(bytesToAppend);
        }

        // adjust file position
        filePosition += (end - start);

        // if leadingBits, deal with them
        if (!ignoreOffset && leadingBits != 0) {

            // bitshift bytes and add leadingBits
            for (int i = 0; i < bytesToAppend.length; i++) {

                // bit shift a byte
                bytesToAppend[i] = manageBitOffset(bytesToAppend[i],
                        leadingBits);
            }

        }

        // write the bytes
        dos.write(bytesToAppend, start, end);

        // append bytes to check byte stream
        if (buildingCheckByteStream && checkByteStream != null) {
            for (int i = start; i < end; i++) {
                checkByteStream.add(bytesToAppend[i]);
            }
        }
    }

    /**
     * Writes a byte array to the file.
     *
     * @param bytesToAppend the byte array
     * @param start
     * @param end
     * @throws IOException
     */
    private void appendBytes(byte[] bytesToAppend, int start, int end)
            throws IOException {

        appendBytes(bytesToAppend, start, end, false);
    }

    /**
     * zero fill bytes till you reach the desired position
     *
     * @param bytes Bytes to zero fill
     * @return true if successful
     * @throws IOException
     */
    @Override
    public boolean skipBytes(long bytes) throws IOException {

        // method variables
        byte zeroFil = 0;

        for (int i = 0; i < bytes; i++) {
            writeByte(zeroFil);
        }

        return true;
    }

    /**
     * Closes the file by writing any extra bits and byte aligning then closing
     * the stream.
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        byteAlign();
        dos.close();
    }

    /**
     * Bit shifts to accommodate for extra bits.
     *
     * @param value The value to bit-shift
     * @param bitCount The amount to bit-shift
     * @return
     */
    private byte manageBitOffset(byte value, byte bitCount) {

        // method variabls
        byte bitsToShiftIn;

        // set bitsToShiftIn
        bitsToShiftIn = extraBits;

        if (littleEndian) {

            // get extra bits from beginning of byte
            extraBits = (byte) ((value >> (8 - bitCount))
                    & AND_VALUES[bitCount]);

            // bit shift left
            value = (byte) ((int) value << bitCount);

            // set extra bit count
            extraBitCount = bitCount;

            value = (byte) ((int) value | bitsToShiftIn);

        } else {

            // get extra bits from end of byte
            extraBits
                    = (byte) (value & AND_VALUES[bitCount]);

            // bit shift right
            value = (byte) ((int) value >>> bitCount);

            // set extra bit count
            extraBitCount = bitCount;

            value = (byte) (value | (bitsToShiftIn << (8 - bitCount)));
        }

        return value;
    }
}
