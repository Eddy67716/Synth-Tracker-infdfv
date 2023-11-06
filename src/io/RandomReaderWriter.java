/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io;

import static io.IOMethods.reverseEndian;
import static io.Writer.AND_VALUES;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edward Jenkins © 2021-2023
 */
public class RandomReaderWriter implements IReadable, IWritable {

    // accesses the file
    private RandomAccessFile raf;
    // name of file
    private String fileName;
    // stores if file is reading in little endian or not
    private boolean littleEndian;
    // the check byte stream used if a portion of the file is needed
    private List<Byte> checkByteStream;
    // add bytes to check byte stream if true
    private boolean buildingCheckByteStream;
    // stores bytes to convert
    private byte[] convertBytes;
    // the extra bits that can't yet be written
    private byte extraBits;
    // count of extra bits
    private byte extraBitCount;
    // count of trailing bits
    private byte readTrailingBits;
    // count of extra offsetted bits
    private byte leadingBitsToWrite;
    // the bit offset over the byte mark
    private byte bitOffset;

    /**
     * The 2-args constructor used to write a file with a file name string.
     *
     * @param fileName The name of the file to write to
     * @param littleEndian Writes little-endian values if true
     * @throws IOException Thrown if file is not found
     */
    public RandomReaderWriter(String fileName, boolean littleEndian)
            throws IOException {
        raf = new RandomAccessFile(fileName, "rw");
        this.littleEndian = littleEndian;
        this.fileName = fileName;
    }

    /**
     * The 1-args constructor used to write a file with a file name string. This
     * can only write in big-endian.
     *
     * @param fileName The name of the file to write to
     * @throws IOException Thrown if file is not found
     */
    public RandomReaderWriter(String fileName)
            throws IOException {
        this(fileName, false);
    }

    /**
     * Returns the random access file used to read data.
     *
     * @return the data input stream
     */
    public RandomAccessFile getRaf() {
        return this.raf;
    }

    /**
     * Gets the file's position.
     *
     * @return the file's current position
     * @throws java.io.IOException
     */
    @Override
    public long getFilePosition() throws IOException {
        return raf.getFilePointer();
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
     * Resets the file position.
     *
     * @param filePosition The position to reset to
     * @throws IOException
     */
    @Override
    public void setFilePosition(long filePosition) throws IOException {
        if (leadingBitsToWrite != 0 || readTrailingBits != 0) {
            byteAlign();
        }
        raf.seek(filePosition);
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

        if (leadingBitsToWrite != 0) {
            int stringLength = outputString.length();
            for (int i = 0; i < stringLength; i++) {
                writeByte((byte) outputString.charAt(i));
            }
        } else {
            raf.writeBytes(outputString);
            if (buildingCheckByteStream && checkByteStream != null) {
                int stringLength = outputString.length();
                for (int i = 0; i < stringLength; i++) {
                    checkByteStream.add((byte) outputString.charAt(i));
                }
            }
        }
    }

    /**
     * Reads byte string from file.
     *
     * @param length Length of string to read
     * @return The string to return
     * @throws IOException
     */
    @Override
    public String getByteString(int length) throws IOException {

        // get bytes
        convertBytes = getBytes(length);

        // char for conversion
        char aChar;

        // stringBuilder
        StringBuilder byteStringBuilder = new StringBuilder();

        // loop through bytes
        for (byte aByte : convertBytes) {

            // convert byte to char
            aChar = (char) aByte;

            // append aChar to the string builder
            byteStringBuilder.append(aChar);
        }

        // return string
        return byteStringBuilder.toString();
    }

    /**
     * Writes a 2-byte char string to file.
     *
     * @param outputString string to output
     * @throws IOException
     */
    @Override
    public void writeCharString(String outputString) throws IOException {

        if (leadingBitsToWrite != 0) {
            int stringLength = outputString.length();
            for (int i = 0; i < stringLength; i++) {
                writeShort((short) outputString.charAt(i));
            }
        } else {
            raf.writeChars(outputString);
        }
    }

    /**
     * Reads 2-byte character string from file.
     *
     * @param length Length of string to read
     * @return The string to return
     * @throws IOException
     */
    @Override
    public String getCharString(int length) throws IOException {

        // stringBuilder
        StringBuilder charStringBuilder = new StringBuilder();

        // get characters
        char aChar;

        // read shorts for array
        for (int i = 0; i < length; i++) {

            // read char short
            aChar = (char) getShort();

            // append aChar to the string builder
            charStringBuilder.append(aChar);
        }

        // return string
        return charStringBuilder.toString();
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
     * Reads a UTF-8 encoded string from file.
     *
     * @return String to return
     * @throws IOException
     */
    @Override
    public String getUTF8String() throws IOException {

        // length of UTF8 data
        int length = getUShortAsInt();

        // get bytes
        convertBytes = getBytes(length);

        // string for conversion
        String utfDecodedString
                = new String(convertBytes, StandardCharsets.UTF_8);

        // return string
        return utfDecodedString;
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
        if (bitOffset != 0 || leadingBitsToWrite != 0) {

            if (leadingBitsToWrite != 0) {

                trailingBits = leadingBitsToWrite;

                leadingBitsToWrite += bitOffset;

                if (leadingBitsToWrite == 8) {

                    // the bits will byte align
                    bytesToWrite++;
                    bitOffset = 0;
                    if (bitsToWrite > leadingBitsToWrite) {
                        byteOverflowing = true;
                    }
                } else if ((leadingBitsToWrite > 8)
                        || (leadingBitsToWrite > 0 && leadingBitsToWrite
                        + bitsToWrite > 8)) {

                    // append another byte
                    byteOverflowing = true;
                    if (leadingBitsToWrite > 8) {
                        bytesToWrite++;
                    }
                }

            } else {
                leadingBitsToWrite = bitOffset;
            }
        }

        // build bytes to write
        writeBytes = new byte[bytesToWrite];

        // write in little endain order
        if (littleEndian) {

            for (int i = 0; i < bytesToWrite; i++) {

                if (bitsToWrite > leadingBitsToWrite) {

                    if (byteOverflowing) {

                        // set temperary leading bits
                        byte newLeadingBits = (leadingBitsToWrite - 8 >= 0)
                                ? (byte) (leadingBitsToWrite - 8) : leadingBitsToWrite;

                        // do the bitshifting of extra byte overflow bits
                        byte orValue = (byte) (value
                                & AND_VALUES[8 - extraBitCount]);

                        writeBytes[i] = (byte) (extraBits
                                | (orValue << extraBitCount));

                        leadingBitsToWrite = newLeadingBits;
                        bitOffset = leadingBitsToWrite;

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

                } else if (leadingBitsToWrite == 8) {

                    // values that are a byte
                    writeBytes[i] = (byte) ((value << extraBitCount)
                            | extraBits);

                    extraBitCount = 0;
                    extraBits = 0;
                    leadingBitsToWrite = 0;
                } else {

                    // do the bitshifting of extra byte overflow bits
                    // just like when there is a byte overflow
                    byte orValue = (byte) (value
                            & AND_VALUES[8 - extraBitCount]);

                    writeBytes[i] = (byte) (extraBits
                            | (orValue << (extraBitCount)));

                    leadingBitsToWrite -= 8;
                    bitOffset = leadingBitsToWrite;
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
            if (bitsToWrite > 0 && leadingBitsToWrite != 0) {

                // initialise leadingBitsToWrite
                if (leadingBitsToWrite == bitOffset) {

                    // set extra bits to the leading offset of value
                    extraBits = (byte) ((value >>> writtenBits)
                            & AND_VALUES[bitOffset]);
                    extraBitCount = bitOffset;
                } else {

                    // set extra bits to the leading offset of value
                    extraBits = (byte) (extraBits | ((value
                            & AND_VALUES[bitOffset]) << extraBitCount));
                    extraBitCount = leadingBitsToWrite;
                }
            }

        } else {

            // write in big endian order
            for (int i = 0; i < bytesToWrite; i++) {

                // values longer than leadingBitsToWrite
                if (bitsToWrite > leadingBitsToWrite) {

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
                        byte newLeadingBits = (leadingBitsToWrite - 8 >= 0)
                                ? (byte) (leadingBitsToWrite - 8) : leadingBitsToWrite;

                        // do the bitshifting of extra byte overflow bits
                        byte orValue = (byte) (value >>> (bits - (8
                                - extraBitCount)) & AND_VALUES[8 - extraBitCount]);

                        writeBytes[i] = (byte) ((extraBits << (8 - extraBitCount))
                                | orValue);

                        leadingBitsToWrite = newLeadingBits;
                        bitOffset = leadingBitsToWrite;
                        byteOverflowing = false;
                    } else {

                        // append byte to writeBytes array
                        writeBytes[i] = (byte) (value >>> bitsToWrite);
                    }
                } else if (leadingBitsToWrite == 8) {

                    // values that are a byte
                    writeBytes[i] = (byte) (value | (extraBits << leadingBitsToWrite
                            - extraBitCount));

                    extraBitCount = 0;
                    extraBits = 0;
                    leadingBitsToWrite = 0;
                } else {

                    // do the bitshifting of extra byte overflow bits
                    // just like when there is a byte overflow
                    byte orValue = (byte) ((value & 0xff) >>> (bits
                            - (8 - extraBitCount)) & AND_VALUES[8 - extraBitCount]);

                    writeBytes[i] = (byte) ((extraBits << (8 - extraBitCount))
                            | orValue);

                    // subtract 8 from leading bits
                    leadingBitsToWrite -= 8;
                    bitOffset = leadingBitsToWrite;
                    byteOverflowing = false;
                }
            }

            // write the bytes
            appendBytes(writeBytes, 0, writeBytes.length, true);

            // deal with extra bits for later
            if (bitsToWrite > 0 && leadingBitsToWrite != 0) {

                // initialise leadingBitsToWrite
                if (leadingBitsToWrite == bitOffset) {

                    // set extra bits to the leading offset of value
                    extraBits = (byte) (value & AND_VALUES[bitOffset]);
                    extraBitCount = bitOffset;
                } else {

                    // set extra bits to the leading offset of value
                    extraBits = (byte) ((extraBits << bitOffset)
                            | (value & AND_VALUES[bitOffset]));
                    extraBitCount = leadingBitsToWrite;
                }
            }
        }
    }

    /**
     * Reads an arbitrary bit length value from file.
     *
     * @param bits the bit length of value to read
     * @param signed reads signed value if true
     * @return the read value
     * @throws IOException
     */
    @Override
    public long getArbitraryBitValue(byte bits, boolean signed)
            throws IOException {

        // method variables
        int bytesToExtract;
        long returnValue = 0;
        // used in byte overflow tracking
        byte leadingBits = 0;
        boolean byteOverflowing = false;
        boolean trailingBitsProcessed = false;
        boolean byteAlign = false;

        // extract bytes
        bytesToExtract = bits / 8;

        // get extra bit offset
        bitOffset = (byte) (bits % 8);

        // deal with bit offsetted values
        if (bitOffset != 0 || readTrailingBits != 0) {

            if (readTrailingBits != 0) {

                if (readTrailingBits < 8) {

                    if ((bitOffset > readTrailingBits)
                            || (readTrailingBits > 0 && readTrailingBits + bits > 8
                            && bitOffset != readTrailingBits)) {

                        // byte overflows the trailing bits
                        byteOverflowing = true;
                        leadingBits = readTrailingBits;
                        if (bitOffset > leadingBits) {
                            bytesToExtract++;
                        }
                    } else {
                        // use to extract a few bits from extra bits
                        readTrailingBits -= bitOffset;
                        trailingBitsProcessed = true;
                        if (readTrailingBits == 0) {
                            byteAlign = true;
                        }
                    }
                }

            } else {
                readTrailingBits = (byte) (8 - bitOffset);
                bytesToExtract++;
            }
        }

        // extract bytes
        convertBytes = extractBytes(bytesToExtract, false, true);

        // build value to return
        if (littleEndian) {

            // the bits read (used in little endian bit reading)
            byte readBits = 0;

            // read value in big endian
            for (int i = 0; i < convertBytes.length; i++) {

                // deal with a byte align
                if (byteAlign) {

                    returnValue = (extraBits);
                    extraBits = 0;
                    i--;
                } // deal with byte overflow
                else if (byteOverflowing) {
                    returnValue = (extraBits);
                    readTrailingBits = (byte) (bytesToExtract * 8 - (bits
                            - leadingBits));
                    bitOffset = leadingBits;
                    i--;
                } // append byte to return value if not last byte or if there 
                // are no trailing bits
                else if (i < convertBytes.length - 1 || readTrailingBits == 0) {
                    returnValue = returnValue | ((long) (convertBytes[i] & 0xff)
                            << readBits);
                } else {

                    // append readTrailingBits bits to return value
                    returnValue = returnValue | ((convertBytes[i]
                            & AND_VALUES[8 - readTrailingBits]) << readBits);
                }

                // deal with the byte overlow
                if (byteOverflowing) {
                    readBits += extraBitCount;
                    extraBitCount = 0;
                    extraBits = 0;
                    byteOverflowing = false;
                } // bitshift one byte to return value if not the second to last 
                // byte or there are no traling bits or is byte aligned
                else if (i < convertBytes.length - 1 && !byteAlign) {

                    readBits += 8;
                } else if (byteAlign) {

                    readBits += extraBitCount;
                    extraBitCount = 0;
                    byteAlign = false;
                } else {

                    // set extra bits to the leading bits of last read byte
                    extraBits = (byte) ((convertBytes[i] >>> (8 - readTrailingBits))
                            & AND_VALUES[readTrailingBits]);

                    // set extra bit count
                    extraBitCount = readTrailingBits;
                }
            }

            // return extra bits only if convertBytes length is zero
            if (convertBytes.length == 0) {

                if (extraBitCount > bitOffset) {

                    // append readTrailingBits bits to return value
                    returnValue = extraBits & AND_VALUES[bitOffset];
                    if (!trailingBitsProcessed) {
                        readTrailingBits -= bitOffset;
                    }
                } else {

                    // return extraBits
                    returnValue = (extraBits & AND_VALUES[extraBitCount]);
                }

                if (readTrailingBits > 0) {

                    // bit shift to get rid of unneeded trailing bits
                    extraBits = (byte) ((extraBits >>> (bitOffset))
                            & AND_VALUES[readTrailingBits]);
                    extraBitCount = readTrailingBits;
                }
            }
        } else {

            // read value in big endian
            for (int i = 0; i < convertBytes.length; i++) {

                // deal with a byte align
                if (byteAlign) {

                    returnValue = (extraBits);
                    extraBitCount = 0;
                    extraBits = 0;
                    i--;
                } // deal with byte overflow
                else if (byteOverflowing) {
                    returnValue = (extraBits);
                    readTrailingBits = (byte) (bytesToExtract * 8 - (bits
                            - leadingBits));
                    bitOffset = leadingBits;
                    extraBitCount = 0;
                    extraBits = 0;
                    byteOverflowing = false;
                    i--;
                } // append byte to return value if not last byte or if there are
                // no trailing bits
                else if (i < convertBytes.length - 1 || readTrailingBits == 0) {
                    returnValue = returnValue | (convertBytes[i] & 0xff);
                } else {

                    // append readTrailingBits bits to return value
                    returnValue = returnValue | ((convertBytes[i]
                            >>> readTrailingBits) & AND_VALUES[8 - readTrailingBits]);
                }

                // bitshift one byte to return value if not the second to last 
                // byte or there are no traling bits or is byte aligned
                if (i < convertBytes.length - 2 || byteAlign) {

                    returnValue = returnValue << 8;
                    byteAlign = false;
                } else if (i == convertBytes.length - 2) {

                    // bitshift 8 - tralingBits if second to last
                    returnValue = returnValue << (8 - readTrailingBits);
                } else {

                    // set extra bits to the traling bits of last read byte
                    extraBits = (byte) (convertBytes[i]
                            & AND_VALUES[readTrailingBits]);

                    // set extra bit count
                    extraBitCount = readTrailingBits;
                }
            }

            // return extra bits only if convertBytes length is zero
            if (convertBytes.length == 0) {

                if (extraBitCount > bitOffset) {

                    // append readTrailingBits bits to return value
                    returnValue = (extraBits >>> extraBitCount - bitOffset)
                            & AND_VALUES[bitOffset];
                    if (!trailingBitsProcessed) {
                        readTrailingBits -= bitOffset;
                    }
                } else {

                    // return extraBits
                    returnValue = (extraBits & AND_VALUES[extraBitCount]);
                }

                if (readTrailingBits > 0) {

                    // bit shift to get rid of unneeded trailing bits
                    extraBits &= AND_VALUES[readTrailingBits];
                    extraBitCount = readTrailingBits;
                }
            }
        }

        if (readTrailingBits == 0) {

            // byte align
            byteAlign();
        }

        // making header bits all 1 if signed and first bit is 1
        if (signed && returnValue >>> (bits - 1) == 1) {

            // bitshift backwards and forwards to set value to signed
            returnValue = returnValue << (64 - bits);
            returnValue = returnValue >> (64 - bits);
        }

        return returnValue;
    }

    /**
     * Writes leading bits if any and byte aligns the file.
     *
     * @throws IOException
     */
    @Override
    public void byteAlign() throws IOException {

        if (leadingBitsToWrite > 0) {

            // write extra bits
            if (littleEndian) {
                writeByte(extraBits, true);
            } else {
                writeByte((byte) (extraBits << (8 - extraBitCount)), true);
            }
        }
        leadingBitsToWrite = 0;
        readTrailingBits = 0;
        extraBitCount = 0;
        extraBits = 0;
        bitOffset = 0;
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
     * Reads a double from file.
     *
     * @return the double value
     * @throws IOException
     */
    @Override
    public double getDouble() throws IOException {

        // get 8 bytes
        convertBytes = extractBytes(8, littleEndian);

        // unwrap value to return
        ByteBuffer debuffer = ByteBuffer.wrap(convertBytes);
        double returnValue = debuffer.getDouble();

        return returnValue;
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
     * Reads a long from file.
     *
     * @return the long value
     * @throws IOException
     */
    @Override
    public long getLong() throws IOException {

        // get 8 bytes
        convertBytes = extractBytes(8, littleEndian);

        // unwrap value to return
        ByteBuffer debuffer = ByteBuffer.wrap(convertBytes);
        long returnValue = debuffer.getLong();

        return returnValue;
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
     * Reads a float from file.
     *
     * @return the float value
     * @throws IOException
     */
    @Override
    public float getFloat() throws IOException {
        // get 4 bytes
        convertBytes = extractBytes(4, littleEndian);

        // unrap value to return
        ByteBuffer debuffer = ByteBuffer.wrap(convertBytes);
        float returnValue = (float) debuffer.getFloat();

        return returnValue;
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
     * Reads an unsigned integer from file.
     *
     * @return the unsigned integer value
     * @throws IOException
     */
    @Override
    public long getUIntAsLong() throws IOException {

        // get 4 bytes
        convertBytes = extractBytes(4, littleEndian);

        // splice 4 bytes with 4 0 bytes to form long
        byte[] spliceToArray = {0, 0, 0, 0,
            convertBytes[0], convertBytes[1], convertBytes[2], convertBytes[3]};

        ByteBuffer debuffer = ByteBuffer.wrap(spliceToArray);
        long returnValue = (long) debuffer.getLong();

        return returnValue;
    }

    /**
     * Reads a signed integer from file.
     *
     * @return the signed integer value
     * @throws IOException
     */
    @Override
    public int getInt() throws IOException {

        // get 4 bytes
        convertBytes = extractBytes(4, littleEndian);

        // unwrap value to return
        ByteBuffer debuffer = ByteBuffer.wrap(convertBytes);
        int returnValue = debuffer.getInt();

        return returnValue;
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
     * Reads an unsigned 24-bit value from file.
     *
     * @return the unsigned 24-bit value
     * @throws IOException
     */
    @Override
    public int getU24BitInt() throws IOException {

        // get 3 bytes
        convertBytes = extractBytes(3, littleEndian);

        // splice 3 bytes with one zero byte
        byte[] spliceToArray = {0, convertBytes[0], convertBytes[1],
            convertBytes[2]};

        ByteBuffer debuffer = ByteBuffer.wrap(spliceToArray);
        int returnValue = debuffer.getInt();

        return returnValue;
    }

    /**
     * Reads a signed 24-bit value from file.
     *
     * @return the signed 24-bit value
     * @throws IOException
     */
    @Override
    public int get24BitInt() throws IOException {

        // get 3 bytes
        convertBytes = extractBytes(3, littleEndian);

        // splice 3 bytes with one zero byte
        byte[] spliceToArray = {0, convertBytes[0], convertBytes[1],
            convertBytes[2]};

        // check if it is negative and then add 11111111 to set it negative
        if (((convertBytes[0] >>> 7) & 1) == 1) {
            spliceToArray[0] = (byte) 0xff;
        }

        ByteBuffer debuffer = ByteBuffer.wrap(spliceToArray);
        int returnValue = debuffer.getInt();

        return returnValue;
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
     * Reads an unsigned short from file.
     *
     * @return the unsigned short value
     * @throws IOException
     */
    @Override
    public int getUShortAsInt() throws IOException {

        // get 2 bytes
        convertBytes = extractBytes(2, littleEndian);

        // splice 2 bytes with 2 0 bytes to form int
        byte[] spliceToArray = {0, 0, convertBytes[0], convertBytes[1]};

        ByteBuffer debuffer = ByteBuffer.wrap(spliceToArray);
        int returnValue = debuffer.getInt();

        return returnValue;
    }

    /**
     * Reads a signed short from file.
     *
     * @return the signed short value
     * @throws IOException
     */
    @Override
    public short getShort() throws IOException {

        // get 2 bytes
        convertBytes = extractBytes(2, littleEndian);

        // unrap value to return
        ByteBuffer debuffer = ByteBuffer.wrap(convertBytes);
        short returnValue = debuffer.getShort();

        return returnValue;
    }

    /**
     * Writes a byte to file.
     *
     * @param value The byte value
     * @param ignoreOffset Used when we need to ignore byte offset stuff
     * @throws IOException
     */
    private void writeByte(byte value, boolean ignoreOffset) throws IOException {

        if (!ignoreOffset && leadingBitsToWrite != 0) {

            // bit shift a byte
            value = manageBitOffset(value, leadingBitsToWrite);
        }

        raf.writeByte(value);
        
        if (buildingCheckByteStream && checkByteStream != null) {
            checkByteStream.add(value);
        }
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
     * Reads an unsigned byte from file.
     *
     * @return the unsigned byte value
     * @throws IOException
     */
    @Override
    public short getUByteAsShort() throws IOException {

        // get byte
        byte convertedByte = raf.readByte();

        short returnValue = convertedByte;

        // and value to make sure it's unsigned
        returnValue = (short) (0x00ff & returnValue);

        return returnValue;
    }

    /**
     * Reads a signed byte from file.
     *
     * @return the signed byte value
     * @throws IOException
     */
    @Override
    public byte getByte() throws IOException {

        // get byte
        byte convertedByte = raf.readByte();
        
        // append byte to check byte stream
        if (buildingCheckByteStream && checkByteStream != null) {
            checkByteStream.add(convertedByte);
        }

        // bit shift to right position if there are traling bits
        if (extraBitCount != 0) {

            convertedByte = manageBitOffset(convertedByte, extraBitCount);
        }

        return convertedByte;
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
     * Reads a boolean value from file
     *
     * @return the boolean variable
     * @throws IOException
     */
    @Override
    public boolean getBoolean() throws IOException {
        // return value
        boolean returnValue = false;

        // value is true if read byte > 0
        if (getByte() != 0) {
            returnValue = true;
        }

        return returnValue;
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

        // if leadingBitsToWrite, deal with them
        if (!ignoreOffset && leadingBitsToWrite != 0) {

            // bitshift bytes and add leadingBitsToWrite
            for (int i = 0; i < bytesToAppend.length; i++) {

                // bit shift a byte
                bytesToAppend[i] = manageBitOffset(bytesToAppend[i],
                        leadingBitsToWrite);
            }

        }

        // write the bytes
        raf.write(bytesToAppend, start, end);
        
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
     * Reads a byte array from file
     *
     * @param bytesToExtract number of bytes to read
     * @return the byte array that is read
     * @throws IOException
     */
    @Override
    public byte[] getBytes(int bytesToExtract)
            throws IOException {

        // method variabls
        byte[] extractedBytes = new byte[bytesToExtract];
        byte bitsToShiftIn;

        // extract bytes
        raf.read(extractedBytes, 0, bytesToExtract);
        
        // append bytes to check byte stream
        if (buildingCheckByteStream && checkByteStream != null) {
            for (int i = 0; i < extractedBytes.length; i++) {
                checkByteStream.add(extractedBytes[i]);
            }
        }

        // deal with bit offset if any
        if (extraBitCount != 0) {

            // bitshift bytes tralingBits times
            for (int i = 0; i < extractedBytes.length; i++) {

                extractedBytes[i]
                        = manageBitOffset(extractedBytes[i], extraBitCount);
            }
        }

        return extractedBytes;
    }

    /**
     * Extracts a byte array to build other data type from.
     *
     * @param bytesToExtract amount of bytes to read
     * @param reverse reverse byte order if true
     * @param ignoreOffset ignore the extra bit offset if this is true
     * @return
     * @throws IOException
     */
    private byte[] extractBytes(int bytesToExtract, boolean reverse,
            boolean ignoreOffset)
            throws IOException {

        // define byte array
        byte[] extractedBytes = new byte[bytesToExtract];

        // extract bytes
        raf.read(extractedBytes, 0, bytesToExtract);
        
        // append bytes to check byte stream
        if (buildingCheckByteStream && checkByteStream != null) {
            for (int i = 0; i < extractedBytes.length; i++) {
                checkByteStream.add(extractedBytes[i]);
            }
        }

        // deal with bit offset if any
        if (!ignoreOffset && extraBitCount != 0) {

            // bitshift bytes tralingBits times
            for (int i = 0; i < extractedBytes.length; i++) {

                extractedBytes[i]
                        = manageBitOffset(extractedBytes[i], extraBitCount);
            }
        }

        // if reverse
        if (reverse) {

            // revers the order of byres
            extractedBytes = reverseEndian(extractedBytes);
        }

        return extractedBytes;
    }

    /**
     * Extracts a byte array to build other data type from.
     *
     * @param bytesToExtract amount of bytes to read
     * @param reverse reverse byte order if true
     * @return the extracted byte array
     * @throws IOException
     */
    private byte[] extractBytes(int bytesToExtract, boolean reverse)
            throws IOException {
        return extractBytes(bytesToExtract, reverse, false);
    }

    /**
     * zero fill bytes till you reach the desired position
     *
     * @param bytes Bytes to zero fill
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
     * Returns available bytes
     *
     * @return approximate amount of bytes left in file
     * @throws IOException
     */
    @Override
    public int available() throws IOException {
        return (int) (raf.length() - raf.getFilePointer());
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
        raf.close();
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
        value = (byte) ((value >>> bitCount) & AND_VALUES[8 - bitCount]);

        // set extra bit count
        extraBitCount = bitCount;

        value = (byte) (value | (bitsToShiftIn << (8 - bitCount)));

        return value;
    }
}
