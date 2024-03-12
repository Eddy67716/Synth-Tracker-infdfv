/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.instruments;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.C5_SPINNER_SIZE;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.LARGE_FIELD_SIZE;
import static ui.UIProperties.MEDIUM_FIELD_SIZE;
import static sound.midi.MidiTables.MIDI_GEN_1_PROGRAM_LIST;

/**
 *
 * @author Edward Jenkins
 */
public class MidiOptions extends JPanel {
    
    // constants
    
    // instance variables
    private int modType;
    private GridBagLayout midiOptionsLayout;
    private GridBagConstraints moc;
    private Border midiOptionsBorder;
    private JLabel midiChannelLabel;
    private JSpinner midiChannelSpinner;              // it and str
    private SpinnerModel midiChannelSpinnerModel;
    private JLabel midiInstrumentLabel;
    private JSpinner midiInstrumentSpinner;              // it and str
    private SpinnerModel midiInstrumentSpinnerModel;
    private JLabel midiInstrumentsTypeLabel;
    private JLabel midiBankLabel;
    private JSpinner midiBankSpinner;              // it and str
    private SpinnerModel midiBankSpinnerModel;
    private JLabel midiBankTypeLabel;
    
    // constructor
    public MidiOptions(int modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    public JSpinner getMidiChannelSpinner() {
        return midiChannelSpinner;
    }

    public JSpinner getMidiInstrumentSpinner() {
        return midiInstrumentSpinner;
    }

    public JSpinner getMidiBankSpinner() {
        return midiBankSpinner;
    }
    
    
    public void init() {
        
        // set the layout
        midiOptionsLayout = new GridBagLayout();
        setLayout(midiOptionsLayout);
        moc = new GridBagConstraints();
        moc.anchor = GridBagConstraints.SOUTHWEST;
        moc.insets = DEF_INSETS;

        // set the border
        midiOptionsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        midiOptionsBorder
                = BorderFactory.createTitledBorder(midiOptionsBorder,
                        "MIDI options", 0, 0, BOLD_FONT);

        // set options border
        setBorder(midiOptionsBorder);
        
        // set the MIDI channel label
        midiChannelLabel = new JLabel("MIDI channel: ");
        midiChannelLabel.setFont(DEF_FONT);
        midiChannelLabel.setPreferredSize(LARGE_FIELD_SIZE);
        moc.gridx = 0;
        moc.gridy = 0;
        add(midiChannelLabel, moc);

        // set MIDI channel spinner model
        midiChannelSpinnerModel = new SpinnerNumberModel(0, 0, 17, 1);

        // set the MIDI channel value spinner
        midiChannelSpinner = new JSpinner(midiChannelSpinnerModel);
        midiChannelSpinner.setPreferredSize(C5_SPINNER_SIZE);
        moc.gridx = 1;
        moc.gridy = 0;
        moc.weightx = 1.0;
        moc.gridwidth = GridBagConstraints.REMAINDER;
        add(midiChannelSpinner, moc);
        
        // set the MIDI instrument label
        midiInstrumentLabel = new JLabel("MIDI instrument: ");
        midiInstrumentLabel.setFont(DEF_FONT);
        midiInstrumentLabel.setPreferredSize(LARGE_FIELD_SIZE);
        moc.gridx = 0;
        moc.weightx = 0.0;
        moc.gridwidth = 0;
        moc.gridy++;
        add(midiInstrumentLabel, moc);

        // set MIDI instrument spinner model
        midiInstrumentSpinnerModel = new SpinnerNumberModel(0, 0, 128, 1);

        // set the MIDI instrument value spinner
        midiInstrumentSpinner = new JSpinner(midiInstrumentSpinnerModel);
        midiInstrumentSpinner.setPreferredSize(C5_SPINNER_SIZE);
        moc.gridx = 1;
        moc.weightx = 1.0;
        moc.gridwidth = GridBagConstraints.REMAINDER;
        add(midiInstrumentSpinner, moc);
        
        // set the MIDI bank label
        midiBankLabel = new JLabel("MIDI bank: ");
        midiBankLabel.setFont(DEF_FONT);
        midiBankLabel.setPreferredSize(LARGE_FIELD_SIZE);
        moc.gridx = 0;
        moc.weightx = 0.0;
        moc.gridwidth = 0;
        moc.gridy++;
        add(midiBankLabel, moc);

        // set MIDI bank spinner model
        midiBankSpinnerModel = new SpinnerNumberModel(0, 0, 16384, 1);

        // set the MIDI bank value spinner
        midiBankSpinner = new JSpinner(midiBankSpinnerModel);
        midiBankSpinner.setPreferredSize(C5_SPINNER_SIZE);
        moc.gridx = 1;
        moc.weightx = 1.0;
        moc.gridwidth = GridBagConstraints.REMAINDER;
        add(midiBankSpinner, moc);
        
        // add pannel to bottom column
        moc.gridx = 0;
        moc.gridy++;
        moc.weightx = 2;
        moc.weighty = 1;
        moc.gridwidth = GridBagConstraints.REMAINDER;
        moc.gridheight = GridBagConstraints.REMAINDER;
        add(new JPanel(), moc);
    }
    
    // change listeners
    public void addMidiChannelChangeListener(ChangeListener changePerformed) {
        this.midiChannelSpinner.addChangeListener(changePerformed);
    }
    
    public void addMidiInstrumentChangeListener(ChangeListener changePerformed) {
        this.midiInstrumentSpinner.addChangeListener(changePerformed);
    }
    
    public void addMidiBankChangeListener(ChangeListener changePerformed) {
        this.midiBankSpinner.addChangeListener(changePerformed);
    }
}
