/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io_stuff;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import static io_stuff.IOMethods.reverseEndian;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Edward Jenkins © Edward Jenkins 2021-2022
 */
public class WriteMethods {

    // constants
    public static final byte[] AND_VALUES = {0, 1, 3, 7, 15, 31, 63, 127};

    // instance variables
    private byte[] convertedBytes;
    private String fileName;
    private boolean littleEndian;
    private DataOutputStream dos;
    private long filePosition;
    private byte extraBits;
    private byte extraBitCount;
    private byte leadingBits;

    /**
     * The 2-args constructor used to write a file with a file name string.
     *
     * @param fileName The name of the file to write to
     * @param littleEndian Writes little-endian values if true
     * @throws IOException Thrown if file is not found
     */
    public WriteMethods(String fileName, boolean littleEndian)
            throws IOException {
        dos = new DataOutputStream(new FileOutputStream(fileName));
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
    public WriteMethods(String fileName)
            throws IOException {
        this(fileName, false);
        this.fileName = fileName;
    }

    /**
     * Writes a byte string to file.
     *
     * @param outputString string to output.
     * @throws IOException
     */
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
    public void writeUTFString(String outputString) throws IOException {

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
    public void writeArbitraryBitValue(long value, byte bits)
            throws IOException {

        // method variables
        int bytesToWrite;
        boolean littleEndian = this.littleEndian;
        byte bitOffset;
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

                leadingBits += bitOffset;

                if (leadingBits == 8) {

                    // the bits will byte align
                    bytesToWrite++;
                } else if (leadingBits > 8) {

                    // subtract 8 from leading bits and append another byte
                    leadingBits -= 8;
                    bytesToWrite++;
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

            // values longer than bit offset
            if (writeBitCount > bitOffset) {

                // subtract 8 to bit shift to next byte to write
                writeBitCount -= 8;

                // if bit count is greater than zero, bitshift
                if (extraBitCount > 0 && leadingBits == 8) {

                    // do the bitshift and oring of extra bits
                    writeBytes[i] = manageBitOffset(
                            (byte) (value >>> bitOffset), extraBitCount);
                } else {

                    // append byte to writeBytes array
                    writeBytes[i] = (byte) (value >>> bitOffset);
                }
            } else if (leadingBits == 8) {

                // values that are a byte
                writeBytes[i] = (byte) (value | (extraBits << leadingBits
                        - extraBitCount));

                extraBitCount = 0;
                extraBits = 0;
                leadingBits = 0;
            }
        }

        // write the bytes
        appendBytes(writeBytes, 0, writeBytes.length);

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
                extraBitCount = bitOffset;
            }
        }

        this.littleEndian = littleEndian;
    }

    /**
     * Writes leading bits if any to byte align the file.
     *
     * @throws IOException
     */
    public void byteAlign() throws IOException {

        if (leadingBits > 0) {

            // write extra bits
            writeByte((byte) (extraBits << extraBitCount));

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
    public void writeIntAsTwentyFourBit(int value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(value);
        appendBytes(buffer.array(), 0, 3);
    }

    /**
     * Writes a short to file.
     *
     * @param value The short value
     * @throws IOException
     */
    public void writeShort(short value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(value);
        appendBytes(buffer.array(), 0, 2);
    }

    /**
     * Writes a byte to file.
     *
     * @param value The byte value
     * @throws IOException
     */
    public void writeByte(byte value) throws IOException {

        if (leadingBits != 0) {

            // bit shift a byte
            value = manageBitOffset(value, leadingBits);
        }

        dos.writeByte(value);

        filePosition++;
    }

    public void writeBoolean(boolean value) throws IOException {

        // value as byte
        byte byteValue = (value) ? (byte) 1 : (byte) 0;

        // write value
        writeByte(byteValue);
    }

    /**
     *
     * @param bytes the bytes to write
     * @throws IOException
     */
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
     * @throws IOException
     */
    private void appendBytes(byte[] bytesToAppend, int start, int end)
            throws IOException {

        // if reverse
        if (littleEndian) {

            // reverse the order of byres
            bytesToAppend = reverseEndian(bytesToAppend);
        }

        // adjust file position
        filePosition += (end - start);

        // if leadingBits, deal with them
        if (leadingBits != 0) {

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
     * zero fill bytes till you reach the desired position
     *
     * @param bytes Bytes to zero fill
     * @throws IOException
     */
    public void skipBytes(long bytes) throws IOException {

        // method variables
        byte zeroFil = 0;

        for (int i = 0; i < bytes; i++) {
            writeByte(zeroFil);
        }

    }

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
