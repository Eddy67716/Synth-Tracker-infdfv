/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io_stuff;

import static io_stuff.IOMethods.reverseEndian;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Edward Jenkins
 */
public class ReadMethods {

    // constants
    public static final byte[] AND_VALUES = {0, 1, 3, 7, 15, 31, 63, 127};

    // instance variables
    private DataInputStream dis;
    private String fileName;
    private boolean littleEndian;
    private byte[] convertBytes;
    private long filePosition;
    private byte extraBits;
    private byte extraBitCount;
    private byte trailingBits;

    /**
     * The 2-args constructor for this object.
     *
     * @param fileName Name of file to read
     * @param littleEndian Reads Little-endian if true
     * @throws java.io.IOException
     */
    public ReadMethods(String fileName, boolean littleEndian)
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
    public ReadMethods(String fileName) throws IOException {
        this(fileName, false);
    }
    
    /**
     * Returns the data input stream used to read data.
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
    public void setLittleEndian(boolean littleEndian) {
        this.littleEndian = littleEndian;
    }

    /**
     * Resets the file position.
     *
     * @param filePosition The position to reset to
     * @throws IOException
     */
    public void setFilePosition(long filePosition) throws IOException {
        this.filePosition = filePosition;
        this.dis.close();
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
    public String getCharString(int length) throws IOException {

        // stringBuilder
        StringBuilder byteStringBuilder = new StringBuilder();

        // get characters
        char aChar;

        // read shorts for array
        for (int i = 0; i < length; i++) {

            // read char short
            aChar = (char) getShort();

            // append aChar to the string builder
            byteStringBuilder.append(aChar);
        }

        // return string
        return byteStringBuilder.toString();
    }

    /**
     * Reads a UTF-8 encoded string from file.
     *
     * @return String to return
     * @throws IOException
     */
    public String getUTF8ByteString() throws IOException {

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
    public long getArbitraryBitValue(byte bits, boolean signed)
            throws IOException {

        // method variables
        int bytesToExtract;
        long returnValue = 0;
        boolean littleEndian = this.littleEndian;
        byte bitOffset;

        // extract bytes
        bytesToExtract = bits / 8;

        // get extra bit offset
        bitOffset = (byte) (bits % 8);

        // deal with bit offsetted values
        if (bitOffset != 0) {

            if (trailingBits != 0) {

                if (trailingBits < 8) {

                    // use to extract a few bits from extra bits
                    trailingBits -= bitOffset;
                } else if (trailingBits > bitOffset) {

                } else if (trailingBits == 8) {

                    // the bits will byte align
                    bytesToExtract--;
                    trailingBits = 0;
                } else if (trailingBits > 8) {

                    // subtract 8 from trailing bits and append another byte
                    trailingBits -= 8;
                    bytesToExtract++;
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
        convertBytes = extractBytes(bytesToExtract, littleEndian);

        // build value to return
        for (int i = 0; i < convertBytes.length; i++) {

            // append byte to return value if not last byte or if there are
            // no trailing bits
            if (i < convertBytes.length - 1 || trailingBits == 0) {
                returnValue = returnValue | (convertBytes[i] & 0xff);
            } else {

                // append trailingBits bits to return value
                returnValue = returnValue | ((convertBytes[i] >>> trailingBits)
                        & AND_VALUES[bitOffset]);
            }

            // bitshift one byte to return value if not the second to last byte
            // or there are no traling bits
            if (i < convertBytes.length - 2) {

                returnValue = returnValue << 8;
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
                returnValue = (extraBits >>> trailingBits) 
                        & AND_VALUES[bitOffset];
            } else {

                // return extraBits
                returnValue = (extraBits & AND_VALUES[extraBitCount]);
            }

            if (trailingBits > 0) {

                // bit shift to get rid of unneeded trailing bits
                extraBits = (byte) ((extraBits >>> trailingBits)
                        & AND_VALUES[trailingBits]);
                extraBitCount = trailingBits;
            }
        }

        if (trailingBits == 0) {

            // byte align
            byteAlign();
        }

        // making header bits all 1 if signed
        if (signed && returnValue >>> (bits - 1) == 1) {

            // iterate through extra header bits and make them 1
            for (int i = 63; i >= bits; i--) {
                returnValue = returnValue | (1 >> i);
            }
        }

        this.littleEndian = littleEndian;
        return returnValue;
    }

    /**
     * Byte aligns the file.
     */
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
    
    public boolean getBoolean() throws IOException
    {
        // return value
        boolean returnValue = false;
        
        // value is true if read byte > 0
        if(getByte() != 0) {
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
    public byte[] getBytes(int bytesToExtract)
            throws IOException {

        // method variabls
        byte[] extractedBytes = new byte[bytesToExtract];
        byte bitsToShiftIn;

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
     * @return
     * @throws IOException
     */
    private byte[] extractBytes(int bytesToExtract, boolean reverse)
            throws IOException {

        // define byte array
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

        // if reverse
        if (reverse) {

            // revers the order of byres
            extractedBytes = reverseEndian(extractedBytes);
        }

        return extractedBytes;
    }

    /**
     * Skip a provided number of bytes.
     *
     * @param bytes The amount of bytes to skip.
     * @return true if successful
     * @throws IOException
     */
    public boolean skipBytes(long bytes) throws IOException {

        boolean skipped = false;

        if (dis.available() > bytes) {
            dis.skip(bytes);
            filePosition += bytes;
            skipped = true;
        }

        return skipped;
    }
    
    public int available() throws IOException {
        return dis.available();
    }
    
    public void close() throws IOException {
        dis.close();
    }

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
