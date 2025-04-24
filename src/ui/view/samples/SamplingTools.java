/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.samples;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;
import lang.LanguageHandler;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.C5_SPINNER_SIZE;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.NOTE_COMBO_BOX_SIZE;
import static ui.UIProperties.SAMPLE_NOTES;

/**
 *
 * @author Edward Jenkins
 */
public class SamplingTools extends JPanel {

    // constants
    private static final Dimension FTUNE_SPINNER_SIZE = new Dimension(85, 20);
    private static final String[] RESAMPLER_OPTIONS = {"Stair-Step", "Sinc LPF"};

    // instance variables
    private int modType;
    private LanguageHandler languageHandler;
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
    private JLabel resamplerOptionsTitleLabel;
    private JComboBox resamplerOptionsComboBox;         // str    
    private JLabel fineTuneTitleLabel;
    private SpinnerModel fineTuneSpinnerModel;
    private JSpinner fineTuneSpinner;                   // MOD, XM and str
    private JLabel originalSampleRateTileLabel;
    private JLabel originalSampleRateLabel;

    // constructor
    public SamplingTools(int modType, LanguageHandler languageHandler) {
        this.modType = modType;
        this.languageHandler = languageHandler;
        init();
    }

    // getters
    public JSpinner getC5SampleRateSpinner() {
        return c5SampleRateSpinner;
    }

    public JComboBox getSampleTransposeComboBox() {
        return sampleTransposeComboBox;
    }

    private void init() {

        // set the layout
        samplingToolsLayout = new GridBagLayout();
        setLayout(samplingToolsLayout);
        stc = new GridBagConstraints();
        stc.anchor = GridBagConstraints.SOUTHWEST;
        //stc.fill = GridBagConstraints.BOTH;
        stc.insets = DEF_INSETS;

        // set the border
        samplingToolsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        samplingToolsBorder
                = BorderFactory.createTitledBorder(samplingToolsBorder,
                        languageHandler
                                .getLanguageText("sample.sampling_tools"),
                        0, 0, BOLD_FONT);

        // set options border
        setBorder(samplingToolsBorder);

        // set C5 sample rate label
        c5SampleRateLabel = new JLabel(languageHandler
                .getLanguageText("sample.sampling_tools.middle_c_rate"));
        c5SampleRateLabel.setFont(DEF_FONT);
        stc.gridx = 0;
        stc.gridy = 0;
        add(c5SampleRateLabel, stc);

        // set C5 sample rate spinner model
        c5SpinnerModel = new SpinnerNumberModel(8363, 2, 9999999, 1);

        // set C5 sample rate spinner
        c5SampleRateSpinner = new JSpinner(c5SpinnerModel);
        c5SampleRateSpinner.setToolTipText(languageHandler
                .getLanguageText("sample.sampling_tools.middle_c_rate.desc"));
        c5SampleRateSpinner.setPreferredSize(C5_SPINNER_SIZE);
        stc.gridx = 1;
        stc.gridy = 0;
        add(c5SampleRateSpinner, stc);

        // set sample transpose title label
        sampleTransposeTitleLabel = new JLabel(languageHandler
                .getLanguageText("sample.sampling_tools.transposition"));
        sampleTransposeTitleLabel.setFont(DEF_FONT);
        stc.gridx = 0;
        stc.gridy = 1;
        add(sampleTransposeTitleLabel, stc);

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
        add(sampleTransposeComboBox, stc);

        if (modType == 1 || modType == 3 || modType == 6) {

            // set fine tuning title label
            fineTuneTitleLabel = new JLabel(languageHandler
                .getLanguageText("sample.sampling_tools.tuning"));
            fineTuneTitleLabel.setFont(DEF_FONT);
            stc.gridx = 2;
            stc.gridy = 0;
            add(fineTuneTitleLabel, stc);

            // set fine tuning spinner model
            fineTuneSpinnerModel = new SpinnerNumberModel(0, -8, 8, 1);

            // set fine tuning spinner
            fineTuneSpinner = new JSpinner(fineTuneSpinnerModel);
            fineTuneSpinner.setPreferredSize(FTUNE_SPINNER_SIZE);
            stc.gridx = 3;
            stc.gridy = 0;
            add(fineTuneSpinner, stc);

        }

        if (modType == 6) {

            // set resampler options title label
            resamplerOptionsTitleLabel = new JLabel("Resampler options: ");
            resamplerOptionsTitleLabel.setFont(DEF_FONT);
            stc.gridx = 2;
            stc.gridy = 1;
            add(resamplerOptionsTitleLabel, stc);

            // make resampler options combo box
            resamplerOptionsComboBox = new JComboBox(RESAMPLER_OPTIONS);
            resamplerOptionsComboBox.setSelectedIndex(0);
            stc.gridx = 3;
            stc.gridy = 1;
            add(resamplerOptionsComboBox, stc);

            // set original sample rate label
            originalSampleRateTileLabel = new JLabel("Original sample rate: ");
            originalSampleRateTileLabel.setFont(DEF_FONT);
            stc.gridx = 4;
            stc.gridy = 0;
            add(originalSampleRateTileLabel, stc);

            // make origian sample rate label
            originalSampleRateLabel = new JLabel("44100");
            stc.gridx = 4;
            stc.gridy = 1;
            add(originalSampleRateLabel, stc);

        }

        // add pannel to bottom column
        stc.gridx = 0;
        stc.gridy = 2;
        stc.weightx = 2;
        stc.weighty = 1;
        stc.gridwidth = GridBagConstraints.REMAINDER;
        stc.gridheight = GridBagConstraints.REMAINDER;
        add(new JPanel(), stc);
    }

    // events and listeners
    public void addC5SampleRateSpinnerChangeEvent(
            ChangeListener changePerformed) {
        c5SampleRateSpinner.addChangeListener(changePerformed);
    }

    public void addSampleTransposeComboBox(ActionListener actionPerformed) {
        sampleTransposeComboBox.addActionListener(actionPerformed);
    }
}
