/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.models;

import module.it.format.ItFile;
import module.IInstrument;
import module.IModuleFile;
import module.IPattern;
import module.it.format.ItSampleHeader;
import ui.view.ProgressionBar;
import ui.workers.LoadModuleSwingWorker;
import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import module.IModuleHeader;
import sound.formats.riff.wave.WaveFile;
import module.ISampleSynth;

/**
 *
 * @author Edward Jenkins
 */
public class LoadViewModel {

    // constnats
    public static final String[] MOD_EXTENSIONS = {
        "mod", "s3m", "xm", "it", "mptm", "str"
    };

    public static final String[] SAMPLE_EXTENSIONS = {
        "wav", "its", "stss", "stcs", "stas", "stos", "sopl", "stfm"
    };

    // instance variables
    private JFileChooser fileChooser;
    private JDialog errorDialog;
    private List<File> openedFiles;
    private FileNameExtensionFilter modFilter;
    private FileNameExtensionFilter sampleFiles;
    // 1 is mod, 2 is s3m, 3 is xm, 4 is it, 5 is MPTM (not sure about implementing) and 6 is str
    private int modID; 
    private String fileExtention;
    private IModuleFile modFile;

    // construcotr
    public LoadViewModel() {
        modFilter
                = new FileNameExtensionFilter("Tracker music", MOD_EXTENSIONS);
        sampleFiles
                = new FileNameExtensionFilter("Samples", SAMPLE_EXTENSIONS);
        openedFiles = new ArrayList<>();
    }

    public String readModuleFile() throws Exception {

        // methods variables
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        int choice;
        LoadModuleSwingWorker loader;

        // initiate fileChooser
        fileChooser = new JFileChooser("src/");
        fileChooser.setSize(500, 400);
        fileChooser.setFileFilter(modFilter);

        // select a file
        choice = fileChooser.showOpenDialog(fileChooser);
        if (fileChooser.getSelectedFile() == null) {
            return "";
        }
        
        File modFileToOpen = fileChooser.getSelectedFile();
        
        String fileName = modFileToOpen.getName();
        
        int dotIndex = fileName.lastIndexOf(".");
        
        if (dotIndex == -1) {
            throw new IllegalArgumentException("Could not find file type");
        }
        
        String fileExtensionType = fileName.substring(dotIndex + 1);
        
        switch (fileExtensionType) {
            case "it":
                openedFiles.add(fileChooser.getSelectedFile());
                this.
                modFile = new ItFile(fileChooser.getSelectedFile().getPath());
                break;
            default:
                throw new IllegalArgumentException("Invalid file type");
        }
        
        
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        try {

            // progression bar
            ProgressionBar progressFrame = new ProgressionBar("Loading IT file...");

            loader = new LoadModuleSwingWorker(modFile, progressFrame);

            loader.execute();

            loader.get();

            return modFile.getFileName();
        } catch (Exception e) {
            throw e;
        }
    }

    public ISampleSynth readSampleFile(String path) {

        int lastIndexOfPeroid = path.lastIndexOf(".");
        ISampleSynth sample = null;
        String fileExtension = path.substring(lastIndexOfPeroid + 1);

        for (String sampleExtension : SAMPLE_EXTENSIONS) {

            if (fileExtension.equals(sampleExtension)) {

                switch (fileExtension) {
                    case "wav":
                        WaveFile waveFile = new WaveFile(path);
                        sample = new ItSampleHeader((ISampleSynth) waveFile);
                        break;
                    case "its":
                        break;
                }
            }
        }

        return sample;
    }

    public void readInstrumentFile() {

    }

    public void readPatternFile() {

    }
    
    public int getModID() {
        return modFile.getModTypeID();
    }
    
    public int getChannels() {
        return modFile.getChannelsCount();
    }
    
    public IModuleHeader getHeader() {
        return modFile.getIModuleheader();
    }

    public List<ISampleSynth> getSamples() {

        return modFile.getISamples();
    }

    public List<IInstrument> getInstruments() {

        return modFile.getIInstruments();
    }
    
    public short[] getPatternOrder() {
        return modFile.getPatternOrder();
    }

    public List<IPattern> getPatterns() {

        return modFile.getIPatterns();
    }

    public IModuleFile getModuleFile() {
        return modFile;
    }
}
