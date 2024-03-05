/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.samples;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.ITALIC_FONT;
import static ui.UIProperties.SMALLER_FIELD_SIZE;
import static ui.UIProperties.LARGE_FIELD_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class SampleDetails extends JPanel {
    
    // instance variables
    private int modType;
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
    
    // constructor
    public SampleDetails(int modType) {
        this.modType = modType;
        init();
    }
    
    // getters
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
    
    private void init() {
        
        // set the layout
        sampleDetailsLayout = new GridBagLayout();
        setLayout(sampleDetailsLayout);
        sdc = new GridBagConstraints();
        sdc.anchor = GridBagConstraints.SOUTHWEST;
        sdc.insets = DEF_INSETS;
        sdc.fill = GridBagConstraints.HORIZONTAL;

        // set the border
        sampleDetailsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        sampleDetailsBorder
                = BorderFactory.createTitledBorder(sampleDetailsBorder,
                        "Sample details", 0, 0, BOLD_FONT);

        // set details border
        setBorder(sampleDetailsBorder);

        // set the sample name label
        sampleNameLabel = new JLabel("Sample name: ");
        sampleNameLabel.setFont(DEF_FONT);
        sdc.gridx = 0;
        sdc.gridy = 0;
        sdc.weightx = 1.0;
        sdc.gridwidth = GridBagConstraints.REMAINDER;
        add(sampleNameLabel, sdc);

        // set the sample name field
        sampleNameField = new JTextField("");
        sampleNameField.setToolTipText("Name of sample. ");
        sampleNameField.setPreferredSize(LARGE_FIELD_SIZE);
        sdc.gridx = 0;
        sdc.gridy++;
        sdc.weightx = 1.0;
        sdc.gridwidth = GridBagConstraints.REMAINDER;
        add(sampleNameField, sdc);

        if (modType == 2 || modType >= 4) {
            // set the file name label
            fileNameLabel = new JLabel("Sample file name: ");
            fileNameLabel.setFont(DEF_FONT);
            sdc.gridx = 0;
            sdc.gridy++;
            sdc.weightx = 0.0;
            sdc.gridwidth = 1;
            add(fileNameLabel, sdc);

            // set the file name field
            fileNameField = new JTextField("");
            fileNameField.setToolTipText("File name of sample. "
                    + "Mostly used with s3m samples.");
            fileNameField.setPreferredSize(SMALLER_FIELD_SIZE);
            sdc.gridx = 1;
            sdc.weightx = 1.0;
            sdc.gridwidth = GridBagConstraints.REMAINDER;
            add(fileNameField, sdc);
        }

        // set the sample format title label
        sampleFormatTitleLabel = new JLabel("Sample format: ");
        sampleFormatTitleLabel.setFont(DEF_FONT);
        sdc.gridx = 0;
        sdc.gridy++;
        sdc.weightx = 0.0;
        sdc.gridwidth = 1;
        add(sampleFormatTitleLabel, sdc);

        // set the sample format label
        sampleFormatLabel = new JLabel();
        sampleFormatLabel.setFont(ITALIC_FONT);
        sampleFormatLabel.setPreferredSize(SMALLER_FIELD_SIZE);
        sdc.gridx = 1;
        sdc.weightx = 1.0;
        sdc.gridwidth = GridBagConstraints.REMAINDER;
        add(sampleFormatLabel, sdc);

        // set the sample channel title label
        sampleChannelTitleLabel = new JLabel("Sample channels: ");
        sampleChannelTitleLabel.setFont(DEF_FONT);
        sdc.gridx = 0;
        sdc.gridy++;
        sdc.weightx = 0.0;
        sdc.gridwidth = 1;
        add(sampleChannelTitleLabel, sdc);

        // set the sample channel label
        sampleChannelLabel = new JLabel();
        sampleChannelLabel.setFont(ITALIC_FONT);
        sampleChannelLabel.setPreferredSize(SMALLER_FIELD_SIZE);
        sdc.gridx = 1;
        sdc.weightx = 1.0;
        sdc.gridwidth = GridBagConstraints.REMAINDER;
        add(sampleChannelLabel, sdc);

        // set the sample length title label
        sampleLengthTitleLabel = new JLabel("Sample length: ");
        sampleLengthTitleLabel.setFont(DEF_FONT);
        sdc.gridx = 0;
        sdc.gridy++;
        sdc.weightx = 0.0;
        sdc.gridwidth = 1;
        add(sampleLengthTitleLabel, sdc);

        // set the sample length label
        sampleLengthLabel = new JLabel();
        sampleLengthLabel.setFont(ITALIC_FONT);
        sampleLengthLabel.setPreferredSize(SMALLER_FIELD_SIZE);
        sdc.gridx = 1;
        sdc.weightx = 1.0;
        sdc.gridwidth = GridBagConstraints.REMAINDER;
        add(sampleLengthLabel, sdc);

        // add pannel to bottom column
        sdc.gridx = 0;
        sdc.gridy++;
        sdc.weightx = 2;
        sdc.weighty = 1;
        sdc.gridwidth = GridBagConstraints.REMAINDER;
        sdc.gridheight = GridBagConstraints.REMAINDER;
        add(new JPanel(), sdc);
    }
    
    // events and listeners
    public void addSampleNameFieldActionListener(ActionListener actionPerformed) {
        sampleNameField.addActionListener(actionPerformed);
    }
    
    public void addFileNameFieldActionListener(ActionListener actionPerformed) {
        fileNameField.addActionListener(actionPerformed);
    }
}
