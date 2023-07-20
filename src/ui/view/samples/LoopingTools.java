/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.samples;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
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
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.C5_SPINNER_SIZE;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;

/**
 *
 * @author Edward Jenkins
 */
public class LoopingTools extends JPanel {
    
    // constants
    private static final String[] LOOP_OPTIONS
            = {"Off", "Forward", "Ping-Pong"};
    
    // instance variables
    private int modType;
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
    
    // constructor
    public LoopingTools(int modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    public JComboBox getLoopComboBox() {
        return loopComboBox;
    }

    public JSpinner getLoopStartSpinner() {
        return loopStartSpinner;
    }

    public JSpinner getLoopEndSpinner() {
        return loopEndSpinner;
    }
    
    private void init() {
        
        // set the layout
        loopingToolsLayout = new GridBagLayout();
        setLayout(loopingToolsLayout);
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

        // set loop tools border
        setBorder(loopingToolsBorder);

        // set loop title label
        loopTitleLabel = new JLabel("Loop");
        loopTitleLabel.setFont(DEF_FONT);
        lc.gridx = 0;
        lc.gridy = 0;
        add(loopTitleLabel, lc);

        // set loop combo box
        loopComboBox = new JComboBox(LOOP_OPTIONS);
        loopComboBox.setSelectedIndex(0);
        lc.gridx = 1;
        lc.gridy = 0;
        lc.weightx = 1;
        lc.gridwidth = GridBagConstraints.REMAINDER;
        add(loopComboBox, lc);

        // set loop start title
        loopStartTitleLabel = new JLabel("Start: ");
        loopStartTitleLabel.setFont(DEF_FONT);
        lc.gridx = 0;
        lc.gridy = 1;
        lc.weightx = 0;
        lc.gridwidth = 0;
        add(loopStartTitleLabel, lc);

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
        add(loopStartSpinner, lc);

        // set loop end title
        loopEndTitleLabel = new JLabel("End: ");
        loopEndTitleLabel.setFont(DEF_FONT);
        lc.gridx = 0;
        lc.gridy = 2;
        lc.weightx = 0;
        lc.gridwidth = 0;
        add(loopEndTitleLabel, lc);

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
        add(loopEndSpinner, lc);

        // add pannel to bottom column
        lc.gridx = 0;
        lc.gridy = 3;
        lc.weightx = 2;
        lc.weighty = 1;
        lc.gridwidth = GridBagConstraints.REMAINDER;
        lc.gridheight = GridBagConstraints.REMAINDER;

        add(new JPanel(), lc);
    }
    
    // events and listeners
    public void addLoopComboBoxActionListener(ActionListener actionPerformed) {
        loopComboBox.addActionListener(actionPerformed);
    }
    
    public void addLoopStartSpinnerChangeListener(ChangeListener changePerformed) {
        loopStartSpinner.addChangeListener(changePerformed);
    }
    
    public void addLoopEndSpinnerChangeListener(ChangeListener changePerformed) {
        loopEndSpinner.addChangeListener(changePerformed);
    }
}
