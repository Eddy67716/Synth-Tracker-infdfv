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
import java.io.EOFException;
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
public class ByteArrayReader extends AbstractReader {

    // constants
    public static final byte[] AND_VALUES = {0, 1, 3, 7, 15, 31, 63, 127};

    // instance variables
    // the main byte array
    private byte[] fileBytes;

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
        super(fileName, littleEndian);
        this.fileBytes = readBytes;
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
        super(littleEndian);
        this.fileBytes = readBytes;
    }

    /**
     * The 2-args constructor for this object. Reads the entire file into an
     * array.
     *
     * @param fileName Name of file to read
     * @param littleEndian Reads Little-endian if true
     * @throws java.io.IOException
     */
    public ByteArrayReader(String fileName, boolean littleEndian)
            throws IOException {
        super(fileName, littleEndian);
        try (DataInputStream readStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(fileName)))) {
            this.fileBytes = readStream.readAllBytes();
        }
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
     * Closes the reader.
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        fileBytes = null;
    }

    public boolean isInBounds() {
        boolean beforeLength = getFilePosition() < fileBytes.length;

        if (getFilePosition() == fileBytes.length) {
            return (getExtraBitCount() > 0);
        }

        return beforeLength;
    }

    @Override
    protected byte readByte() throws IOException {
        if (getFilePosition() < fileBytes.length) {
            return fileBytes[(int)getFilePosition()];
        } else {
            throw (new EOFException("End of array reached."));
        }
    }

    @Override
    protected void readBytes(byte[] array) throws IOException {
        int filePosition = (int) getFilePosition();
        for (int i = 0; i < array.length; i++, filePosition++) {

            if (filePosition >= fileBytes.length) {
                array[i] = 0;
            } else {
                array[i] = fileBytes[filePosition];
            }
        }
    }

    @Override
    protected int getAvailableBytes() throws IOException {
        return (int) (fileBytes.length - getFilePosition());
    }

    @Override
    protected void skipBytes(int bytes) throws IOException {
        setFilePosition(getFilePosition() + bytes);
    }
}
