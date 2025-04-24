/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.samples;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.VALUE_SPINNER_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class VibratoOptions extends JPanel {

    // constnats
    public static final String[] XM_IT_VIBRATO_WAVEFORMS
            = {"Sine wave", "Down Sawtooth", "Square", "Random"};
    public static final String[] STR_VIBRATO_WAVEFORMS
            = {"Sine wave", "Down Sawtooth", "Square", "Random", "Triangle",
                "Up Sawtooth"};

    // instnace variables
    private int modType;
    private LanguageHandler languageHandler;
    private GridBagLayout vibOptionsLayout;
    private GridBagConstraints voc;
    private Border vibOptionsBorder;
    private JLabel vibSpeedLabel;
    private JSpinner vibSpeedSpinner;                   // xm, it and str
    private SpinnerModel vibSpeedSpinnerModel;
    private JLabel vibDepthLabel;
    private JSpinner vibDepthSpinner;                   // xm, it and str
    private SpinnerModel vibDepthSpinnerModel;
    private JLabel vibDelayLabel;
    private JSpinner vibDelaySpinner;                   // xm, it and str
    private SpinnerModel vibDelaySpinnerModel;
    private JLabel vibRateLabel;
    private JComboBox vibRateComboBox;                  // str
    private JPanel vibSubPanel;
    private GridBagLayout vibSubPanelLayout;
    private GridBagConstraints vsc;
    private JLabel vibWaveformLabel;
    private JComboBox vibWaveformComboBox;              // xm, it and str
    private String[] vibratoWaveforms;
    private JLabel sineIcon;
    private JLabel squareIcon;
    private JLabel fallingSawtoothIcon;
    private JLabel randomIcon;
    private JLabel triangleIcon;
    private JLabel risingSawtoothIcon;

    // constructor
    public VibratoOptions(int modType, LanguageHandler languageHandler) {
        this.modType = modType;
        this.languageHandler = languageHandler;
        init();
    }

    // getters
    public JSpinner getVibSpeedSpinner() {
        return vibSpeedSpinner;
    }

    public JSpinner getVibDepthSpinner() {
        return vibDepthSpinner;
    }

    public JSpinner getVibDelaySpinner() {
        return vibDelaySpinner;
    }

    public JComboBox getVibWaveformComboBox() {
        return vibWaveformComboBox;
    }

    private void init() {

        vibOptionsLayout = new GridBagLayout();
        setLayout(vibOptionsLayout);
        this.voc = new GridBagConstraints();
        voc.anchor = GridBagConstraints.SOUTHWEST;
        //voc.fill = GridBagConstraints.BOTH;
        voc.insets = DEF_INSETS;

        // set the border
        vibOptionsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        vibOptionsBorder
                = BorderFactory.createTitledBorder(vibOptionsBorder,
                        languageHandler
                                .getLanguageText("sample.vibrato_options"), 0,
                        0, BOLD_FONT);

        // set vibrato options border
        setBorder(vibOptionsBorder);

        // set vibrato speed label
        vibSpeedLabel = new JLabel(languageHandler
                .getLanguageText("sample.vibrato_options.speed"));
        vibSpeedLabel.setFont(DEF_FONT);
        voc.gridx = 0;
        voc.gridy = 0;
        add(vibSpeedLabel, voc);

        // set vibrato speed spinner model
        vibSpeedSpinnerModel = new SpinnerNumberModel(0, 0, 64, 1);

        // set vibrato speed spinner
        vibSpeedSpinner = new JSpinner(vibSpeedSpinnerModel);
        vibSpeedSpinner.setPreferredSize(VALUE_SPINNER_SIZE);
        voc.gridx = 1;
        add(vibSpeedSpinner, voc);

        // set vibrato depth label
        vibDepthLabel = new JLabel(languageHandler
                .getLanguageText("sample.vibrato_options.depth"));
        vibDepthLabel.setFont(DEF_FONT);
        voc.gridx = 0;
        voc.gridy++;
        add(vibDepthLabel, voc);

        // set vibrato depth spinner model
        vibDepthSpinnerModel = new SpinnerNumberModel(0, 0, 32, 1);

        // set vibrato depth spinner
        vibDepthSpinner = new JSpinner(vibDepthSpinnerModel);
        vibDepthSpinner.setPreferredSize(VALUE_SPINNER_SIZE);
        voc.gridx = 1;
        add(vibDepthSpinner, voc);

        // set vibrato delay label
        vibDelayLabel = new JLabel(languageHandler
                .getLanguageText("sample.vibrato_options.delay"));
        vibDelayLabel.setFont(DEF_FONT);
        voc.gridx = 0;
        voc.gridy++;
        add(vibDelayLabel, voc);

        // set vibrato delay spinner model
        vibDelaySpinnerModel = new SpinnerNumberModel(0, 0, 255, 1);

        // set vibrato delay spinner
        vibDelaySpinner = new JSpinner(vibDelaySpinnerModel);
        vibDelaySpinner.setPreferredSize(VALUE_SPINNER_SIZE);
        voc.gridx = 1;
        add(vibDelaySpinner, voc);

        // set icons
        sineIcon = new JLabel(new ImageIcon("/ui/assets/Sine.png"));

        sineIcon.setToolTipText("Sine wave");

        fallingSawtoothIcon = new JLabel(new ImageIcon(getClass()
                .getResource(
                        "/ui/assets/Falling Ramp Sawtooth.png")));

        fallingSawtoothIcon.setToolTipText("Falling sawtooth");

        squareIcon = new JLabel(new ImageIcon(getClass()
                .getResource("/ui/assets/Square.png")));

        squareIcon.setToolTipText("Square wave");

        randomIcon = new JLabel(new ImageIcon(getClass()
                .getResource("/ui/assets/Random.png")));

        randomIcon.setToolTipText("Random");

        String[] choices;

        // set comboBox items
        switch (modType) {
            default:
                vibratoWaveforms = new String[]{
                    languageHandler.getLanguageText("wave.sine"),
                    languageHandler.getLanguageText("wave.square"),
                    languageHandler.getLanguageText("wave.ramp_up"),
                    languageHandler.getLanguageText("wave.ramp_down")
                };
                break;
            case 4:
            case 5:
                vibratoWaveforms = new String[]{
                    languageHandler.getLanguageText("wave.sine"),
                    languageHandler.getLanguageText("wave.square"),
                    languageHandler.getLanguageText("wave.ramp_down"),
                    languageHandler.getLanguageText("wave.random")
                };
                break;
            case 6:
                vibratoWaveforms = new String[]{
                    languageHandler.getLanguageText("wave.sine"),
                    languageHandler.getLanguageText("wave.square"),
                    languageHandler.getLanguageText("wave.ramp_down"),
                    languageHandler.getLanguageText("wave.random"),
                    languageHandler.getLanguageText("wave.ramp_up"),
                    languageHandler.getLanguageText("wave.triangle")
                };
                break;
        }
        
        choices = vibratoWaveforms;

        // set label
        vibWaveformLabel = new JLabel(languageHandler
                .getLanguageText("sample.vibrato_options.wave"));
        vibWaveformLabel.setFont(DEF_FONT);

        // set comboBox
        vibWaveformComboBox = new JComboBox(choices);

        // set a 1 by 3 area for subpanel
        voc.gridx = 3;
        voc.gridy = 0;
        voc.gridwidth = 1;
        voc.gridheight = 3;
        voc.anchor = GridBagConstraints.NORTHWEST;

        // set subPanelLayout
        vibSubPanelLayout = new GridBagLayout();

        // set subPanel
        vibSubPanel = new JPanel(vibSubPanelLayout);

        // set up subPanel grid bag constraints;
        vsc = new GridBagConstraints();
        vsc.anchor = GridBagConstraints.SOUTHWEST;
        vsc.insets = DEF_INSETS;

        // add vibrato waveform label
        vsc.gridx = 0;
        vsc.gridy = 0;
        vibSubPanel.add(vibWaveformLabel, vsc);

        // add vibrato waveform combo box
        vsc.gridy = 1;
        vibSubPanel.add(vibWaveformComboBox, vsc);

        // add extra JPanel for aligment
        vsc.gridy = 2;
        vsc.gridwidth = 2;
        vsc.weighty = 1;

        vibSubPanel.add(new JPanel(), vsc);

        // add panel to vibrato options
        add(vibSubPanel, voc);

        // set bottom panel
        voc.gridx = 0;
        voc.gridy = 4;
        voc.weightx = 1;
        voc.weighty = 1;
        voc.gridwidth = GridBagConstraints.REMAINDER;
        voc.gridheight = GridBagConstraints.REMAINDER;
        add(new JPanel(), voc);
    }

    // events and listeners
    public void addVibSpeedSpinnerChangeListener(
            ChangeListener changePerformed) {
        vibSpeedSpinner.addChangeListener(changePerformed);
    }

    public void addVibDepthSpinnerChangeListener(
            ChangeListener changePerformed) {
        vibDepthSpinner.addChangeListener(changePerformed);
    }

    public void addVibDelaySpinnerChangeListener(
            ChangeListener changePerformed) {
        vibDelaySpinner.addChangeListener(changePerformed);
    }

    public void addVibWaveformComboBoxActionListenr(
            ActionListener actionPerformed) {
        this.vibWaveformComboBox.addActionListener(actionPerformed);
    }
}
