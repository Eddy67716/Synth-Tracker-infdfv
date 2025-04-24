/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.instruments;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import lang.LanguageHandler;
import module.IInstrument;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;

/**
 *
 * @author Edward Jenkins
 */
public class InstrumentTools extends JPanel {

    // instance variables
    private int modType;
    private LanguageHandler languageHandler;
    // this panel
    private GridBagLayout toolsLayout;
    private GridBagConstraints tc;
    // basics
    private IInstrument selectedInstrument;
    // instrument details (file name, etc)
    private InstrumentDetails instrumentDetails;
    // sound options
    private InstrumentSoundOptions soundOptions;
    // sustain options
    private SustainOptions sustainOptions;
    // filter options
    private FilterOptions filterOptions;
    // random options
    private RandomOffsets randomOffsets;
    // MIDI options
    private MidiOptions midiOptions;
    // Note map view
    private NoteMapView noteMapView;

    public InstrumentTools(int modType, LanguageHandler languageHandler) {
        this.modType = modType;
        this.languageHandler = languageHandler;

        init();
    }

    // gettes
    public int getModType() {
        return modType;
    }

    public InstrumentDetails getInstrumentDetails() {
        return instrumentDetails;
    }

    public InstrumentSoundOptions getSoundOptions() {
        return soundOptions;
    }

    public SustainOptions getSustainOptions() {
        return sustainOptions;
    }

    public FilterOptions getFilterOptions() {
        return filterOptions;
    }

    public RandomOffsets getRandomOffsets() {
        return randomOffsets;
    }

    public MidiOptions getMidiOptions() {
        return midiOptions;
    }

    public NoteMapView getNoteMapView() {
        return noteMapView;
    }

    public void init() {
        
        // set layout
        toolsLayout = new GridBagLayout();
        tc = new GridBagConstraints();
        tc.anchor = GridBagConstraints.SOUTHWEST;
        tc.fill = GridBagConstraints.VERTICAL;
        this.setLayout(toolsLayout);

        // add innstrument details to tools
        instrumentDetails = new InstrumentDetails(modType, languageHandler);
        //tc.fill = GridBagConstraints.BOTH;
        tc.gridx = 0;
        tc.gridy = 0;
        this.add(instrumentDetails, tc);
        
        // add sustain options to tools
        sustainOptions = new SustainOptions(modType, languageHandler);
        tc.gridy++;
        tc.weighty = 1;
        tc.gridheight = GridBagConstraints.REMAINDER;
        this.add(sustainOptions, tc);
        
        // add MIDI options
        midiOptions = new MidiOptions(modType, languageHandler);
        tc.gridx = 1;
        tc.gridy = 0;
        tc.weighty = 0;
        tc.gridheight = 1;
        add(midiOptions, tc);
        
        // add instrument sound options to tools 
        soundOptions = new InstrumentSoundOptions(modType, languageHandler);
        tc.gridx = 1;
        tc.gridy = 1;
        tc.gridheight = GridBagConstraints.REMAINDER;
        add(soundOptions, tc);
        
        // add filter options to tools
        filterOptions = new FilterOptions(modType, languageHandler);
        tc.gridx++;
        tc.gridy = 0;
        tc.weighty = 0.0;
        tc.gridheight = 1;
        add(filterOptions, tc);
        
        // add random offsets to tools
        randomOffsets = new RandomOffsets(modType, languageHandler);
        tc.gridy = 1;
        tc.weighty = 1;
        tc.gridheight = GridBagConstraints.REMAINDER;
        add(randomOffsets, tc);
        
        // add note map view
        noteMapView = new NoteMapView(modType, languageHandler);
        tc.gridx++;
        tc.gridy = 0;
        tc.weightx = 1;
        tc.weighty = 1;
        tc.gridheight = GridBagConstraints.REMAINDER;
        add(noteMapView, tc);
        
    }
}
