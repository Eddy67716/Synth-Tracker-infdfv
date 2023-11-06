package io;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import io.*;
import static io.IOMethods.reverseEndian;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edward Jenkins © 2021-2023
 */
public class ByteArrayReader implements IReadable {

    // constants
    public static final byte[] AND_VALUES = {0, 1, 3, 7, 15, 31, 63, 127};

    // instance variables
    // name of file
    private String fileName;
    // stores if file is reading in little endian or not
    private boolean littleEndian;
    // the main byte array
    private byte[] byteArray;
    // the check byte stream used if a portion of the file is needed
    private List<Byte> checkByteStream;
    // add bytes to check byte stream if true
    private boolean buildingCheckByteStream;
    // byted needed to build values
    private byte[] readBytes;
    // stores the position in the file
    private int filePosition;
    // the extra bits that can't yet be written
    private byte extraBits;
    // count of extra bits
    private byte extraBitCount;
    // count of trailing bits
    private byte trailingBits;
    // the bit offset over the byte mark
    private byte bitOffset;

    /**
     * The 3-args constructor for this object.
     *
     * @param fileName Name of file to read
     * @param littleEndian Reads Little-endian if true
     * @param readBytes the array of bytes to read from
     * @throws java.io.IOException
     */
    public ByteArrayReader(String fileName, boolean littleEndian,
            byte[] readBytes) throws IOException {
        this.byteArray = readBytes;
        this.littleEndian = littleEndian;
        filePosition = 0;
        trailingBits = 0;
        this.fileName = fileName;
    }

    /**
     * The 2-args constructor for this object.
     *
     * @param readBytes the array of bytes to read from
     * @param littleEndian Reads Little-endian if true
     * @throws java.io.IOException
     */
    public ByteArrayReader(byte[] readBytes, boolean littleEndian)
            throws IOException {
        this.byteArray = readBytes;
        this.littleEndian = littleEndian;
        filePosition = 0;
        trailingBits = 0;
    }

    /**
     * The 2-args constructor for this object. Reads the entire file into an
     * array.
     *
     * @param fileName Name of file to read
     * @param littleEndian Reads Little-endian if true
     * @param bytesToExtract the amount of bytes to read into the reader
     * @throws java.io.IOException
     */
    public ByteArrayReader(String fileName, boolean littleEndian)
            throws IOException {
        try (DataInputStream readStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(fileName)))) {
            this.byteArray = readStream.readAllBytes();
        }
        this.littleEndian = littleEndian;
        filePosition = 0;
        trailingBits = 0;
        this.fileName = fileName;
    }

    /**
     * The 1-arg constructor for this object. It can only read big-endian.
     *
     * @param fileName The name of the file to read to.
     * @throws java.io.IOException I/O exception
     */
    public ByteArrayReader(String fileName) throws IOException {
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
     * Resets the file position.
     *
     * @param filePosition The position to reset to
     * @throws IOException
     */
    @Override
    public void setFilePosition(long filePosition) throws IOException {
        this.filePosition = (int) filePosition;
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
     * Reads byte string from file.
     *
     * @param length Length of string to read
     * @return The string to return
     * @throws IOException
     */
    @Override
    public String getByteString(int length) throws IOException {

        // get bytes
        readBytes = getBytes(length);

        // char for conversion
        char aChar;

        // stringBuilder
        StringBuilder byteStringBuilder = new StringBuilder();

        // loop through bytes
        for (byte aByte : readBytes) {

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
        readBytes = getBytes(length);

        // string for conversion
        String utfDecodedString
                = new String(readBytes, StandardCharsets.UTF_8);

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
                    byteAlign = (trailingBits == 0);
                }
            }
        } else if (bitOffset != 0) {
            trailingBits = (byte) (8 - bitOffset);
            bytesToExtract++;
        }

        // extract bytes
        readBytes = extractBytes(bytesToExtract, false, true);

        // build value to return
        if (littleEndian) {

            // the bits read (used in little endian bit reading)
            byte readBits = 0;

            // read value in big endian
            for (int i = 0; i < readBytes.length; i++) {

                // deal with a byte align
                if (byteAlign) {

                    returnValue = (extraBits);
                    extraBits = 0;
                    i--;
                } // deal with byte overflow
                else if (byteOverflowing) {
                    returnValue = (extraBits);
                    trailingBits = (byte) (bytesToExtract * 8 - (bits
                            - leadingBits));
                    bitOffset = leadingBits;
                    i--;
                } // append byte to return value if not last byte or if there 
                // are no trailing bits
                else if (i < readBytes.length - 1 || trailingBits == 0) {
                    returnValue = returnValue | ((long) (readBytes[i] & 0xff)
                            << readBits);
                } else {

                    // append trailingBits bits to return value
                    returnValue = returnValue | ((readBytes[i]
                            & AND_VALUES[8 - trailingBits]) << readBits);
                }

                // deal with the byte overlow
                if (byteOverflowing) {
                    readBits += extraBitCount;
                    extraBitCount = 0;
                    extraBits = 0;
                    byteOverflowing = false;
                } // bitshift one byte to return value if not the second to last 
                // byte or there are no traling bits or is byte aligned
                else if (i < readBytes.length - 1 && !byteAlign) {

                    readBits += 8;
                } else if (byteAlign) {

                    readBits += extraBitCount;
                    extraBitCount = 0;
                    byteAlign = false;
                } else {

                    // set extra bits to the leading bits of last read byte
                    extraBits = (byte) ((readBytes[i] >>> (8 - trailingBits))
                            & AND_VALUES[trailingBits]);

                    // set extra bit count
                    extraBitCount = trailingBits;
                }
            }

            // return extra bits only if readBytes length is zero
            if (readBytes.length == 0) {

                if (extraBitCount > bitOffset) {

                    // append trailingBits bits to return value
                    returnValue = extraBits & AND_VALUES[bitOffset];
                    if (!trailingBitsProcessed) {
                        trailingBits -= bitOffset;
                    }
                } else {

                    // return extraBits
                    returnValue = (extraBits & AND_VALUES[extraBitCount]);
                }

                if (trailingBits > 0) {

                    // bit shift to get rid of unneeded trailing bits
                    extraBits = (byte) ((extraBits >>> (bitOffset))
                            & AND_VALUES[trailingBits]);
                    extraBitCount = trailingBits;
                }
            }
        } else {

            // read value in big endian
            for (int i = 0; i < readBytes.length; i++) {

                // deal with a byte align
                if (byteAlign) {

                    returnValue = (extraBits);
                    extraBitCount = 0;
                    extraBits = 0;
                    i--;
                } // deal with byte overflow
                else if (byteOverflowing) {
                    returnValue = (extraBits);
                    trailingBits = (byte) (bytesToExtract * 8 - (bits
                            - leadingBits));
                    bitOffset = leadingBits;
                    extraBitCount = 0;
                    extraBits = 0;
                    byteOverflowing = false;
                    i--;
                } // append byte to return value if not last byte or if there are
                // no trailing bits
                else if (i < readBytes.length - 1 || trailingBits == 0) {
                    returnValue = returnValue | (readBytes[i] & 0xff);
                } else {

                    // append trailingBits bits to return value
                    returnValue = returnValue | ((readBytes[i]
                            >>> trailingBits) & AND_VALUES[8 - trailingBits]);
                }

                // bitshift one byte to return value if not the second to last 
                // byte or there are no traling bits or is byte aligned
                if (i < readBytes.length - 2 || byteAlign) {

                    returnValue = returnValue << 8;
                    byteAlign = false;
                } else if (i == readBytes.length - 2) {

                    // bitshift 8 - tralingBits if second to last
                    returnValue = returnValue << (8 - trailingBits);
                } else {

                    // set extra bits to the traling bits of last read byte
                    extraBits = (byte) (readBytes[i]
                            & AND_VALUES[trailingBits]);

                    // set extra bit count
                    extraBitCount = trailingBits;
                }
            }

            // return extra bits only if readBytes length is zero
            if (readBytes.length == 0) {

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
                    extraBits &= AND_VALUES[trailingBits];
                    extraBitCount = trailingBits;
                }
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
        readBytes = extractBytes(8, littleEndian);

        // unwrap value to return
        ByteBuffer debuffer = ByteBuffer.wrap(readBytes);
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
        readBytes = extractBytes(8, littleEndian);

        // unwrap value to return
        ByteBuffer debuffer = ByteBuffer.wrap(readBytes);
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
        readBytes = extractBytes(4, littleEndian);

        // splice 4 bytes with 4 0 bytes to form long
        byte[] spliceToArray = {0, 0, 0, 0,
            readBytes[0], readBytes[1], readBytes[2], readBytes[3]};

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
        readBytes = extractBytes(4, littleEndian);

        // unwrap value to return
        ByteBuffer debuffer = ByteBuffer.wrap(readBytes);
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
        readBytes = extractBytes(4, littleEndian);

        // unrap value to return
        ByteBuffer debuffer = ByteBuffer.wrap(readBytes);
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
        readBytes = extractBytes(3, littleEndian);

        // splice 3 bytes with one zero byte
        byte[] spliceToArray = {0, readBytes[0], readBytes[1],
            readBytes[2]};

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
        readBytes = extractBytes(3, littleEndian);

        // splice 3 bytes with one zero byte
        byte[] spliceToArray = {0, readBytes[0], readBytes[1],
            readBytes[2]};

        // check if it is negative and then add 11111111 to set it negative
        if (((readBytes[0] >>> 7) & 1) == 1) {
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
        readBytes = extractBytes(2, littleEndian);

        // splice 2 bytes with 2 0 bytes to form int
        byte[] spliceToArray = {0, 0, readBytes[0], readBytes[1]};

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
        readBytes = extractBytes(2, littleEndian);

        // unrap value to return
        ByteBuffer debuffer = ByteBuffer.wrap(readBytes);
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
        byte convertedByte = getByte();

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
        byte convertedByte = byteArray[filePosition];

        // increment file position
        filePosition++;

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
     * Reads a byte array from file
     *
     * @param bytesToExtract number of bytes to read
     * @return the byte array that is read
     * @throws IOException
     */
    @Override
    public byte[] getBytes(int bytesToExtract)
            throws IOException {

        // define byte array
        byte[] extractedBytes = new byte[bytesToExtract];

        for (int i = 0; i < extractedBytes.length; i++, filePosition++) {

            if (filePosition >= byteArray.length) {
                throw new IndexOutOfBoundsException(
                        "Index has reached the end of file");
            }

            extractedBytes[i] = byteArray[filePosition];

            // append bytes to check byte stream
            if (buildingCheckByteStream && checkByteStream != null) {
                checkByteStream.add(extractedBytes[i]);
            }

            if (extraBitCount != 0) {
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

        for (int i = 0; i < extractedBytes.length; i++, filePosition++) {

            if (filePosition >= byteArray.length) {
                extractedBytes[i] = 0;
            } else {
                extractedBytes[i] = byteArray[filePosition];
            }

            // append bytes to check byte stream
            if (buildingCheckByteStream && checkByteStream != null) {
                checkByteStream.add(extractedBytes[i]);
            }

            if (!ignoreOffset && extraBitCount != 0) {
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

        if (available() > bytes) {
            filePosition += bytes;
            skipped = true;
        }

        // append bytes to check byte stream
        if (buildingCheckByteStream && checkByteStream != null) {
            for (int i = 0; i < bytes; i++) {
                checkByteStream.add((byte)0);
            }
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
        return (int) (readBytes.length - filePosition);
    }

    /**
     * Closes the reader.
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        readBytes = null;
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

            // bit shift a byte
            value = (byte) ((value >>> bitCount) & AND_VALUES[8 - bitCount]);

            // set extra bit count
            extraBitCount = bitCount;

            value = (byte) (value | (bitsToShiftIn << (8 - bitCount)));
        }

        return value;
    }

    public boolean isInBounds() {
        boolean beforeLength = filePosition < byteArray.length;

        if (filePosition == byteArray.length) {
            return (extraBitCount > 0);
        }

        return beforeLength;
    }
}
