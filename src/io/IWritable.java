/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package io;

import java.io.IOException;

/**
 *
 * @author Edward Jenkins © 2021-2023
 */
public interface IWritable {
    
    /**
     * Gets the file's position.
     *
     * @return the file's current position
     * @throws java.io.IOException
     */
    public long getFilePosition() throws IOException;
    
    /**
     * Sets endianess of file.
     *
     * @param littleEndian Little-endian if true
     */
    public void setLittleEndian(boolean littleEndian);
    
    /**
     * Starts saving a check byte stream that can be used for 
     * CRC or other checks.
     */
    public void buildCheckByteStream();
    
    /**
     * Gets the check byte stream that has been saved.
     * @return the check byte stream
     */
    public byte[] getCheckByteStream();
    
    /**
     * Resets the check byte stream.
     */
    public void resetCheckByteStream();
    
    /**
     * End the check byte stream.
     */
    public void endCheckByteStream();

    /**
     * Writes a byte string to file.
     *
     * @param outputString string to output.
     * @throws IOException
     */
    public void writeByteString(String outputString) throws IOException;

    /**
     * Writes a 2-byte char string to file.
     *
     * @param outputString string to output
     * @throws IOException
     */
    public void writeCharString(String outputString) throws IOException;

    /**
     * Writes a UTF-8 encoded string to file.
     *
     * @param outputString string to encode
     * @throws IOException
     */
    public void writeUTF8String(String outputString) throws IOException;

    /**
     * Writes a value that is of an arbitrary bit length to file.
     *
     * @param value The value to write
     * @param bits The bit length of value
     * @throws IOException
     */
    public void writeArbitraryBitValue(long value, byte bits)
            throws IOException;

    /**
     * Writes leading bits if any and byte aligns the file.
     *
     * @throws IOException
     */
    public void byteAlign() throws IOException;

    /**
     * Writes a double value to file.
     *
     * @param value The double to write
     * @throws IOException
     */
    public void writeDouble(double value) throws IOException;

    /**
     * Writes a long value to file.
     *
     * @param value The long to write
     * @throws IOException
     */
    public void writeLong(long value) throws IOException;

    /**
     * Writes a float value to file.
     *
     * @param value The float to write
     * @throws IOException
     */
    public void writeFloat(float value) throws IOException;

    /**
     * Writes an integer value to file.
     *
     * @param value The integer to write
     * @throws IOException
     */
    public void writeInt(int value) throws IOException;

    /**
     * Writes a 24-bit value to file.
     *
     * @param value The 24-bit value
     * @throws IOException
     */
    public void writeIntAsTwentyFourBit(int value) throws IOException;

    /**
     * Writes a short to file.
     *
     * @param value The short value
     * @throws IOException
     */
    public void writeShort(short value) throws IOException;

    /**
     * Writes a byte to file.
     *
     * @param value The byte value
     * @throws IOException
     */
    public void writeByte(byte value) throws IOException;

    /**
     * Writes a boolean value to file
     *
     * @param value the boolean to write
     * @throws IOException
     */
    public void writeBoolean(boolean value) throws IOException;

    /**
     * Writes an array of bytes to the file
     *
     * @param bytes the bytes to write
     * @throws IOException
     */
    public void writeBytes(byte[] bytes) throws IOException;
    
    /**
     * zero fill bytes till you reach the desired position
     *
     * @param bytes Bytes to zero fill
     * @return true if successful
     * @throws IOException
     */
    public boolean skipBytes(long bytes) throws IOException;

    /**
     * Closes the file by writing any extra bits and byte aligning then closing
     * the stream.
     *
     * @throws IOException
     */
    public void close() throws IOException;
}
