/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.models;

import module.it.format.ITFile;
import module.IInstrument;
import module.IModuleFile;
import module.IPattern;
import module.it.format.ITSampleHeader;
import sound.formats.wave.WaveFile;
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
import module.IAudioSample;

/**
 *
 * @author Edward Jenkins
 */
public class LoadViewModel {

    // constnats
    public static final String[] MOD_EXTENSIONS = {
        "mod", "s3m", "xm", "it", "str"
    };

    public static final String[] SAMPLE_EXTENSIONS = {
        "wav", "its"
    };

    // instance variables
    JFileChooser fileChooser;
    JDialog errorDialog;
    List<File> openedFiles;
    FileNameExtensionFilter modFilter;
    FileNameExtensionFilter sampleFiles;
    int readFileType; // 1 is mod, 2 is s3m, 3 is xm, 4 is it, 5 is MPTM (not sure about implementing) and 6 is str
    String fileExtention;
    IModuleFile modFile;

    // construcotr
    public LoadViewModel() {
        modFilter
                = new FileNameExtensionFilter("Tracker music", MOD_EXTENSIONS);
        sampleFiles
                = new FileNameExtensionFilter("Samples", SAMPLE_EXTENSIONS);
        openedFiles = new ArrayList<File>();
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
        openedFiles.add(fileChooser.getSelectedFile());
        modFile = new ITFile(fileChooser.getSelectedFile().getPath());
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

    public IAudioSample readSampleFile(String path) {

        int lastIndexOfPeroid = path.lastIndexOf(".");
        IAudioSample sample = null;
        String fileExtension = path.substring(lastIndexOfPeroid + 1);

        for (String sampleExtension : SAMPLE_EXTENSIONS) {

            if (fileExtension.equals(sampleExtension)) {

                switch (fileExtension) {
                    case "wav":
                        WaveFile waveFile = new WaveFile(path);
                        sample = new ITSampleHeader((IAudioSample) waveFile);
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

    public List<IAudioSample> getSamples() {

        return modFile.getISamples();
    }

    public List<IInstrument> getInstruments() {

        return modFile.getIInstruments();
    }

    public List<IPattern> getPatterns() {

        return modFile.getIPatterns();
    }

    public IModuleFile getModuleFile() {
        return modFile;
    }
}
