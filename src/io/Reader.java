/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import static io.IOMethods.reverseEndian;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Edward Jenkins © 2021-2023
 */
public class Reader implements IReadable{

    // constants
    public static final byte[] AND_VALUES = {0, 1, 3, 7, 15, 31, 63, 127};

    // instance variables
    private DataInputStream dis;        // the data input stream used in reading file
    private String fileName;            // name of file
    private boolean littleEndian;       // stores if file is reading in little endian or not
    private byte[] convertBytes;
    private long filePosition;          // stores the position in the file
    private byte extraBits;             // the extra bits that can't yet be written
    private byte extraBitCount;         // count of extra bits
    private byte trailingBits;          // count of trailing bits
    private byte bitOffset;             // the bit offset over the byte mark

    /**
     * The 2-args constructor for this object.
     *
     * @param fileName Name of file to read
     * @param littleEndian Reads Little-endian if true
     * @throws java.io.IOException
     */
    public Reader(String fileName, boolean littleEndian)
            throws IOException {
        this.dis = new DataInputStream(new BufferedInputStream(
                new FileInputStream(fileName)));
        this.littleEndian = littleEndian;
        filePosition = 0;
        trailingBits = 0;
        this.fileName = fileName;
    }

    /**
     * The 1-arg constructor for this object.It can only read big-endian.
     *
     * @param fileName The name of the file to read to.
     * @throws java.io.IOException I/O exception
     */
    public Reader(String fileName) throws IOException {
        this(fileName, false);
    }

    /**
     * Returns the data input stream used to read data.
     *
     * @return the data input stream
     */
    public DataInputStream getDis() {
        return this.dis;
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
     * Sets the DataInputStream.
     *
     * @param dis the new DataInputStream object
     */
    public void setDis(DataInputStream dis) {
        this.dis = dis;
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
        this.filePosition = filePosition;
        this.dis = new DataInputStream(new BufferedInputStream(
                new FileInputStream(fileName)));
        dis.skip(filePosition);
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
        byte leadingBits = 0;           // used in byte overflow tracking
        boolean littleEndian = this.littleEndian;
        boolean byteOverflowing = false;
        boolean trailingBitsProcessed = false;
        boolean byteAlign = false;

        // extract bytes
        bytesToExtract = bits / 8;

        // get extra bit offset
        bitOffset = (byte) (bits % 8);

        // deal with bit offsetted values
        if (bitOffset != 0) {

            if (trailingBits != 0) {

                if (trailingBits < 8) {

                    if ((bitOffset > trailingBits) 
                            || (trailingBits > 0 && trailingBits + bits > 8
                            && bitOffset != trailingBits)) {

                        // byte overflows the trailing bits
                        byteOverflowing = true;
                        leadingBits = trailingBits;
                        if (bitOffset > leadingBits) {
                            bytesToExtract++;
                        }
                    } else {
                        // use to extract a few bits from extra bits
                        trailingBits -= bitOffset;
                        trailingBitsProcessed = true;
                        if (trailingBits == 0) {
                            byteAlign = true;
                        }
                    }
                } else if (trailingBits == 8) {

                    // the bits will byte align
                    bytesToExtract--;
                    trailingBits = 0;
                }

            } else {
                trailingBits = (byte) (8 - bitOffset);
                bytesToExtract++;
            }

            // set to big endian for arbitrary bit values that aren't byte
            // aligned.
            this.littleEndian = false;
        }

        // extract bytes
        convertBytes = extractBytes(bytesToExtract, this.littleEndian, true);

        // build value to return
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
                trailingBits = (byte) (bytesToExtract * 8 - (bits - leadingBits));
                bitOffset = leadingBits;
                extraBitCount = 0;
                extraBits = 0;
                byteOverflowing = false;
                i--;
            } // append byte to return value if not last byte or if there are
            // no trailing bits
            else if (i < convertBytes.length - 1 || trailingBits == 0) {
                returnValue = returnValue | (convertBytes[i] & 0xff);
            } else {

                // append trailingBits bits to return value
                returnValue = returnValue | ((convertBytes[i] >>> trailingBits)
                        & AND_VALUES[8 - trailingBits]);
            }

            
            // bitshift one byte to return value if not the second to last byte
            // or there are no traling bits or is byte aligned
            if (i < convertBytes.length - 2 || byteAlign) {

                returnValue = returnValue << 8;
                byteAlign = false;
            } else if (i == convertBytes.length - 2) {

                // bitshift 8 - tralingBits if second to last
                returnValue = returnValue << (8 - trailingBits);
            } else if (extraBitCount != trailingBits
                    && i == convertBytes.length - 1
                    && trailingBits == 0) {

                // bitshift to left and append extra bits
                returnValue = (returnValue << extraBitCount) | extraBits;

            } else {

                // set extra bits to the traling bits of last read byte
                extraBits = (byte) (convertBytes[i] & AND_VALUES[trailingBits]);

                // set extra bit count
                extraBitCount = trailingBits;
            }
        }

        // return extra bits only if convertBytes length is zero
        if (convertBytes.length == 0) {

            if (extraBitCount > bitOffset) {

                // append trailingBits bits to return value
                returnValue = (extraBits >>> extraBitCount - bitOffset)
                        & AND_VALUES[bitOffset];
                if (!trailingBitsProcessed) {
                    trailingBits -= bitOffset;
                }
            } else {

                // return extraBits
                returnValue = (extraBits & AND_VALUES[extraBitCount]);
            }

            if (trailingBits > 0) {

                // bit shift to get rid of unneeded trailing bits
                extraBits = (byte) ((extraBits >>> extraBitCount - trailingBits)
                        & AND_VALUES[trailingBits]);
                extraBitCount = trailingBits;
            }
        }

        if (trailingBits == 0) {

            // byte align
            byteAlign();
        }

        // making header bits all 1 if signed and first bit is 1
        if (signed && returnValue >>> (bits - 1) == 1) {
            
            // bitshift backwards and forwards to set value to signed
            returnValue = returnValue << (64 - bits);
            returnValue = returnValue >> (64 - bits);
        }

        this.littleEndian = littleEndian;
        return returnValue;
    }

    /**
     * Byte aligns the file.
     */
    @Override
    public void byteAlign() {

        trailingBits = 0;
        extraBitCount = 0;
        extraBits = 0;
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
     * Reads an unsigned byte from file.
     *
     * @return the unsigned byte value
     * @throws IOException
     */
    @Override
    public short getUByteAsShort() throws IOException {

        // get byte
        byte convertedByte = dis.readByte();

        short returnValue = convertedByte;

        // and value to make sure it's unsigned
        returnValue = (short) (0x00ff & returnValue);

        // increment file position
        filePosition++;

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
        byte convertedByte = dis.readByte();

        // increment file position
        filePosition++;

        // bit shift to right position if there are traling bits
        if (extraBitCount != 0) {

            convertedByte = manageBitOffset(convertedByte, extraBitCount);
        }

        return convertedByte;
    }

    /**
     * Reads a boolean value from file
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

        // extract bytes
        dis.read(extractedBytes, 0, bytesToExtract);

        // increment file pos by number of bytes read
        filePosition += bytesToExtract;

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
        dis.read(extractedBytes, 0, bytesToExtract);

        // increment file pos by number of bytes read
        filePosition += bytesToExtract;

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
     * Skip a provided number of bytes.
     *
     * @param bytes The amount of bytes to skip.
     * @return true if successful
     * @throws IOException
     */
    @Override
    public boolean skipBytes(long bytes) throws IOException {

        boolean skipped = false;

        if (dis.available() > bytes) {
            dis.skip(bytes);
            filePosition += bytes;
            skipped = true;
        }

        return skipped;
    }

    /**
     * Returns available bytes
     * 
     * @return approximate amount of bytes left in file
     * @throws IOException 
     */
    @Override
    public int available() throws IOException {
        return dis.available();
    }

    /**
     * Closes the reader.
     * 
     * @throws IOException 
     */
    @Override
    public void close() throws IOException {
        dis.close();
    }

    /**
     * Processes any bit-shifting needed on the value
     * 
     * @param value the byte to process
     * @param bitCount the amount of bits to bit shift
     * @return the processed value
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