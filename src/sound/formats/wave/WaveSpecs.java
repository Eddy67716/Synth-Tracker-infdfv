/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound.formats.wave;

import sound.formats.SampleData;

/**
 *
 * @author Edward Jenkins
 */
public abstract class WaveSpecs {
    
    // constant varialbs

    // header constants
    public static final String S_GROUP_ID_HEADER = "RIFF"; // RIFF
    public static final String S_RIFF_TYPE = "WAVE";       // WAVE
    
    // format chunk
    public static final String S_GROUP_ID_FORMAT = "fmt "; // FMT
    public static final long DW_FORMAT_CHUNK_SIZE = 16;
    public static final short W_FORMAT_TAG = 1;
    public static final int W_CHANNELS = 1;
    public static final long DW_SAMPLE_PER_SEC = 44100;
    public static final long DW_AVG_BYTE_PER_SEC = 88200;
    public static final int W_BLOCK_ALIGN = 1;
    public static final int DW_BITS_PER_SAMPLE = 16;
    public static final double[][] SAMPLE_DATA = {{0}, {0}};
    public static final int SUB_FORMAT_HEAD = 3;
    public static final String SUB_FORMAT_TAIL = "GUID";
    public static final boolean DEF_FLOATING = false;
    
    // fact chunk id
    public static final String S_GROUP_ID_FACT = "fact";    // fact

    // data chunk
    public static final String S_GROUP_ID_DATA = "data";   // DATA
    
    // instance variables
    // header
    private long dwFileLength;
    // format
    private long dwFormatChunkSize;
    private int wFormatTag;
    private int wChannels;
    private long dwSamplesPerSec;
    private long dwAvgBytesPerSec;
    private int wBlockAlign;
    private int wBitsPerSample;
    private int cbSize;
    private int wValidBitsPerSample;
    private long dwChannelMask;
    private int subFormatHead;
    private String subFormatTail;
    // fact
    private int dwFactChunkSize;
    private int dwSampleLength;
    // for floating point files
    private boolean floatingPoint;
    // data
    private long dwDataChunkSize;
    private SampleData sampleData;
    
    // all args constructor
    public WaveSpecs(int channels, long sampleRate, int bitsPerSample, 
            double[][] sampleData, boolean floating) {
        wChannels = channels;
        dwSamplesPerSec = sampleRate;
        wBitsPerSample = bitsPerSample;
        floatingPoint = floating;
        wBlockAlign = (int)(wChannels * (wBitsPerSample / 8));
        dwDataChunkSize = wBlockAlign * sampleData.length;
        dwAvgBytesPerSec = sampleRate * wBlockAlign;
        this.sampleData = new SampleData((byte)wBitsPerSample, 
                (wBitsPerSample != 8 && ! floatingPoint), floatingPoint,
                (byte)channels,
                ((int) getDWDataChunkSize() / (getWBitsPerSample() / 8)
                / getWChannels())
        );
        for (double[] sampleData1 : sampleData) {
            this.sampleData.inputPoints(sampleData1);
        }
    }
    
    // no args constructor
    public WaveSpecs() {
        this(W_CHANNELS, DW_SAMPLE_PER_SEC, DW_BITS_PER_SAMPLE, SAMPLE_DATA, 
                DEF_FLOATING);
    }
    
    // getters

    public long getDWFileLength() {
        return dwFileLength;
    }

    public long getDWFormatChunkSize() {
        return dwFormatChunkSize;
    }

    public int getWFormatTag() {
        return wFormatTag;
    }

    public int getWChannels() {
        return wChannels;
    }

    public long getDWSamplesPerSec() {
        return dwSamplesPerSec;
    }

    public long getDWAvgBytesPerSec() {
        return dwAvgBytesPerSec;
    }

    public int getWBlockAlign() {
        return wBlockAlign;
    }

    public int getWBitsPerSample() {
        return wBitsPerSample;
    }

    public int getCBSize() {
        return cbSize;
    }

    public int getWValidBitsPerSample() {
        return wValidBitsPerSample;
    }

    public long getDWChannelMask() {
        return dwChannelMask;
    }

    public int getSubFormatHead() {
        return subFormatHead;
    }

    public String getSubFormatTail() {
        return subFormatTail;
    }

    public int getDWFactChunkSize() {
        return dwFactChunkSize;
    }

    public int getDWSampleLength() {
        return dwSampleLength;
    }

    public boolean isFloatingPoint() {
        return floatingPoint;
    }

    public long getDWDataChunkSize() {
        return dwDataChunkSize;
    }

    public SampleData getSampleData() {
        return sampleData;
    }
    
    // setters
    public void setDWFileLength(long dwFileLength) {
        this.dwFileLength = dwFileLength;
    }

    public void setDWFormatChunkSize(long dwFormatChunkSize) {
        this.dwFormatChunkSize = dwFormatChunkSize;
    }

    public void setWFormatTag(int wFormatTag) {
        this.wFormatTag = wFormatTag;
    }

    public void setWChannels(int wChannels) {
        this.wChannels = wChannels;
    }

    public void setDWSamplesPerSec(long dwSamplesPerSec) {
        this.dwSamplesPerSec = dwSamplesPerSec;
    }

    public void setDWAvgBytesPerSec(long dwAvgBytesPerSec) {
        this.dwAvgBytesPerSec = dwAvgBytesPerSec;
    }

    public void setWBlockAlign(int wBlockAlign) {
        this.wBlockAlign = wBlockAlign;
    }

    public void setWBitsPerSample(int wBitsPerSample) {
        this.wBitsPerSample = wBitsPerSample;
    }

    public void setCBSize(int cbSize) {
        this.cbSize = cbSize;
    }

    public void setWValidBitsPerSample(int wValidBitsPerSample) {
        this.wValidBitsPerSample = wValidBitsPerSample;
    }

    public void setDWChannelMask(long dwChannelMask) {
        this.dwChannelMask = dwChannelMask;
    }

    public void setSubFormatHead(int subFormatHead) {
        this.subFormatHead = subFormatHead;
    }

    public void setSubFormatTail(String subFormatTail) {
        this.subFormatTail = subFormatTail;
    }

    public void setDWFactChunkSize(int dwFactChunkSize) {
        this.dwFactChunkSize = dwFactChunkSize;
    }

    public void setDWSampleLength(int dwSampleLength) {
        this.dwSampleLength = dwSampleLength;
    }

    public void setFloatingPoint(boolean floatingPoint) {
        this.floatingPoint = floatingPoint;
    }

    public void setDWDataChunkSize(long dwDataChunkSize) {
        this.dwDataChunkSize = dwDataChunkSize;
    }

    public void setSampleData(SampleData sampleData) {
        this.sampleData = sampleData;
    }
}
