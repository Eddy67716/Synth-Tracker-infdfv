/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.formats.riff.wave;

import static io.IOMethods.LITTLE_ENDIAN;
import io.Reader;
import io.Writer;
import java.io.FileNotFoundException;
import java.io.IOException;
import sound.formats.AudioSampleData;
import module.ISampleSynth;
import file.ISaveableFile;
import sound.formats.GenericEnvelopeSpec;
import static sound.formats.GenericEnvelopeSpec.EMPTY_ENVELOPE;

/**
 *
 * @author eddy6
 */
public class WaveFile implements ISaveableFile, ISampleSynth {

    // instance variables
    // file
    private String waveName;
    private WaveFormChunk waveHeader;

    // wave file output
    public WaveFile(int channels, long sampleRate, int bitsPerSample,
            double[][] sampleData, String wavOutput,
            boolean floatingPoint) {
        waveHeader = new WaveFormChunk(channels, sampleRate, bitsPerSample,
                sampleData, floatingPoint);
        this.waveName = wavOutput + ".wav";
    }

    public WaveFile(String wavOutput, long sampleRate,
            AudioSampleData sampleData) {
        waveHeader = new WaveFormChunk(sampleRate, sampleData);
        this.waveName = wavOutput + ".wav";
    }

    // wave file streaming
    public WaveFile(int channels, long sampleRate, int bitsPerSample,
            String wavOutput, boolean floatingPoint) {
        this(channels, sampleRate, bitsPerSample, null, wavOutput,
                floatingPoint);
    }

    // wave reading
    public WaveFile(String wavInput) {
        this.waveName = wavInput;
        waveHeader = new WaveFormChunk();
    }

    // getters
    public WaveFormChunk getWaveHeader() {
        return waveHeader;
    }

    @Override
    public boolean write() throws IOException {
        // initialise the Writer
        Writer w = new Writer(waveName, LITTLE_ENDIAN);

        // write header
        waveHeader.write(w);

        return true;
    }

    @Override
    public boolean read() throws IOException, FileNotFoundException, 
            IllegalArgumentException {

        // set up reader
        Reader r = new Reader(waveName, LITTLE_ENDIAN);

        // read header
        waveHeader.read(r);

        return true;
    }

    @Override
    public int length() {

        return waveHeader.length();
    }

    @Override
    public byte getTranspositionNote() {
        WaveInstrument waveInstrument = waveHeader.getWaveInstrument();

        if (waveInstrument != null) {
            byte transpsitionNote = waveInstrument.getbUnshiftedNote();

            return transpsitionNote;
        } else {
            return 0;
        }
    }

    @Override
    public byte getFineTuning() {
        WaveInstrument waveInstrument = waveHeader.getWaveInstrument();

        if (waveInstrument != null) {
            byte fineTuning = waveInstrument.getChFineTune();

            return fineTuning;
        } else {
            return 0;
        }
    }

    @Override
    public int getC5Speed() {
        return (int) waveHeader.getWaveFormat().getDWSamplesPerSec();
    }

    @Override
    public short getBitRate() {
        return (short) waveHeader.getWaveFormat().getWBitsPerSample();
    }

    @Override
    public boolean isSigned() {
        return waveHeader.getWaveFormat().getWBitsPerSample() != 8;
    }

    @Override
    public boolean isFloating() {
        return waveHeader.getWaveFormat().isFloatingPoint();
    }

    @Override
    public boolean isCompressed() {
        return false;
    }

    @Override
    public boolean isStereo() {
        return waveHeader.getWaveFormat().getWChannels() > 1;
    }

    @Override
    public int getSampleLength() {
        return waveHeader.getWaveData().getSampleData().getSampleCount();
    }

    @Override
    public boolean isLooped() {

        WaveSampleChunk waveSample = waveHeader.getWaveSample();

        if (waveSample != null) {
            return waveSample.getcSampleLoops() != 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean isSustainLooped() {
        WaveSampleChunk waveSample = waveHeader.getWaveSample();

        if (waveSample != null) {
            return waveSample.getcSampleLoops() > 1;
        } else {
            return false;
        }
    }

    @Override
    public boolean isPingPongLooped() {
        WaveSampleChunk waveSample = waveHeader.getWaveSample();

        if (waveSample != null && waveSample.getcSampleLoops() > 0) {
            SampleLoop sampleLoop = (waveSample.getcSampleLoops() > 1)
                    ? waveSample.getSampleLoops()[1]
                    : waveSample.getSampleLoops()[0];

            return (sampleLoop.getDwType() == SampleLoop.LOOP_PING_PONG);

        } else {
            return false;
        }
    }

    @Override
    public boolean isPingPongSustainLooped() {
        WaveSampleChunk waveSample = waveHeader.getWaveSample();

        if (waveSample != null && waveSample.getcSampleLoops() > 1) {
            SampleLoop sampleLoop = waveSample.getSampleLoops()[0];

            return (sampleLoop.getDwType() == SampleLoop.LOOP_PING_PONG);
        } else {
            return false;
        }
    }

    @Override
    public long getLoopBeginning() {
        WaveSampleChunk waveSample = waveHeader.getWaveSample();

        if (waveSample != null && waveSample.getcSampleLoops() > 0) {
            SampleLoop sampleLoop = (waveSample.getcSampleLoops() > 1)
                    ? waveSample.getSampleLoops()[1]
                    : waveSample.getSampleLoops()[0];

            return sampleLoop.getDwStart();

        } else {
            return 0;
        }
    }

    @Override
    public long getLoopEnd() {
        WaveSampleChunk waveSample = waveHeader.getWaveSample();

        if (waveSample != null && waveSample.getcSampleLoops() > 0) {
            SampleLoop sampleLoop = (waveSample.getcSampleLoops() > 1)
                    ? waveSample.getSampleLoops()[1]
                    : waveSample.getSampleLoops()[0];

            return sampleLoop.getDwEnd();

        } else {
            return getSampleLength();
        }
    }

    @Override
    public long getSustainLoopBeginning() {
        WaveSampleChunk waveSample = waveHeader.getWaveSample();

        if (waveSample != null && waveSample.getcSampleLoops() > 1) {
            SampleLoop sampleLoop = waveSample.getSampleLoops()[0];

            return sampleLoop.getDwStart();
        } else {
            return 0;
        }
    }

    @Override
    public long getSustainLoopEnd() {
        WaveSampleChunk waveSample = waveHeader.getWaveSample();

        if (waveSample != null && waveSample.getcSampleLoops() > 1) {
            SampleLoop sampleLoop = waveSample.getSampleLoops()[0];

            return sampleLoop.getDwStart();
        } else {
            return getSampleLength();
        }
    }

    @Override
    public AudioSampleData getAudioSampleData() {
        return waveHeader.getWaveData().getSampleData();
    }

    @Override
    public void setC5Speed(int c5Speed) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setBitRate(short bitRate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setSigned(boolean signed) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setStereo(boolean stereo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setLooped(boolean looped) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setSustainLooped(boolean sustainLooped) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setPingPongLooped(boolean pingPongLooped) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setPingPongSustainLooped(boolean pingPongSustainLooped) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setLoopBeginning(long loopBeginning) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setLoopEnd(long loopEnd) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setSustainLoopBeginning(long sustainLoopBeginning) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setSustainLoopEnd(long sustainLoopEnd) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setAudioSampleData(AudioSampleData data) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getSampleName() {
        return "";
    }

    @Override
    public String getDosFileName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*@Override
    public SynthCache getSampleCache() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }*/

    @Override
    public double getNoralisedDefaultVolume() {
        WaveOMPTExtra omptExtras = waveHeader.getWaveOMPTExtra();

        if (omptExtras != null) {
            return (double) omptExtras.getDefaultVolume() / 64;
        } else {
            return 1;
        }
    }

    @Override
    public double getNormalisedGlobalVolume() {
        WaveOMPTExtra omptExtras = waveHeader.getWaveOMPTExtra();

        if (omptExtras != null) {
            return (double) omptExtras.getGlobalVolume() / 64;
        } else {
            return 1;
        }
    }

    @Override
    public double getNormalisedPanValue() {
        WaveOMPTExtra omptExtras = waveHeader.getWaveOMPTExtra();

        if (omptExtras != null) {
            return ((double) omptExtras.getPanning() - 32) / 32;
        } else {
            return 0;
        }
    }

    @Override
    public boolean isPanning() {
        WaveOMPTExtra omptExtras = waveHeader.getWaveOMPTExtra();

        if (omptExtras != null) {
            return omptExtras.isPanned();
        } else {
            return false;
        }
    }

    @Override
    public double getFullVibratoSpeed() {
        WaveOMPTExtra omptExtras = waveHeader.getWaveOMPTExtra();

        if (omptExtras != null) {
            return omptExtras.getVibratoRate();
        } else {
            return 0;
        }
    }

    @Override
    public double getFullVibratoDepth() {
        WaveOMPTExtra omptExtras = waveHeader.getWaveOMPTExtra();

        if (omptExtras != null) {
            return (double) omptExtras.getVibratoDepth();
        } else {
            return 0;
        }
    }

    @Override
    public byte getVibratoWaveform() {
        WaveOMPTExtra omptExtras = waveHeader.getWaveOMPTExtra();

        if (omptExtras != null) {
            return omptExtras.getVibratoWave();
        } else {
            return 0;
        }
    }

    @Override
    public double getVibratoDelay() {
        WaveOMPTExtra omptExtras = waveHeader.getWaveOMPTExtra();

        if (omptExtras != null) {
            return omptExtras.getVibratoSweep();
        } else {
            return 0;
        }
    }

    @Override
    public byte getStartingNote() {
        WaveInstrument waveInstrument = waveHeader.getWaveInstrument();

        if (waveInstrument != null) {
            byte startingNote = waveInstrument.getbLowNote();

            return startingNote;
        } else {
            return 0;
        }
    }

    @Override
    public byte getEndingNote() {
        WaveInstrument waveInstrument = waveHeader.getWaveInstrument();

        if (waveInstrument != null) {
            byte startingNote = waveInstrument.getbHighNote();

            return startingNote;
        } else {
            return 0;
        }
    }

    @Override
    public byte getStartingVelocity() {
        WaveInstrument waveInstrument = waveHeader.getWaveInstrument();

        if (waveInstrument != null) {
            byte startingNote = waveInstrument.getbLowVelocity();

            return startingNote;
        } else {
            return 0;
        }
    }

    @Override
    public byte getEndingVelocity() {
        WaveInstrument waveInstrument = waveHeader.getWaveInstrument();

        if (waveInstrument != null) {
            byte startingNote = waveInstrument.getbHighVelocity();

            return startingNote;
        } else {
            return 0;
        }
    }

    @Override
    public void setSampleName(String sampleName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setDosFileName(String dosFileName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setNormalisedDefaultVolume(double defaultVolume) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setNormalisedGlobalVolume(double globalVolume) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setNormalisedPanValue(double panValue) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setPanning(boolean panning) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setVibratoSpeed(double vibratoSpeed) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setFullVibratoDepth(double vibratoDepth) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setVibratoWaveform(byte vibratoWaveform) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setVibratoDelay(double vibratoDelay) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public GenericEnvelopeSpec getGenericEnvelopeSpec() {
        return EMPTY_ENVELOPE;
    }

    @Override
    public void setDefaultVolume(int defaultVolume) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setGlobalVolume(int globalVolume) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setPanValue(int panValue) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getDefaultVolume() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getGlobalVolume() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getPanValue() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
