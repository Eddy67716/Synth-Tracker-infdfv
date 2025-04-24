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
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edward Jenkins © 2021-2023
 */
public class Reader extends AbstractReader {

    // constants
    public static final byte[] AND_VALUES = {0, 1, 3, 7, 15, 31, 63, 127};

    // instance variables
    // the data input stream used in reading file
    private DataInputStream dis;

    /**
     * The 2-args constructor for this object.
     *
     * @param fileName Name of file to read
     * @param littleEndian Reads Little-endian if true
     * @throws java.io.IOException
     */
    public Reader(String fileName, boolean littleEndian)
            throws IOException {
        super(fileName, littleEndian);
        this.dis = new DataInputStream(new BufferedInputStream(
                new FileInputStream(fileName)));
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
     * Sets the DataInputStream.
     *
     * @param dis the new DataInputStream object
     */
    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }

    /**
     * Resets the file position.
     *
     * @param filePosition The position to reset to
     * @throws IOException
     */
    @Override
    public void setFilePosition(long filePosition) throws IOException {
        this.dis = new DataInputStream(new BufferedInputStream(
                new FileInputStream(getFileName())));
        dis.skip(filePosition);
        super.setFilePosition(filePosition);
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

    @Override
    protected byte readByte() throws IOException {
        return dis.readByte();
    }

    @Override
    protected void readBytes(byte[] byteArray) throws IOException {
        dis.read(byteArray, 0, byteArray.length);
    }

    @Override
    protected int getAvailableBytes() throws IOException {
        return dis.available();
    }

    @Override
    protected void skipBytes(int bytes) throws IOException {
        dis.skipBytes(bytes);
    }
}
