/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.windows.replace;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_INSETS;

/**
 *
 * @author eddy6
 */
public class ReplaceInstrument extends JPanel {
    
    // instance variables
    private int modType;
    private Border replaceInstrumentBorder;
    private GridBagLayout replaceInstrumentLayout;
    private GridBagConstraints ric;
    private JLabel replaceInstrumentTypeLabel;
    private JRadioButton singleInstrumentRadioButton;
    private JRadioButton instrumentShiftRadioButton;
    private ButtonGroup instrumentShiftGroup;
    private JLabel singleInstrumentLabel;
    private JSpinner singleInstrumentSpinner;
    private JLabel shiftLabel;
    private JSpinner shiftSpinner;
    
    // constructor
    public ReplaceInstrument(int modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    public JRadioButton getSingleInstrumentRadioButton() {
        return singleInstrumentRadioButton;
    }

    public JRadioButton getInstrumentShiftRadioButton() {
        return instrumentShiftRadioButton;
    }

    public JSpinner getSingleInstrumentSpinner() {
        return singleInstrumentSpinner;
    }

    public JSpinner getShiftSpinner() {
        return shiftSpinner;
    }
    
    public void init() {
        // set the layout
        replaceInstrumentLayout = new GridBagLayout();
        setLayout(replaceInstrumentLayout);
        
        // setup the constrants
        ric = new GridBagConstraints();
        ric.anchor = GridBagConstraints.SOUTHWEST;
        //ric.fill = GridBagConstraints.BOTH;
        ric.insets = DEF_INSETS;

        // set the border
        replaceInstrumentBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        replaceInstrumentBorder
                = BorderFactory.createTitledBorder(replaceInstrumentBorder,
                        "Instrument: ", 0, 0, BOLD_FONT);

        // set options border
        setBorder(replaceInstrumentBorder);
        
        // instument selection type label
        replaceInstrumentTypeLabel = new JLabel("Replace instrument type: ");
        
        ric.gridx = 0;
        ric.gridwidth = GridBagConstraints.REMAINDER;
        ric.weightx = 1;
        ric.gridy = 0;
        ric.gridheight = 1;
        ric.weighty = 0;
        
        add(replaceInstrumentTypeLabel, ric);
        
        // single instrument radio button
        singleInstrumentRadioButton = new JRadioButton("Single");
        
        ric.gridy++;
        ric.gridwidth = 1;
        
        add(singleInstrumentRadioButton, ric);
        
        // instrument shift radio button
        instrumentShiftRadioButton = new JRadioButton("Shift instruments");
        
        ric.gridx++;
        ric.gridwidth = GridBagConstraints.REMAINDER;
        
        add(instrumentShiftRadioButton, ric);
            
        // button structure
        instrumentShiftGroup = new ButtonGroup();
        instrumentShiftGroup.add(singleInstrumentRadioButton);
        instrumentShiftGroup.add(instrumentShiftRadioButton);
        
        // first note label
        singleInstrumentLabel = new JLabel("New instrument: ");
        
        ric.gridx = 0;
        ric.gridwidth = GridBagConstraints.REMAINDER;
        ric.weightx = 1;
        ric.gridy++;
        
        add(singleInstrumentLabel, ric);
        
        // first note combo box
        singleInstrumentSpinner = new JSpinner();
        
        ric.gridy++;
        
        add(singleInstrumentSpinner, ric);
        
        // octave label
        shiftLabel = new JLabel("Instrument shift: ");
        
        ric.gridx = 0;
        ric.gridwidth = GridBagConstraints.REMAINDER;
        ric.weightx = 1;
        ric.gridy++;
        
        add(shiftLabel, ric);
        
        // octave box
        shiftSpinner = new JSpinner();
        
        ric.gridy++;
        
        add(shiftSpinner, ric);
        
        // add trailing empty JPanel
        ric.gridy++;
        ric.weighty = 1.0;
        ric.gridheight = GridBagConstraints.REMAINDER;
        
        add(new JPanel(), ric);
        
    }
}
