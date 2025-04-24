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
import lang.LanguageHandler;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.C5_SPINNER_SIZE;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;

/**
 *
 * @author Edward Jenkins
 */
public class SustainLoopTools extends JPanel {

    // constants
    // instance variables
    private int modType;
    private LanguageHandler languageHandler;
    private final String[] loopOptions;
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

    // constructor
    public SustainLoopTools(int modType, LanguageHandler languageHandler) {
        this.modType = modType;
        this.languageHandler = languageHandler;
        loopOptions = new String[]{
            languageHandler.getLanguageText("sample.loop_tools.off"),
            languageHandler.getLanguageText("sample.loop_tools.forwards"),
            languageHandler.getLanguageText("sample.loop_tools.bidi"),};
        init();
    }

    // getters
    public JComboBox getSusLoopComboBox() {
        return susLoopComboBox;
    }

    public JSpinner getSusLoopStartSpinner() {
        return susLoopStartSpinner;
    }

    public JSpinner getSusLoopEndSpinner() {
        return susLoopEndSpinner;
    }

    private void init() {

        // set the layout
        susLoopToolsLayout = new GridBagLayout();
        setLayout(susLoopToolsLayout);
        slc = new GridBagConstraints();
        slc.anchor = GridBagConstraints.SOUTHWEST;
        //slc.fill = GridBagConstraints.BOTH;
        slc.insets = DEF_INSETS;

        // set the border
        susLoopToolsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        susLoopToolsBorder
                = BorderFactory.createTitledBorder(susLoopToolsBorder,
                        languageHandler
                                .getLanguageText("sample.sustain_loop_tools"),
                        0, 0, BOLD_FONT);

        // set loop tools border
        setBorder(susLoopToolsBorder);

        // set loop title label
        susLoopTitleLabel = new JLabel(languageHandler
                .getLanguageText("sample.loop_tools.loop"));
        susLoopTitleLabel.setFont(DEF_FONT);
        slc.gridx = 0;
        slc.gridy = 0;
        add(susLoopTitleLabel, slc);

        // set loop combo box
        susLoopComboBox = new JComboBox(loopOptions);
        susLoopComboBox.setSelectedIndex(0);
        slc.gridx = 1;
        slc.gridy = 0;
        slc.weightx = 1;
        slc.gridwidth = GridBagConstraints.REMAINDER;
        add(susLoopComboBox, slc);

        // set loop start title
        susLoopStartTitleLabel = new JLabel(languageHandler
                .getLanguageText("sample.loop_tools.start"));
        susLoopStartTitleLabel.setFont(DEF_FONT);
        slc.gridx = 0;
        slc.gridy = 1;
        slc.weightx = 0;
        slc.gridwidth = 0;
        add(susLoopStartTitleLabel, slc);

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
        add(susLoopStartSpinner, slc);

        // set loop end title
        susLoopEndTitleLabel = new JLabel(languageHandler
                .getLanguageText("sample.loop_tools.end"));
        susLoopEndTitleLabel.setFont(DEF_FONT);
        slc.gridx = 0;
        slc.gridy = 2;
        slc.weightx = 0;
        slc.gridwidth = 0;
        add(susLoopEndTitleLabel, slc);

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
        add(susLoopEndSpinner, slc);

        // add pannel to bottom column
        slc.gridx = 0;
        slc.gridy = 3;
        slc.weightx = 2;
        slc.weighty = 1;
        slc.gridwidth = GridBagConstraints.REMAINDER;
        slc.gridheight = GridBagConstraints.REMAINDER;

        add(new JPanel(), slc);
    }

    // events and listeners
    public void addSusLoopComboBoxActionListener(
            ActionListener actionPerformed) {
        susLoopComboBox.addActionListener(actionPerformed);
    }

    public void addSusLoopStartSpinnerChangeListener(
            ChangeListener changePerformed) {
        susLoopStartSpinner.addChangeListener(changePerformed);
    }

    public void addSusLoopEndSpinnerChangeListener(
            ChangeListener changePerformed) {
        susLoopEndSpinner.addChangeListener(changePerformed);
    }
}
