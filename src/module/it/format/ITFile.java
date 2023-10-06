/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.it.format;

import io.IReadable;
import io.IWritable;
import io.RandomReaderWriter;
import io.Reader;
import io.Writer;
import module.IInstrument;
import module.IModuleFile;
import module.IPattern;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import module.IAudioSample;

/**
 *
 * @author Edward Jenkins
 */
public class ITFile implements IModuleFile {

    // constants
    public static final int IT_MOD_TYPE_ID = 4;

    // instance variables
    // file name
    private String file;
    // file header
    private ITHeader header;
    // instruments
    private List<ITInstrument> instruments;
    // sample headers
    private List<ITSampleHeader> sampleHeaders;
    // patterns
    private List<ITPattern> patterns;
    // stores the start of instrument headers
    private long[] offsetOfInstruments;
    // stores the start of sample headers
    private long[] offsetOfSamples;
    // stores the start of patterns
    private long[] offsetOfPatterns;
    // total number of channels in file
    private byte totalNumberOfChannels;

    // constructor
    public ITFile(String file) {
        this.file = file;
    }

    @Override
    public String getFileName() {
        File testFile = new File(file);
        return testFile.getName();
    }

    // get header
    public ITHeader getHeader() {
        return this.header;
    }

    @Override
    public int getModTypeID() {
        return IT_MOD_TYPE_ID;
    }

    // get instruments
    public List<ITInstrument> getInstruments() {
        return this.instruments;
    }

    // get samples
    public List<ITSampleHeader> getSamples() {
        return this.sampleHeaders;
    }

    // get patterns
    public List<ITPattern> getPatterns() {
        return this.patterns;
    }

    // get channel count
    public byte getTotalNumberOfChannels() {
        return this.totalNumberOfChannels;
    }

    @Override
    public boolean read() throws IOException, IllegalArgumentException {

        // IT format is in Little Endian
        IReadable reader
                = new Reader(file, true);

        boolean read;

        try {

            // read header
            header = new ITHeader();

            read = header.read(reader);

            // read instruments
            // number of instruments
            int instrumentNumber = header.getInstrumentNum();

            // instruments
            instruments = new ArrayList<>();

            // get instrument offsets
            offsetOfInstruments = header.getOffsetOfInstruments();

            // get instrument format
            boolean oldFormat;

            int compatibleWithTracker = header.getCompatibleWithTracker();

            oldFormat = compatibleWithTracker < 0x200;

            // read instrument and envelopes
            // iterate through instruments
            for (int i = 0; i < instrumentNumber; i++) {

                // initialise instrument
                instruments.add(new ITInstrument(offsetOfInstruments[i],
                        oldFormat));

                // read per instruments
                read = instruments.get(i).read(reader);
            }

            // read samples
            // number of samples
            int sampleNumber = header.getSampleNum();

            // samples
            sampleHeaders = new ArrayList<>();

            // get sample offsets
            offsetOfSamples = header.getOffsetOfSampleHeaders();

            // interate through samples
            for (int i = 0; i < sampleNumber; i++) {

                // initialise sample
                sampleHeaders.add(new ITSampleHeader(offsetOfSamples[i]));

                // read per samples
                read = sampleHeaders.get(i).read(reader);
            }

            // read patterns
            // nunber of chnannels
            byte numberOfChannels;

            // number of patterns
            int patterneNumber = header.getPatternNum();

            // patterns
            patterns = new ArrayList<>();

            // get pattern offsets
            offsetOfPatterns = header.getOffsetOfPatterns();

            // iterate through patterns
            for (int i = 0; i < patterneNumber; i++) {

                // initialise pattern
                patterns.add(new ITPattern(offsetOfPatterns[i]));

                String patternName = "";

                // set patern name
                if (header.getPatternNames() != null) {
                    if (!header.getPatternNames().getPatternNameAtChannel(i)
                            .equals("empty")) {
                        patternName = header.getPatternNames()
                                .getPatternNameAtChannel(i);
                    }
                }

                patterns.get(i).setName(patternName);

                // read per pattern
                read = patterns.get(i).read(reader);

                numberOfChannels = patterns.get(i).getNumberOfChannels();

                if (numberOfChannels > totalNumberOfChannels) {
                    totalNumberOfChannels = numberOfChannels;
                }
            }

        } catch (IOException | IllegalArgumentException e) {
            throw e;
        }

        return read;
    }

    @Override
    public boolean write() throws IOException {

        // update all counts and set offsets
        updateFileDetails();
        
        // set up the file writer
        IWritable writer = new Writer(file, true);

        // write header
        header.write(writer);
        
        // write instruments
        for (ITInstrument instrument: instruments) {
            instrument.write(writer);
        }
        
        // write sample headers
        for (ITSampleHeader sampleHeader : sampleHeaders) {
            sampleHeader.writeHeader(writer);
        }
        
        // write patterns
        for (ITPattern pattern : patterns) {
            pattern.write(writer);
        }
        
        // write sample data
        for (ITSampleHeader sampleHeader : sampleHeaders) {
            sampleHeader.getSampleData().write(writer);
        }
        
        return true;
    }

    // get instruments
    @Override
    public List<IInstrument> getIInstruments() {

        List<IInstrument> returnInstruments
                = new ArrayList(this.instruments.size());

        for (int i = 0; i < instruments.size(); i++) {
            returnInstruments.add(instruments.get(i));
        }

        return returnInstruments;
    }

    // get samples
    @Override
    public List<IAudioSample> getISamples() {
        List<IAudioSample> returnSamples
                = new ArrayList(sampleHeaders.size());

        for (int i = 0; i < sampleHeaders.size(); i++) {
            returnSamples.add(sampleHeaders.get(i));
        }

        return returnSamples;
    }

    // get patterns
    @Override
    public List<IPattern> getIPatterns() {
        List<IPattern> returnPatterns
                = new ArrayList(patterns.size());

        for (int i = 0; i < patterns.size(); i++) {
            returnPatterns.add(patterns.get(i));
        }

        return returnPatterns;
    }

    @Override
    public void play() {

    }

    @Override
    public int length() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public byte getChannelsCount() {
        return totalNumberOfChannels;
    }

    public void updateFileDetails() {

        // update header edit history
        header.buildNewEditHistory();

        // set offsets for everything
        int offset = header.length();

        // instruments
        for (int i = 0; i < instruments.size(); i++) {
            header.getOffsetOfInstruments()[i] = offset;

            offset += instruments.get(i).length();
        }

        // sample headers
        for (int i = 0; i < sampleHeaders.size(); i++) {
            header.getOffsetOfSampleHeaders()[i] = offset;

            offset += sampleHeaders.get(i).length();
        }

        // patterns
        for (int i = 0; i < patterns.size(); i++) {
            header.getOffsetOfPatterns()[i] = offset;

            offset += patterns.get(i).length();
        }

        // sample data
        for (int i = 0; i < sampleHeaders.size(); i++) {
            ITSampleHeader sampleHeader = sampleHeaders.get(i);

            sampleHeader.setSamplePointer(offset);

            offset += sampleHeader.getSampleData().length();
        }
    }
}
