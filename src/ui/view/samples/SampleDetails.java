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
import lang.LanguageHandler;
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
    private LanguageHandler languageHandler;
    private GridBagLayout sampleDetailsLayout;
    private GridBagConstraints sdc;
    private Border sampleDetailsBorder;
    // all
    private JLabel sampleNameLabel;
    private JTextField sampleNameField;
    // s3m, it and str
    private JLabel fileNameLabel;
    private JTextField fileNameField;
    private JLabel sampleFormatTitleLabel;
    private JLabel sampleFormatLabel;
    // all
    private JLabel sampleChannelTitleLabel;
    // all                   
    private JLabel sampleChannelLabel;
    private JLabel sampleLengthTitleLabel;
    private JLabel sampleLengthLabel;

    // constructor
    public SampleDetails(int modType, LanguageHandler languageHandler) {
        this.modType = modType;
        this.languageHandler = languageHandler;
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
        sdc.fill = GridBagConstraints.BOTH;

        // set the border
        sampleDetailsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        sampleDetailsBorder
                = BorderFactory.createTitledBorder(sampleDetailsBorder,
                        languageHandler.getLanguageText("sample.details"),
                        0, 0, BOLD_FONT);

        // set details border
        setBorder(sampleDetailsBorder);

        // set the sample name label
        sampleNameLabel = new JLabel(languageHandler
                .getLanguageText("sample.details.name"));
        sampleNameLabel.setFont(DEF_FONT);
        sdc.gridx = 0;
        sdc.gridy = 0;
        sdc.weightx = 1.0;
        sdc.gridwidth = GridBagConstraints.REMAINDER;
        add(sampleNameLabel, sdc);

        // set the sample name field
        sampleNameField = new JTextField("");
        sampleNameField.setToolTipText(languageHandler
                .getLanguageText("sample.details.name.desc"));
        sampleNameField.setPreferredSize(LARGE_FIELD_SIZE);
        sdc.gridx = 0;
        sdc.gridy++;
        sdc.weightx = 1.0;
        sdc.gridwidth = GridBagConstraints.REMAINDER;
        add(sampleNameField, sdc);

        if (modType == 2 || modType >= 4) {
            // set the file name label
            fileNameLabel = new JLabel(languageHandler
                    .getLanguageText("sample.details.dos_file"));
            fileNameLabel.setFont(DEF_FONT);
            sdc.gridx = 0;
            sdc.gridy++;
            sdc.weightx = 0.0;
            sdc.gridwidth = 1;
            add(fileNameLabel, sdc);

            // set the file name field
            fileNameField = new JTextField("");
            fileNameField.setToolTipText(languageHandler
                    .getLanguageText("sample.details.dos_file.desc"));
            fileNameField.setPreferredSize(SMALLER_FIELD_SIZE);
            sdc.gridx = 1;
            sdc.weightx = 1.0;
            sdc.gridwidth = GridBagConstraints.REMAINDER;
            add(fileNameField, sdc);
        }

        // set the sample format title label
        sampleFormatTitleLabel = new JLabel(languageHandler
                .getLanguageText("sample.details.format"));
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
        sampleChannelTitleLabel = new JLabel(languageHandler
                .getLanguageText("sample.details.channels"));
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
        sampleLengthTitleLabel = new JLabel(languageHandler
                .getLanguageText("sample.details.length"));
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
