/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io;

import java.io.IOException;

/**
 *
 * @author Edward Jenkins
 */
public interface IReadable {
    
    /**
     * Gets the file's position.
     *
     * @return the file's current position
     * @throws java.io.IOException
     */
    public long getFilePosition()throws IOException;
    
    /**
     * Sets endianess of file.
     *
     * @param littleEndian Little-endian if true
     */
    public void setLittleEndian(boolean littleEndian);

    /**
     * Resets the file position.
     *
     * @param filePosition The position to reset to
     * @throws IOException
     */
    public void setFilePosition(long filePosition) throws IOException;
    
    /**
     * Reads byte string from file.
     *
     * @param length Length of string to read
     * @return The string to return
     * @throws IOException
     */
    public String getByteString(int length) throws IOException;

    /**
     * Reads 2-byte character string from file.
     *
     * @param length Length of string to read
     * @return The string to return
     * @throws IOException
     */
    public String getCharString(int length) throws IOException;

    /**
     * Reads a UTF-8 encoded string from file.
     *
     * @return String to return
     * @throws IOException
     */
    public String getUTF8String() throws IOException;

    /**
     * Reads an arbitrary bit length value from file.
     *
     * @param bits the bit length of value to read
     * @param signed reads signed value if true
     * @return the read value
     * @throws IOException
     */
    public long getArbitraryBitValue(byte bits, boolean signed)
            throws IOException;

    /**
     * Byte aligns the file.
     */
    public void byteAlign() throws IOException;

    /**
     * Reads a long from file.
     *
     * @return the long value
     * @throws IOException
     */
    public long getLong() throws IOException;

    /**
     * Reads a double from file.
     *
     * @return the double value
     * @throws IOException
     */
    public double getDouble() throws IOException;

    /**
     * Reads an unsigned integer from file.
     *
     * @return the unsigned integer value
     * @throws IOException
     */
    public long getUIntAsLong() throws IOException;

    /**
     * Reads a signed integer from file.
     *
     * @return the signed integer value
     * @throws IOException
     */
    public int getInt() throws IOException;

    /**
     * Reads a float from file.
     *
     * @return the float value
     * @throws IOException
     */
    public float getFloat() throws IOException;

    /**
     * Reads an unsigned 24-bit value from file.
     *
     * @return the unsigned 24-bit value
     * @throws IOException
     */
    public int getU24BitInt() throws IOException;

    /**
     * Reads a signed 24-bit value from file.
     *
     * @return the signed 24-bit value
     * @throws IOException
     */
    public int get24BitInt() throws IOException;

    /**
     * Reads an unsigned short from file.
     *
     * @return the unsigned short value
     * @throws IOException
     */
    public int getUShortAsInt() throws IOException;

    /**
     * Reads a signed short from file.
     *
     * @return the signed short value
     * @throws IOException
     */
    public short getShort() throws IOException;

    /**
     * Reads an unsigned byte from file.
     *
     * @return the unsigned byte value
     * @throws IOException
     */
    public short getUByteAsShort() throws IOException;

    /**
     * Reads a signed byte from file.
     *
     * @return the signed byte value
     * @throws IOException
     */
    public byte getByte() throws IOException;

    /**
     * Reads a boolean value from file
     * @return the boolean variable
     * @throws IOException 
     */
    public boolean getBoolean() throws IOException;

    /**
     * Reads a byte array from file
     *
     * @param bytesToExtract number of bytes to read
     * @return the byte array that is read
     * @throws IOException
     */
    public byte[] getBytes(int bytesToExtract)
            throws IOException;
    
    /**
     * Skip a provided number of bytes.
     *
     * @param bytes The amount of bytes to skip.
     * @return true if successful
     * @throws IOException
     */
    public boolean skipBytes(long bytes) throws IOException;

    /**
     * Returns available bytes
     * 
     * @return approximate amount of bytes left in file
     * @throws IOException 
     */
    public int available() throws IOException;

    /**
     * Closes the reader.
     * 
     * @throws IOException 
     */
    public void close() throws IOException;
}
