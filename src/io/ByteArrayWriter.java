/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io;

import static io.IOMethods.reverseEndian;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edward Jenkins © 2021-2023
 */
public class ByteArrayWriter extends AbstractWriter {

    // constants
    public static final byte[] AND_VALUES = {0, 1, 3, 7, 15, 31, 63, 127};

    // instance variables
    // the data output stream
    private DataOutputStream dos;
    // the byte array to store data before writing it all
    private List<Byte> byteArray;

    /**
     * The 2-args constructor used to write a file with a file name string.
     *
     * @param fileName The name of the file to write to
     * @param littleEndian Writes little-endian values if true
     * @throws IOException Thrown if file is not found
     */
    public ByteArrayWriter(String fileName, boolean littleEndian)
            throws IOException {
        super(fileName, littleEndian);
        dos = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream(fileName)));
        byteArray = new LinkedList<>();
    }

    /**
     * The 1-args constructor used to write a file with a file name string. This
     * can only write in big-endian.
     *
     * @param fileName The name of the file to write to
     * @throws IOException Thrown if file is not found
     */
    public ByteArrayWriter(String fileName)
            throws IOException {
        this(fileName, false);
    }

    /**
     * The 1-arg constructor used to just build an array.
     *
     * @param littleEndian The endieness of the array
     */
    public ByteArrayWriter(boolean littleEndian) throws IOException {
        super(littleEndian);
        byteArray = new LinkedList<>();
    }
    
    /**
     * Writes all bytes in the byte array and resets it
     *
     * @throws IOException
     */
    public void writeAndClearArray() throws IOException {
        byteAlign();
        for (Byte writeByte : byteArray) {
            dos.writeByte(writeByte);
        }
        resetByteArray();
        dos.close();
    }

    /**
     * Extracts the array from the writer
     *
     * @return the extracted array
     */
    public byte[] extractArray() {
        byte[] extractionArray = new byte[byteArray.size()];

        Iterator listIterator = byteArray.iterator();

        for (int i = 0; i < extractionArray.length; i++) {
            if (listIterator.hasNext()) {
                extractionArray[i] = (byte) listIterator.next();
            }
        }

        return extractionArray;
    }

    /**
     * Resets the byte array
     */
    public void resetByteArray() {
        byteArray = new LinkedList<>();
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
        for (Byte writeByte : byteArray) {
            dos.writeByte(writeByte);
        }
        byteArray = null;
        dos.close();
    }

    @Override
    protected void writeByteToFile(byte byteToWrite) throws IOException {
        this.byteArray.add(byteToWrite);
    }

    @Override
    protected void writeBytesToFile(byte[] bytesToWrite) throws IOException {
        for (byte byteToWrite : bytesToWrite) {
            byteArray.add(byteToWrite);
        }
    }
    
    protected void writeBytesToFile(byte[] bytesToWrite, int start, int length) 
            throws IOException {
        for (int i = start; i < start + length; i++) {
            byteArray.add(bytesToWrite[i]);
        }
    }
}
