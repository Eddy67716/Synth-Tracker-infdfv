/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.it.format;

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
    private String file;                        // file name
    private ITHeader header;                    // file header
    private List<ITInstrument> instruments;     // instruments
    private List<ITSampleHeader> sampleHeaders; // sample headers
    private List<ITPattern> patterns;           // pattenrs
    private long[] offsetOfInstruments;         // stores the start of instrument headers
    private long[] offsetOfSamples;             // stores the start of sample headers
    private long[] offsetOfPatterns;            // stores the start of patterns
    private byte totalNumberOfChannels;         // total number of channels in file

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

        // read header
        boolean headerRead = readHeader();

        // read instruments
        boolean instrumentsRead = readInstruments();

        // read samples
        boolean samplesRead = readSamples();

        // read patterms
        boolean patternsRead = readPatterns();

        return (headerRead && instrumentsRead && samplesRead && patternsRead);

    }

    @Override
    public boolean write() throws IOException {
        return false;
    }

    public boolean readHeader() throws IOException, IllegalArgumentException {

        // boolean headerSuccess
        boolean headerSuccess;

        // boolean value for read
        boolean read = false;

        try {

            header = new ITHeader(file);

            read = header.read();

        } catch (IOException e) {
            throw e;
        }

        headerSuccess = read;

        return headerSuccess;
    }

    // read Instruments
    public boolean readInstruments() throws IOException, 
            IllegalArgumentException {

        // instrument success
        boolean instrumentSuccess = true;

        // read
        boolean read = false;

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
        try {

            // iterate through instruments
            for (int i = 0; i < instrumentNumber; i++) {

                // initialise instrument
                instruments.add(new ITInstrument(file,
                        offsetOfInstruments[i], oldFormat));

                // read per instruments
                read = instruments.get(i).read();
            }

        } catch (IOException e) {
            throw e;
        }

        return instrumentSuccess;
    }

    // read Samples
    public boolean readSamples() throws IOException, IllegalArgumentException {

        // sample success
        boolean sampleSuccess = true;

        // read
        boolean read = false;

        // number of samples
        int sampleNumber = header.getSampleNum();

        // samples
        sampleHeaders = new ArrayList<>();

        // get sample offsets
        offsetOfSamples = header.getOffsetOfSampleHeaders();

        // read samples
        try {

            // iterate through samples
            for (int i = 0; i < sampleNumber; i++) {

                // initialise sample
                sampleHeaders.add(new ITSampleHeader(file,
                        offsetOfSamples[i]));

                // read per samples
                read = sampleHeaders.get(i).read();
            }

        } catch (Exception e) {
            throw e;
        }

        return sampleSuccess;
    }

    // read Patterns
    public boolean readPatterns() throws IOException {

        // pattern success
        boolean pattenSuccess = true;

        // nunber of chnannels
        byte numberOfChannels;

        // read
        boolean read = false;

        // number of patterns
        int patterneNumber = header.getPatternNum();

        // patterns
        patterns = new ArrayList<>();

        // get pattern offsets
        offsetOfPatterns = header.getOffsetOfPatterns();

        // read patterns
        try {

            // iterate through samples
            for (int i = 0; i < patterneNumber; i++) {

                // initialise pattern
                patterns.add(new ITPattern(file,
                        offsetOfPatterns[i]));

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
                read = patterns.get(i).read();

                numberOfChannels = patterns.get(i).getNumberOfChannels();

                if (numberOfChannels > totalNumberOfChannels) {
                    totalNumberOfChannels = numberOfChannels;
                }
            }

        } catch (IOException e) {
            throw e;
        }

        return pattenSuccess;
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
}
