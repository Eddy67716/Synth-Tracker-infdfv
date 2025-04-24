/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.instruments;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import lang.LanguageHandler;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.SMALLER_FIELD_SIZE;
import static ui.UIProperties.LARGE_FIELD_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class InstrumentDetails extends JPanel {
    
    // instance variables
    private int modType;
    private LanguageHandler languageHandler;
    private GridBagLayout instrumentDetailsLayout;
    private GridBagConstraints idc;
    private Border instrumentDetailsBorder;
    private JLabel instrumentNameLabel;
    private JTextField instrumentNameField;         // all
    private JLabel fileNameLabel;
    private JTextField fileNameField;               // it and str
    
    // constructor
    public InstrumentDetails(int modType, LanguageHandler languageHandler) {
        this.modType = modType;
        this.languageHandler = languageHandler;
        init();
    }
    
    // getters
    public JTextField getInstrumentNameField() {
        return instrumentNameField;
    }

    public JTextField getFileNameField() {
        return fileNameField;
    }
    
    private void init() {
        
        // set the layout
        instrumentDetailsLayout = new GridBagLayout();
        setLayout(instrumentDetailsLayout);
        idc = new GridBagConstraints();
        idc.anchor = GridBagConstraints.SOUTHWEST;
        idc.fill = GridBagConstraints.BOTH;  
        idc.insets = DEF_INSETS;

        // set the border
        instrumentDetailsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        instrumentDetailsBorder
                = BorderFactory.createTitledBorder(instrumentDetailsBorder,
                        languageHandler.getLanguageText("instrument.details"), 
                        0, 0, BOLD_FONT);

        // set details border
        setBorder(instrumentDetailsBorder);

        // set the instrument name label
        instrumentNameLabel = new JLabel(languageHandler
                .getLanguageText("instrument.details.name"));
        instrumentNameLabel.setFont(DEF_FONT);
        idc.gridx = 0;
        idc.gridy = 0;
        idc.weightx = 0;
        idc.gridwidth = GridBagConstraints.REMAINDER;
        add(instrumentNameLabel, idc);

        // set the instrument name field
        instrumentNameField = new JTextField("");
        instrumentNameField.setToolTipText(languageHandler
                .getLanguageText("instrument.details.name.desc"));
        instrumentNameField.setPreferredSize(LARGE_FIELD_SIZE);
        idc.gridx = 0;
        idc.gridy++;
        idc.weightx = 1;
        idc.gridwidth = GridBagConstraints.REMAINDER;
        add(instrumentNameField, idc);

        if (modType >= 4) {
            // set the file name label
            fileNameLabel = new JLabel(languageHandler
                .getLanguageText("instrument.details.dos_file"));
            fileNameLabel.setFont(DEF_FONT);
            idc.gridx = 0;
            idc.gridy++;
            idc.weightx = 0;
            idc.gridwidth = 1;
            add(fileNameLabel, idc);

            // set the file name field
            fileNameField = new JTextField("");
            fileNameField.setToolTipText(languageHandler
                .getLanguageText("instrument.details.dos_file.desc"));
            fileNameField.setPreferredSize(SMALLER_FIELD_SIZE);
            idc.gridx = 1;
            idc.weightx = 1;
            idc.gridwidth = GridBagConstraints.REMAINDER;
            add(fileNameField, idc);
        }
    }
    
    // listeners
    public void addInstrumentNameActionListener(ActionListener actionPerformed) {
        this.instrumentNameField.addActionListener(actionPerformed);
    }
    
    public void addInstrumentFileNameActionListener(ActionListener actionPerformed) {
        this.fileNameField.addActionListener(actionPerformed);
    }
}
