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

/**
 *
 * @author Edward Jenkins © Edward Jenkins 2021-2023
 */
public class Writer implements IWritable{

    // constants
    public static final byte[] AND_VALUES = {0, 1, 3, 7, 15, 31, 63, 127};

    // instance variables
    private String fileName;            // name of file
    private boolean littleEndian;       // whether to write in little endian
    private DataOutputStream dos;       // the data output stream
    private long filePosition;          // bytes written
    private byte extraBits;             // extra bits when writing non byte values
    private byte extraBitCount;         // how many bits are in extra bits
    private byte leadingBits;           // amount of extra offsetted bits

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
     * Writes a byte string to file.
     *
     * @param outputString string to output.
     * @throws IOException
     */
    @Override
    public void writeByteString(String outputString) throws IOException {

        dos.writeBytes(outputString);
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

        dos.writeChars(outputString);
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
        byte trailingBits = 0;      // trailing bits used if byte overflow occures
        boolean littleEndian = this.littleEndian;
        boolean byteOverflowing = false;
        byte writeBitCount;
        byte[] writeBytes;

        // extract bytes
        bytesToWrite = bits / 8;
        writeBitCount = bits;

        // get extra bit offset
        bitOffset = (byte) (bits % 8);

        // deal with bit offsetted values
        if (bitOffset != 0) {

            if (leadingBits != 0) {

                trailingBits = leadingBits;

                leadingBits += bitOffset;

                if (leadingBits == 8) {

                    // the bits will byte align
                    bytesToWrite++;
                    bitOffset = 0;
                    if (writeBitCount > leadingBits) {
                        byteOverflowing = true;
                    }
                } else if ((leadingBits > 8)
                        || (leadingBits > 0 && leadingBits + writeBitCount
                        > 8)) {

                    // append another byte
                    byteOverflowing = true;
                    if (leadingBits > 8) {
                        bytesToWrite++;
                    }
                }

            } else {
                leadingBits = bitOffset;
            }

            // set to big endian for arbitrary bit values that aren't byte
            // aligned.
            this.littleEndian = false;
        }

        // build bytes to write
        writeBytes = new byte[bytesToWrite];

        for (int i = 0; i < bytesToWrite; i++) {

            // values longer than leadingBits
            if (writeBitCount > leadingBits) {

                // subtract 8 to bit shift to next byte to write
                if (byteOverflowing) {
                    writeBitCount -= (8 - trailingBits);
                } else if (writeBitCount >= 8) {
                    writeBitCount -= 8;
                } else {
                    writeBitCount = leadingBits;
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
                } // if bit count is greater than zero, bitshift
                else if (extraBitCount > 0 && leadingBits == 8) {

                    // do the bitshifting of extra bits
                    writeBytes[i] = manageBitOffset(
                            (byte) (value >>> bitOffset), extraBitCount);

                } else {

                    // append byte to writeBytes array
                    writeBytes[i] = (byte) (value >>> writeBitCount);
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
        if (writeBitCount > 0 && leadingBits != 0) {

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

        // set back to original endianness.
        this.littleEndian = littleEndian;
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
            writeByte((byte) (extraBits << (8 - extraBitCount)), true);

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

        // get extra bits from end of byte
        extraBits
                = (byte) (value & AND_VALUES[bitCount]);

        // bit shift a byte
        value = (byte) ((int) value >>> bitCount);

        // set extra bit count
        extraBitCount = bitCount;

        value = (byte) (value | (bitsToShiftIn << (8 - bitCount)));

        return value;
    }
}
