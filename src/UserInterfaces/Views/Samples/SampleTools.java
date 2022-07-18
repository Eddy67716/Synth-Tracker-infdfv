/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces.Views.Samples;

import UserInterfaces.Controllers.SampleController;
import UserInterfaces.UIProperties;
import static UserInterfaces.UIProperties.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import Module.IAudioSample;

/**
 *
 * @author Edward Jenkins
 */
public class SampleTools extends JPanel {

    // constants
    private static final Dimension VALUE_SPINNER_SIZE = new Dimension(45, 20);
    private static final Dimension DETAILS_FIELD_SIZE = new Dimension(208, 20);
    private static final Dimension SMALLER_FIELD_SIZE = new Dimension(100, 20);
    private static final Dimension C5_SPINNER_SIZE = new Dimension(80, 20);
    private static final Dimension FTUNE_SPINNER_SIZE = new Dimension(85, 20);
    private static final Dimension NOTE_COMBO_BOX_SIZE = new Dimension(80, 25);
    private static final String[] SAMPLE_NOTES = {"C_", "C#", "D_",
        "D#", "E_", "F_", "F#", "G_", "G#", "A_", "A#", "B_"};
    private static final String[] TRANSPOSITION_OPTIONS = {
        "MOD-IT 8272 & 8363Hz",
        "Synth tracker 1 44.1kHz",
        "Synth tracker 2 48kHz",
        "Custom Sample rate"
    };
    private static final String[] RESAMPLER_OPTIONS = {"Stair-Step", "Sinc LPF"};
    private static final String[] LOOP_OPTIONS
            = {"Off", "Forward", "Ping-Pong"};
    private static final String[] VIBRATO_WAVEFORMS
            = {"Sine", "Square", "Sawtooth", "Random"};

    // instance variables
    private int modType;
    // this panel
    private GridBagLayout toolsLayout;
    private GridBagConstraints tc;
    // basics
    private IAudioSample sample;
    // Sample details (File name, bitrate, channels, etc.)
    private JPanel sampleDetails;
    private GridBagLayout sampleDetailsLayout;
    private GridBagConstraints sdc;
    private Border sampleDetailsBorder;
    private JLabel sampleNameLabel;
    private JTextField sampleNameField;         // all
    private JLabel fileNameLabel;
    private JTextField fileNameField;           // s3m, it and str
    private JLabel sampleFormatTitleLabel;
    private JLabel sampleFormatLabel;           // all
    private JLabel sampleChannelTitleLabel;
    private boolean stereo;                     // all
    private JLabel sampleChannelLabel;
    private JLabel sampleLengthTitleLabel;
    private JLabel sampleLengthLabel;           // all
    // sound options (volume, panning, etc.)
    private JPanel soundOptions;
    private GridBagLayout soundOptionsLayout;
    private GridBagConstraints soc;
    private Border soundOptionsBorder;
    private JLabel defaultVolumeLabel;
    private JSpinner defaultVolumeValue;            // all
    private SpinnerModel defVolumeSpinnerModel;
    private JSlider defaultVolumeSlider;
    private JLabel globalVolumeLabel;
    private JSpinner globalVolumeValue;             // s3m, it and str
    private SpinnerModel globalVolumeSpinnerModel;
    private JSlider globalVolumeSlider;
    private JLabel defaultPanningLabel;
    private JSpinner defaultPanningValue;           // xm, it and str
    private SpinnerModel panSpinnerModel;
    private JSlider defaultPanningSlider;
    private JLabel usePanningLabel;
    private JCheckBox panning;                      // it and str
    private JLabel useSurroundLabel;
    private JCheckBox surround;                     // str
    // sampling tools (defining note frequency and C5 frequency)
    private JPanel samplingTools;
    private GridBagLayout samplingToolsLayout;
    private GridBagConstraints stc;
    private Border samplingToolsBorder;
    private JLabel c5SampleRateLabel;
    private SpinnerModel c5SpinnerModel;
    private JSpinner c5SampleRateSpinner;               // s3m, it and str
    private JLabel sampleTransposeTitleLabel;
    private int octaveRange;
    private ArrayList<String> notes;
    private JComboBox sampleTransposeComboBox;          // s3m, xm, it and str
    private JLabel transpositionOptionsTitleLabel;
    private JComboBox transpositionOptionsComboBox;     // str
    private JLabel resamplerOptionsTitleLabel;
    private JComboBox resamplerOptionsComboBox;         // str    
    private JLabel fineTuneTitleLabel;
    private SpinnerModel fineTuneSpinnerModel;
    private JSpinner fineTuneSpinner;                   // MOD, XM and str
    // looping tools
    private JPanel loopingTools;                        // all
    private GridBagLayout loopingToolsLayout;
    private GridBagConstraints lc;
    private Border loopingToolsBorder;
    private JLabel loopTitleLabel;
    private JComboBox loopComboBox;
    private JLabel loopStartTitleLabel;
    private JSpinner loopStartSpinner;
    private SpinnerModel loopStartSpinnerModel;
    private JLabel loopEndTitleLabel;
    private JSpinner loopEndSpinner;
    private SpinnerModel loopEndSpinnerModel;
    // sustain loop tools
    private JPanel susLoopTools;                    // it and str
    private GridBagLayout susLoopToolsLayout;
    private GridBagConstraints slc;
    private Border susLoopToolsBorder;
    private JLabel susLoopTitleLabel;
    private JComboBox susLoopComboBox;
    private JLabel susLoopStartTitleLabel;
    private JSpinner susLoopStartSpinner;
    private SpinnerModel susLoopStartSpinnerModel;
    private JLabel susLoopEndTitleLabel;
    private JSpinner susLoopEndSpinner;
    private SpinnerModel susLoopEndSpinnerModel;
    // vibrato options
    private JPanel vibratoOptions;                      // it and str
    private GridBagLayout vibOptionsToolsLayout;
    private GridBagConstraints voc;
    private Border vibOptionsBorder;
    private JComboBox vibWaveformComboBox;
    private Icon sineIcon;
    private Icon squareIcon;
    private Icon sawtoothIcon;
    private Icon randomIcon;
    // detuning options
    private JPanel detuningOptins;                      // str
    // adsr options
    private JPanel ADSROptions;                         // str

    // consturctor
    public SampleTools(int modType) {

        // set layout
        toolsLayout = new GridBagLayout();
        tc = new GridBagConstraints();
        tc.anchor = GridBagConstraints.NORTHWEST;
        tc.fill = GridBagConstraints.VERTICAL;
        this.setLayout(toolsLayout);
        this.modType = modType;

        // set the panels
        createSampleDetailsPanel();
        createSoundOptionsPanel();
        createLoopToolsPanel();
        createSustainLoopToolsPanel();
        createSampleToolsPanel();
    }

    // getters
    public JSpinner getDefaultVolumeValue() {
        return this.defaultVolumeValue;
    }

    public JSlider getDefaultVolumeSlider() {
        return this.defaultVolumeSlider;
    }

    public JSpinner getGlobalVolumeValue() {
        return this.globalVolumeValue;
    }

    public JSlider getGlobalVolumeSlider() {
        return this.globalVolumeSlider;
    }

    public JCheckBox getPanning() {
        return this.panning;
    }

    public JSpinner getDefaultPanningValue() {
        return this.defaultPanningValue;
    }

    public JSlider getDefaultPanningSlider() {
        return this.defaultPanningSlider;
    }

    public JTextField getSampleNameField() {
        return this.sampleNameField;
    }

    public JTextField getFileNameField() {
        return fileNameField;
    }

    public JLabel getSampleFormatLabel() {
        return sampleFormatLabel;
    }

    public JLabel getSampleChannelLabel() {
        return sampleChannelLabel;
    }

    public JLabel getSampleLengthLabel() {
        return sampleLengthLabel;
    }

    public int getModType() {
        return modType;
    }

    public JSpinner getC5SampleRateSpinner() {
        return c5SampleRateSpinner;
    }

    public JComboBox getLoopComboBox() {
        return loopComboBox;
    }

    public JSpinner getLoopStartSpinner() {
        return loopStartSpinner;
    }

    public JSpinner getLoopEndSpinner() {
        return loopEndSpinner;
    }

    public JComboBox getSusLoopComboBox() {
        return susLoopComboBox;
    }

    public JSpinner getSusLoopStartSpinner() {
        return susLoopStartSpinner;
    }

    public JSpinner getSusLoopEndSpinner() {
        return susLoopEndSpinner;
    }
    
    

    private void createSampleDetailsPanel() {

        // set the layout
        sampleDetailsLayout = new GridBagLayout();
        sdc = new GridBagConstraints();
        sdc.anchor = GridBagConstraints.SOUTHWEST;
        sdc.insets = DEF_INSETS;

        // set the border
        sampleDetailsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        sampleDetailsBorder
                = BorderFactory.createTitledBorder(sampleDetailsBorder,
                        "Sample details", 0, 0, BOLD_FONT);

        // set the sample options JPanel
        sampleDetails = new JPanel(sampleDetailsLayout, false);

        // set details border
        sampleDetails.setBorder(sampleDetailsBorder);

        // set the sample name label
        sampleNameLabel = new JLabel("Sample name: ");
        sampleNameLabel.setFont(DEF_FONT);
        sdc.gridx = 0;
        sdc.gridy = 0;
        sdc.weightx = 1.0;
        sdc.gridwidth = GridBagConstraints.REMAINDER;
        sampleDetails.add(sampleNameLabel, sdc);

        // set the sample name field
        sampleNameField = new JTextField("");
        sampleNameField.setToolTipText("Name of sample. ");
        sampleNameField.setPreferredSize(DETAILS_FIELD_SIZE);
        sdc.gridx = 0;
        sdc.gridy++;
        sdc.weightx = 1.0;
        sdc.gridwidth = GridBagConstraints.REMAINDER;
        sampleDetails.add(sampleNameField, sdc);

        if (modType == 2 || modType >= 4) {
            // set the file name label
            fileNameLabel = new JLabel("Sample file name: ");
            fileNameLabel.setFont(DEF_FONT);
            sdc.gridx = 0;
            sdc.gridy++;
            sdc.weightx = 0.0;
            sdc.gridwidth = 1;
            sampleDetails.add(fileNameLabel, sdc);

            // set the file name field
            fileNameField = new JTextField("");
            fileNameField.setToolTipText("File name of sample. "
                    + "Mostly used with s3m samples.");
            fileNameField.setPreferredSize(SMALLER_FIELD_SIZE);
            sdc.gridx = 1;
            sdc.weightx = 1.0;
            sdc.gridwidth = GridBagConstraints.REMAINDER;
            sampleDetails.add(fileNameField, sdc);
        }

        // set the sample format title label
        sampleFormatTitleLabel = new JLabel("Sample format: ");
        sampleFormatTitleLabel.setFont(DEF_FONT);
        sdc.gridx = 0;
        sdc.gridy++;
        sdc.weightx = 0.0;
        sdc.gridwidth = 1;
        sampleDetails.add(sampleFormatTitleLabel, sdc);

        // set the sample format label
        sampleFormatLabel = new JLabel();
        sampleFormatLabel.setFont(ITALIC_FONT);
        sampleFormatLabel.setPreferredSize(SMALLER_FIELD_SIZE);
        sdc.gridx = 1;
        sdc.weightx = 1.0;
        sdc.gridwidth = GridBagConstraints.REMAINDER;
        sampleDetails.add(sampleFormatLabel, sdc);

        // set the sample channel title label
        sampleChannelTitleLabel = new JLabel("Sample channels: ");
        sampleChannelTitleLabel.setFont(DEF_FONT);
        sdc.gridx = 0;
        sdc.gridy++;
        sdc.weightx = 0.0;
        sdc.gridwidth = 1;
        sampleDetails.add(sampleChannelTitleLabel, sdc);

        // set the sample channel label
        sampleChannelLabel = new JLabel();
        sampleChannelLabel.setFont(ITALIC_FONT);
        sampleChannelLabel.setPreferredSize(SMALLER_FIELD_SIZE);
        sdc.gridx = 1;
        sdc.weightx = 1.0;
        sdc.gridwidth = GridBagConstraints.REMAINDER;
        sampleDetails.add(sampleChannelLabel, sdc);

        // set the sample channel title label
        sampleLengthTitleLabel = new JLabel("Sample length: ");
        sampleLengthTitleLabel.setFont(DEF_FONT);
        sdc.gridx = 0;
        sdc.gridy++;
        sdc.weightx = 0.0;
        sdc.gridwidth = 1;
        sampleDetails.add(sampleLengthTitleLabel, sdc);

        // set the sample channel label
        sampleLengthLabel = new JLabel();
        sampleLengthLabel.setFont(ITALIC_FONT);
        sampleLengthLabel.setPreferredSize(SMALLER_FIELD_SIZE);
        sdc.gridx = 1;
        sdc.weightx = 1.0;
        sdc.gridwidth = GridBagConstraints.REMAINDER;
        sampleDetails.add(sampleLengthLabel, sdc);

        // add pannel to bottom column
        sdc.gridx = 0;
        sdc.gridy++;
        sdc.weightx = 2;
        sdc.weighty = 1;
        sdc.gridwidth = GridBagConstraints.REMAINDER;
        sdc.gridheight = GridBagConstraints.REMAINDER;
        sampleDetails.add(new JPanel(), sdc);

        // add to tools;
        tc.gridx = 0;
        tc.gridy = 0;
        tc.weighty = 1;
        tc.gridheight = 2;
        this.add(sampleDetails, tc);
    }

    private void createSoundOptionsPanel() {

        // set the layout
        soundOptionsLayout = new GridBagLayout();
        soc = new GridBagConstraints();
        soc.anchor = GridBagConstraints.SOUTHWEST;
        soc.insets = DEF_INSETS;

        // set the border
        soundOptionsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        soundOptionsBorder
                = BorderFactory.createTitledBorder(soundOptionsBorder,
                        "Sound options", 0, 0, BOLD_FONT);

        // set the sample options JPanel
        soundOptions = new JPanel(soundOptionsLayout, false);

        // set options border
        soundOptions.setBorder(soundOptionsBorder);

        // set the default volume label
        defaultVolumeLabel = new JLabel("Default volume: ");
        defaultVolumeLabel.setFont(DEF_FONT);
        soc.gridx = 0;
        soc.gridy = 0;
        soundOptions.add(defaultVolumeLabel, soc);

        // set default volume spinner model
        defVolumeSpinnerModel = new SpinnerNumberModel(64, 0, 64, 1);

        // set the default volume value spinner
        defaultVolumeValue = new JSpinner(defVolumeSpinnerModel);
        defaultVolumeValue.setPreferredSize(VALUE_SPINNER_SIZE);
        soc.gridx = 1;
        soc.gridy = 0;
        soc.insets = DEF_INSETS;
        soundOptions.add(defaultVolumeValue, soc);

        // set the default volume slider
        defaultVolumeSlider = new JSlider(0, 64, 64);
        soc.gridx = 2;
        soc.gridy = 0;
        soc.weightx = 1.0;
        soc.gridwidth = GridBagConstraints.REMAINDER;
        soundOptions.add(defaultVolumeSlider, soc);

        if (modType == 2 || modType >= 4) {

            // set the global volume label
            globalVolumeLabel = new JLabel("Global volume: ");
            globalVolumeLabel.setFont(DEF_FONT);
            soc.gridx = 0;
            soc.gridy = 1;
            soc.weightx = 0.0;
            soc.gridwidth = 0;
            soundOptions.add(globalVolumeLabel, soc);

            // set global volume spinner model
            globalVolumeSpinnerModel = new SpinnerNumberModel(64, 0, 64, 1);

            // set the global volume value spinner
            globalVolumeValue = new JSpinner(globalVolumeSpinnerModel);
            globalVolumeValue.setPreferredSize(VALUE_SPINNER_SIZE);
            soc.gridx = 1;
            soc.gridy = 1;
            soc.weightx = 0.0;
            soc.insets = DEF_INSETS;
            soundOptions.add(globalVolumeValue, soc);

            // set the global volume slider
            globalVolumeSlider = new JSlider(0, 64, 64);
            soc.gridx = 2;
            soc.gridy = 1;
            soc.weightx = 1.0;
            soc.gridwidth = GridBagConstraints.REMAINDER;
            soundOptions.add(globalVolumeSlider, soc);
        }

        // set the use panning label
        usePanningLabel = new JLabel("Use panning: ");
        usePanningLabel.setFont(DEF_FONT);
        soc.gridx = 0;
        soc.gridy = 2;
        soc.weightx = 0.0;
        soc.gridwidth = 0;
        soundOptions.add(usePanningLabel, soc);

        // set the use panning checkbox
        panning = new JCheckBox();
        panning.setSelected(false);
        soc.gridx = 2;
        soc.gridy = 2;
        soc.weightx = 1.0;
        soc.gridwidth = GridBagConstraints.REMAINDER;
        soc.insets = CHECKBOX_INSETS;
        soundOptions.add(panning, soc);

        // set the default panning label
        defaultPanningLabel = new JLabel("Default panning: ");
        defaultPanningLabel.setFont(DEF_FONT);
        soc.gridx = 0;
        soc.gridy = 3;
        soc.gridwidth = 1;
        soc.weightx = 0.0;
        soc.gridwidth = 0;
        soc.insets = DEF_INSETS;
        soundOptions.add(defaultPanningLabel, soc);

        // set the panning spinner model
        panSpinnerModel = new SpinnerNumberModel(32, 0, 64, 1);

        // set the default panning value spinner
        defaultPanningValue = new JSpinner(panSpinnerModel);
        defaultPanningValue.setPreferredSize(VALUE_SPINNER_SIZE);
        defaultPanningValue.setEnabled(panning.isSelected());
        soc.gridx = 1;
        soc.gridy = 3;
        soc.insets = DEF_INSETS;
        soundOptions.add(defaultPanningValue, soc);

        // set the default panning slider
        defaultPanningSlider = new JSlider(0, 64, 32);
        defaultPanningSlider.setEnabled(panning.isSelected());
        soc.gridx = 2;
        soc.gridy = 3;
        soc.weightx = 1.0;
        soc.gridwidth = GridBagConstraints.REMAINDER;
        soundOptions.add(defaultPanningSlider, soc);

        if (modType == 5) {
            // set the use surround label
            useSurroundLabel = new JLabel("Use surround: ");
            useSurroundLabel.setFont(DEF_FONT);
            soc.gridx = 0;
            soc.gridy = 4;
            soc.gridwidth = 0;
            soc.weightx = 0.0;
            soundOptions.add(useSurroundLabel, soc);

            // set the use surround checkbox
            surround = new JCheckBox();
            surround.setSelected(false);
            soc.gridx = 2;
            soc.gridy = 4;
            soc.weightx = 1.0;
            soc.gridwidth = GridBagConstraints.REMAINDER;
            soc.insets = CHECKBOX_INSETS;
            soundOptions.add(surround, soc);
        }

        // add pannel to bottom column
        sdc.gridx = 0;
        sdc.gridy = 5;
        sdc.weightx = 2;
        sdc.weighty = 1;
        sdc.gridwidth = GridBagConstraints.REMAINDER;
        sdc.gridheight = GridBagConstraints.REMAINDER;
        soundOptions.add(new JPanel(), sdc);

        // add to tools
        tc.gridx = 1;
        tc.gridy = 0;
        tc.weighty = 1;
        tc.gridheight = 2;
        this.add(soundOptions, tc);
    }

    private void createSampleToolsPanel() {

        // set the layout
        samplingToolsLayout = new GridBagLayout();
        stc = new GridBagConstraints();
        stc.anchor = GridBagConstraints.SOUTHWEST;
        stc.insets = DEF_INSETS;

        // set the border
        samplingToolsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        samplingToolsBorder
                = BorderFactory.createTitledBorder(samplingToolsBorder,
                        "Sampling tools", 0, 0, BOLD_FONT);

        // set the sample options JPanel
        samplingTools = new JPanel(samplingToolsLayout, false);

        // set options border
        samplingTools.setBorder(samplingToolsBorder);

        // set C5 sample rate label
        c5SampleRateLabel = new JLabel("C5 Sample Rate: ");
        c5SampleRateLabel.setFont(DEF_FONT);
        stc.gridx = 0;
        stc.gridy = 0;
        samplingTools.add(c5SampleRateLabel, stc);

        // set C5 sample rate spinner model
        c5SpinnerModel = new SpinnerNumberModel(8363, 0, 9999999, 1);

        // set C5 sample rate spinner
        c5SampleRateSpinner = new JSpinner(c5SpinnerModel);
        c5SampleRateSpinner.setToolTipText("Sample rate for Middle C. ");
        c5SampleRateSpinner.setPreferredSize(C5_SPINNER_SIZE);
        stc.gridx = 1;
        stc.gridy = 0;
        samplingTools.add(c5SampleRateSpinner, stc);

        // set sample transpose title label
        sampleTransposeTitleLabel = new JLabel("Tramsposition: ");
        sampleTransposeTitleLabel.setFont(DEF_FONT);
        stc.gridx = 0;
        stc.gridy = 1;
        samplingTools.add(sampleTransposeTitleLabel, stc);

        // make list for combo box
        notes = new ArrayList<String>();

        for (int i = 0; i <= 10; i++) {  // i is octave

            // iterate through all 12 notes per octave
            for (String note : SAMPLE_NOTES) {

                // add note to notes list
                notes.add(note + i);
            }
        }

        // make combo box
        sampleTransposeComboBox = new JComboBox(notes.toArray());
        sampleTransposeComboBox.setPreferredSize(NOTE_COMBO_BOX_SIZE);
        sampleTransposeComboBox.setSelectedIndex(60);
        stc.gridx = 1;
        stc.gridy = 1;
        samplingTools.add(sampleTransposeComboBox, stc);

        if (modType == 1 || modType == 3 || modType == 5) {

            // set fine tuning title label
            fineTuneTitleLabel = new JLabel("Fine tuning: ");
            fineTuneTitleLabel.setFont(DEF_FONT);
            stc.gridx = 2;
            stc.gridy = 0;
            samplingTools.add(fineTuneTitleLabel, stc);

            // set fine tuning spinner model
            fineTuneSpinnerModel = new SpinnerNumberModel(0, -8, 8, 1);

            // set fine tuning spinner
            fineTuneSpinner = new JSpinner(fineTuneSpinnerModel);
            fineTuneSpinner.setPreferredSize(FTUNE_SPINNER_SIZE);
            stc.gridx = 3;
            stc.gridy = 0;
            samplingTools.add(fineTuneSpinner, stc);

        }

        if (modType == 5) {

            // set resampler options title label
            resamplerOptionsTitleLabel = new JLabel("Resampler options: ");
            resamplerOptionsTitleLabel.setFont(DEF_FONT);
            stc.gridx = 2;
            stc.gridy = 1;
            samplingTools.add(resamplerOptionsTitleLabel, stc);

            // make resampler options combo box
            resamplerOptionsComboBox = new JComboBox(RESAMPLER_OPTIONS);
            resamplerOptionsComboBox.setSelectedIndex(0);
            stc.gridx = 3;
            stc.gridy = 1;
            samplingTools.add(resamplerOptionsComboBox, stc);

            // set transpositions options title label
            transpositionOptionsTitleLabel = new JLabel("Tramsposition options: ");
            transpositionOptionsTitleLabel.setFont(DEF_FONT);
            stc.gridx = 4;
            stc.gridy = 0;
            samplingTools.add(transpositionOptionsTitleLabel, stc);

            // make transpositions options combo box
            transpositionOptionsComboBox = new JComboBox(TRANSPOSITION_OPTIONS);
            transpositionOptionsComboBox.setSelectedIndex(1);
            stc.gridx = 4;
            stc.gridy = 1;
            samplingTools.add(transpositionOptionsComboBox, stc);

        }

        // add pannel to bottom column
        stc.gridx = 0;
        stc.gridy = 2;
        stc.weightx = 2;
        stc.weighty = 1;
        stc.gridwidth = GridBagConstraints.REMAINDER;
        stc.gridheight = GridBagConstraints.REMAINDER;
        samplingTools.add(new JPanel(), stc);

        // add to tools
        tc.gridx = 2;
        tc.gridy = 1;
        tc.weightx = 1.0;
        tc.weighty = 1.0;
        tc.gridwidth = 2;
        tc.gridheight = 1;
        this.add(samplingTools, tc);
    }

    private void createLoopToolsPanel() {

        // set the layout
        loopingToolsLayout = new GridBagLayout();
        lc = new GridBagConstraints();
        lc.anchor = GridBagConstraints.SOUTHWEST;
        lc.insets = DEF_INSETS;

        // set the border
        loopingToolsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        loopingToolsBorder
                = BorderFactory.createTitledBorder(loopingToolsBorder,
                        "Loop tools: ", 0, 0, BOLD_FONT);

        // set the loop tools JPanel
        loopingTools = new JPanel(loopingToolsLayout, false);

        // set loop tools border
        loopingTools.setBorder(loopingToolsBorder);

        // set loop title label
        loopTitleLabel = new JLabel("Loop");
        loopTitleLabel.setFont(DEF_FONT);
        lc.gridx = 0;
        lc.gridy = 0;
        loopingTools.add(loopTitleLabel, lc);

        // set loop combo box
        loopComboBox = new JComboBox(LOOP_OPTIONS);
        loopComboBox.setSelectedIndex(0);
        lc.gridx = 1;
        lc.gridy = 0;
        lc.weightx = 1;
        lc.gridwidth = GridBagConstraints.REMAINDER;
        loopingTools.add(loopComboBox, lc);

        // set loop start title
        loopStartTitleLabel = new JLabel("Start: ");
        loopStartTitleLabel.setFont(DEF_FONT);
        lc.gridx = 0;
        lc.gridy = 1;
        lc.weightx = 0;
        lc.gridwidth = 0;
        loopingTools.add(loopStartTitleLabel, lc);

        // set loop start spinner model
        loopStartSpinnerModel
                = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);

        // set loop start spinner
        loopStartSpinner = new JSpinner(loopStartSpinnerModel);
        loopStartSpinner.setPreferredSize(C5_SPINNER_SIZE);
        lc.gridx = 1;
        lc.gridy = 1;
        lc.weightx = 1;
        lc.gridwidth = GridBagConstraints.REMAINDER;
        loopingTools.add(loopStartSpinner, lc);

        // set loop end title
        loopEndTitleLabel = new JLabel("End: ");
        loopEndTitleLabel.setFont(DEF_FONT);
        lc.gridx = 0;
        lc.gridy = 2;
        lc.weightx = 0;
        lc.gridwidth = 0;
        loopingTools.add(loopEndTitleLabel, lc);

        // set loop start spinner model
        loopEndSpinnerModel
                = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);

        // set loop start spinner
        loopEndSpinner = new JSpinner(loopEndSpinnerModel);
        loopEndSpinner.setPreferredSize(C5_SPINNER_SIZE);
        lc.gridx = 1;
        lc.gridy = 2;
        lc.weightx = 1;
        lc.gridwidth = GridBagConstraints.REMAINDER;
        loopingTools.add(loopEndSpinner, lc);

        // add pannel to bottom column
        lc.gridx = 0;
        lc.gridy = 3;
        lc.weightx = 2;
        lc.weighty = 1;
        lc.gridwidth = GridBagConstraints.REMAINDER;
        lc.gridheight = GridBagConstraints.REMAINDER;

        loopingTools.add(new JPanel(), lc);

        // add to tools
        tc.gridx = 2;
        tc.gridy = 0;
        tc.weightx = 0.0;
        tc.weighty = 0.0;
        tc.gridwidth = 1;
        tc.gridheight = 1;

        this.add(loopingTools, tc);
    }

    private void createSustainLoopToolsPanel() {

        // set the layout
        susLoopToolsLayout = new GridBagLayout();
        slc = new GridBagConstraints();
        slc.anchor = GridBagConstraints.SOUTHWEST;
        slc.insets = DEF_INSETS;

        // set the border
        susLoopToolsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        susLoopToolsBorder
                = BorderFactory.createTitledBorder(susLoopToolsBorder,
                        "Sustain loop tools: ", 0, 0, BOLD_FONT);

        // set the loop tools JPanel
        susLoopTools = new JPanel(susLoopToolsLayout, false);

        // set loop tools border
        susLoopTools.setBorder(susLoopToolsBorder);

        // set loop title label
        susLoopTitleLabel = new JLabel("Loop");
        susLoopTitleLabel.setFont(DEF_FONT);
        slc.gridx = 0;
        slc.gridy = 0;
        susLoopTools.add(susLoopTitleLabel, slc);

        // set loop combo box
        susLoopComboBox = new JComboBox(LOOP_OPTIONS);
        susLoopComboBox.setSelectedIndex(0);
        slc.gridx = 1;
        slc.gridy = 0;
        slc.weightx = 1;
        slc.gridwidth = GridBagConstraints.REMAINDER;
        susLoopTools.add(susLoopComboBox, slc);

        // set loop start title
        susLoopStartTitleLabel = new JLabel("Start: ");
        susLoopStartTitleLabel.setFont(DEF_FONT);
        slc.gridx = 0;
        slc.gridy = 1;
        slc.weightx = 0;
        slc.gridwidth = 0;
        susLoopTools.add(susLoopStartTitleLabel, slc);

        // set loop start spinner model
        susLoopStartSpinnerModel
                = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);

        // set loop start spinner
        susLoopStartSpinner = new JSpinner(susLoopStartSpinnerModel);
        susLoopStartSpinner.setPreferredSize(C5_SPINNER_SIZE);
        slc.gridx = 1;
        slc.gridy = 1;
        slc.weightx = 1;
        slc.gridwidth = GridBagConstraints.REMAINDER;
        susLoopTools.add(susLoopStartSpinner, slc);

        // set loop end title
        susLoopEndTitleLabel = new JLabel("End: ");
        susLoopEndTitleLabel.setFont(DEF_FONT);
        slc.gridx = 0;
        slc.gridy = 2;
        slc.weightx = 0;
        slc.gridwidth = 0;
        susLoopTools.add(susLoopEndTitleLabel, slc);

        // set loop start spinner model
        susLoopEndSpinnerModel
                = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);

        // set loop start spinner
        susLoopEndSpinner = new JSpinner(susLoopEndSpinnerModel);
        susLoopEndSpinner.setPreferredSize(C5_SPINNER_SIZE);
        slc.gridx = 1;
        slc.gridy = 2;
        slc.weightx = 1;
        slc.gridwidth = GridBagConstraints.REMAINDER;
        susLoopTools.add(susLoopEndSpinner, slc);

        // add pannel to bottom column
        slc.gridx = 0;
        slc.gridy = 3;
        slc.weightx = 2;
        slc.weighty = 1;
        slc.gridwidth = GridBagConstraints.REMAINDER;
        slc.gridheight = GridBagConstraints.REMAINDER;

        susLoopTools.add(new JPanel(), slc);

        // add to tools
        tc.gridx = 3;
        tc.gridy = 0;
        tc.weightx = 0.0;
        tc.weighty = 0.0;
        tc.gridwidth = 1;
        tc.gridheight = 1;

        this.add(susLoopTools, tc);
    }

    public void createVibratoOptions() {

    }

    // action listeners
    public void addDefVolumeValChangeListener(ChangeListener changePerformed) {
        this.defaultVolumeValue.addChangeListener(changePerformed);
    }

    public void addDefVolumeSliderChangeListener(ChangeListener changePerformed) {
        this.defaultVolumeSlider.addChangeListener(changePerformed);
    }

    public void addGlobVolumeValChangeListener(ChangeListener changePerformed) {
        this.globalVolumeValue.addChangeListener(changePerformed);
    }

    public void addGlobVolumeSliderChangeListener(ChangeListener changePerformed) {
        this.globalVolumeSlider.addChangeListener(changePerformed);
    }

    public void addPaningActionListener(ActionListener actionPerformed) {
        this.panning.addActionListener(actionPerformed);
    }

    public void addDefPanValChangeListener(ChangeListener changePerformed) {
        this.defaultPanningValue.addChangeListener(changePerformed);
    }

    public void addDefPanSliderChangeListener(ChangeListener changePerformed) {
        this.defaultPanningSlider.addChangeListener(changePerformed);
    }

    public void addSurrondActionListener(ActionListener actionPerformed) {
        this.surround.addActionListener(actionPerformed);
    }
}
