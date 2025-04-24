/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.formats.riff.wave;

import sound.formats.riff.RiffChunk;
import io.IReadable;
import io.IWritable;
import java.io.FileNotFoundException;
import java.io.IOException;
import sound.formats.AudioSampleData;

/**
 *
 * @author eddy6
 */
public class WaveFormatChunk extends RiffChunk {

    // constants
    public static final String S_GROUP_ID = "fmt "; // FMT
    public static final short PCM_W_FORMAT_TAG = 1;
    public static final short FLOAT_W_FORMAT_TAG = 3;

    // format
    private int wFormatTag;
    private int wChannels;
    private long dwSamplesPerSec;
    private long dwAvgBytesPerSec;
    private int wBlockAlign;
    private int wBitsPerSample;
    private boolean floatingPoint;
    private int cbSize;
    private WaveFactChunk factChunk;
    private int wValidBitsPerSample;
    private long dwChannelMask;
    private int subFormatHead;
    private String subFormatTail;

    // all args constructor
    public WaveFormatChunk(int channels, long sampleRate, int bitsPerSample,
            boolean floatingPoint, double[][] sampleData) {
        this();
        wChannels = channels;
        dwSamplesPerSec = sampleRate;
        wBitsPerSample = bitsPerSample;
        wBlockAlign = (int) (wChannels * (wBitsPerSample / 8));
        dwAvgBytesPerSec = dwSamplesPerSec * wBlockAlign;
        this.floatingPoint = floatingPoint;
        if (floatingPoint) {
            wFormatTag = FLOAT_W_FORMAT_TAG;
            setChunkSize(18);
            cbSize = 0;
            this.factChunk = new WaveFactChunk(4, wChannels
                    * sampleData[0].length);
        } else {
            wFormatTag = PCM_W_FORMAT_TAG;
            setChunkSize(16);
        }
    }
    
    public WaveFormatChunk(long sampleRate, AudioSampleData sampleData) {
        this();
        wChannels = sampleData.getChannels();
        dwSamplesPerSec = sampleRate;
        wBitsPerSample = sampleData.getBitrate();
        wBlockAlign = (int) (wChannels * (wBitsPerSample / 8));
        dwAvgBytesPerSec = dwSamplesPerSec * wBlockAlign;
        floatingPoint = sampleData.isFloating();
        if (floatingPoint) {
            wFormatTag = FLOAT_W_FORMAT_TAG;
            setChunkSize(18);
            cbSize = 0;
            this.factChunk = new WaveFactChunk(4, wChannels
                    * sampleData.getSampleCount());
        } else {
            wFormatTag = PCM_W_FORMAT_TAG;
            setChunkSize(16);
        }
    }

    // no args constructor
    public WaveFormatChunk() {
        super(S_GROUP_ID);
    }

    // getters
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

    public boolean isFloatingPoint() {
        return floatingPoint;
    }

    public int getCbSize() {
        return cbSize;
    }

    public WaveFactChunk getFactChunk() {
        return factChunk;
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
    
    // setters
    public void setwChannels(int wChannels) {
        this.wChannels = wChannels;
    }

    public void setDwSamplesPerSec(long dwSamplesPerSec) {
        this.dwSamplesPerSec = dwSamplesPerSec;
    }

    public void setwBitsPerSample(int wBitsPerSample) {
        this.wBitsPerSample = wBitsPerSample;
    }

    @Override
    public boolean write(IWritable w) throws IOException {

        // write super details
        boolean written = super.write(w);

        // write sub details
        // wFormatTag
        w.writeShort((short) wFormatTag);
        // wChannels
        w.writeShort((short) wChannels);
        // dwSamplesPerSec
        w.writeInt((int) dwSamplesPerSec);
        // dwAvgBytesPerSec
        w.writeInt((int) dwAvgBytesPerSec);
        // wBlockAlign
        w.writeShort((short) wBlockAlign);
        // wBitsPerSample
        w.writeShort((short) wBitsPerSample);

        if (floatingPoint) {
            // cbSize
            w.writeShort((short) cbSize);
            // fact chunk
            factChunk.write(w);
        }

        return written;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {
        boolean read = super.read(r);
        
        // wFormatTag
        wFormatTag = r.getUShortAsInt();
        
        // check if it is float data
        if (getWFormatTag() == 3) {
            floatingPoint = true;
        }
        
        // wChannels
        wChannels = r.getUShortAsInt();
        
        // dwSamplesPerSec
        dwSamplesPerSec = r.getUIntAsLong();
        
        // dwAvgBytesPerSec
        dwAvgBytesPerSec = r.getUIntAsLong();
        
        // wBlockAlign
        wBlockAlign = r.getUShortAsInt();
        
        // wBitsPerSample
        wBitsPerSample = r.getUShortAsInt();

        // extension chunk
        switch ((int) getChunkSize()) {
            case 18:
                // cbSize
                cbSize = r.getUShortAsInt();
                break;
            default:
                break;
        }
        
        setFullyRead(read);

        return read;
    }

}
