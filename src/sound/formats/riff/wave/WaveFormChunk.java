/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.formats.riff.wave;

import sound.formats.riff.RiffChunk;
import io.IReadable;
import io.IWritable;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import sound.formats.AudioSampleData;
import sound.formats.riff.InfoList;
import static sound.formats.riff.RiffChunk.S_GROUP_ID_LENGTH;
import sound.formats.riff.RiffForm;
import sound.formats.riff.RiffList;
import sound.formats.riff.UnknownList;
import sound.formats.riff.UnknownRiffChunk;

/**
 *
 * @author eddy6
 */
public class WaveFormChunk extends RiffForm {
    
    // constants
    public static final String S_GROUP_ID = "RIFF"; // RIFF
    public static final String S_RIFF_TYPE = "WAVE"; // WAVE
    
    // instance variables
    private WaveFormatChunk waveFormat;
    private WaveDataChunk waveData;
    private RiffChunk[] otherChunks;

    public WaveFormChunk(int channels, long sampleRate, int bitsPerSample,
            double[][] sampleData, boolean floatingPoint) {
        super(S_GROUP_ID, S_RIFF_TYPE);
        waveFormat = new WaveFormatChunk(channels, sampleRate, bitsPerSample,
                floatingPoint, sampleData);
        waveData = new WaveDataChunk(waveFormat, sampleData);
        
        if (floatingPoint) {
            setChunkSize(waveFormat.getChunkSize() + waveData
                    .getChunkSize() + waveFormat.getFactChunk().getChunkSize()
                    + 34);
        } else {
            setChunkSize(waveFormat.getChunkSize() + waveData
                    .getChunkSize() + 20);
        }
    }
    
    public WaveFormChunk(long sampleRate, AudioSampleData sampleData) {
        super(S_GROUP_ID, S_RIFF_TYPE);
        waveFormat = new WaveFormatChunk(sampleRate, sampleData);
        waveData = new WaveDataChunk(waveFormat, sampleData);
        if (sampleData.isFloating()) {
            setChunkSize(waveFormat.getChunkSize() + waveData
                    .getChunkSize() + waveFormat.getFactChunk().getChunkSize()
                    + 34);
        } else {
            setChunkSize(waveFormat.getChunkSize() + waveData
                    .getChunkSize() + 20);
        }
    }
    
    public WaveFormChunk() {
        super(S_GROUP_ID, S_RIFF_TYPE);
        waveFormat = new WaveFormatChunk();
    }
    
    public WaveFormatChunk getWaveFormat() {
        return waveFormat;
    }

    public WaveDataChunk getWaveData() {
        return waveData;
    }

    public WaveSampleChunk getWaveSample() {
        WaveSampleChunk waveSample = null;

        if (otherChunks != null) {
            for (RiffChunk chunk : this.otherChunks) {
                if (chunk instanceof WaveSampleChunk sampleChunk) {
                    waveSample = sampleChunk;
                    break;
                }
            }
        }

        return waveSample;
    }

    public WaveInstrument getWaveInstrument() {
        WaveInstrument waveInstrument = null;

        if (otherChunks != null) {
            for (RiffChunk chunk : this.otherChunks) {
                if (chunk instanceof WaveInstrument instrumentChunk) {
                    waveInstrument = instrumentChunk;
                    break;
                }
            }
        }

        return waveInstrument;
    }

    public WaveOMPTExtra getWaveOMPTExtra() {
        WaveOMPTExtra waveOmptExtra = null;

        if (otherChunks != null) {
            for (RiffChunk chunk : this.otherChunks) {
                if (chunk instanceof WaveOMPTExtra extraChunk) {
                    waveOmptExtra = extraChunk;
                    break;
                }
            }
        }

        return waveOmptExtra;
    }

    public void addChunk(RiffChunk chunk) {

        if (otherChunks == null) {
            otherChunks = new RiffChunk[1];
            otherChunks[0] = chunk;
        } else {
            RiffChunk[] newChunks = new RiffChunk[otherChunks.length + 1];
            System.arraycopy(otherChunks, 0, newChunks, 0, otherChunks.length);
            newChunks[newChunks.length - 1] = chunk;
            otherChunks = newChunks;
        }
    }
    
    @Override
    public boolean write(IWritable w) throws IOException {
        
        // write super data
        boolean written = super.write(w);
        
        // write format
        waveFormat.write(w);

        // write data
        waveData.write(w);

        // write any other chunks
        if (otherChunks != null) {
            for (RiffChunk waveChunk : otherChunks) {
                waveChunk.write(w);
            }
        }

        w.close();

        return written;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {
        
        // read super data
        boolean read = super.read(r);
        
        // read format
        waveFormat.read(r);

        // setup data
        waveData = new WaveDataChunk(waveFormat);

        // read data
        waveData.read(r);

        // prepair to read other chunks
        ArrayList<RiffChunk> chunkList = new ArrayList<>();

        // read any other chunks
        try {
            while (r.available() > 0) {

                String byteCheck = r.getByteString(4);

                RiffChunk chunk;

                switch (byteCheck) {
                    default:
                        chunk = new UnknownRiffChunk(byteCheck);
                        break;
                    case RiffList.S_GROUP_ID:
                        long listLength = r.getUIntAsLong();
                        String listIdString
                                = r.getByteString(S_GROUP_ID_LENGTH);
                        if (listIdString.equals(InfoList.LIST_ID)) {
                            chunk = new InfoList(listLength);
                        } else {
                            chunk = new UnknownList(listIdString, listLength);
                        }
                        break;
                    case WaveSampleChunk.S_GROUP_ID:
                        chunk = new WaveSampleChunk();
                        break;
                    case WaveInstrument.S_GROUP_ID:
                        chunk = new WaveInstrument();
                        break;
                    case WaveOMPTExtra.S_GROUP_ID:
                        chunk = new WaveOMPTExtra();
                        break;
                }

                chunk.setRiffIdRead(true);

                chunk.read(r);

                chunkList.add(chunk);
            }

        } catch (EOFException e) {
            System.out.println("File ended");
        }

        if (!chunkList.isEmpty()) {
            otherChunks = chunkList.toArray(RiffChunk[]::new);
        }
        
        return read;
    }

    @Override
    public int length() {
        
        int length = super.length() + waveFormat.length()
                + waveData.length();

        if (otherChunks != null & otherChunks.length > 0) {
            for (RiffChunk chunk : otherChunks) {
                length += chunk.length();
            }
        }
        
        return length;
    }
}
