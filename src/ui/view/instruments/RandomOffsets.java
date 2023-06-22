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
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.MEDIUM_FIELD_SIZE;
import static ui.UIProperties.VALUE_SPINNER_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class RandomOffsets extends JPanel {

    // instance variables
    private int modType;
    private GridBagLayout randomOffsetsLayout;
    private GridBagConstraints roc;
    private Border randomOffsetsBorder;
    private JLabel randomVolumeLabel;
    private JSpinner randomVolumeSpinner;               // it and str
    private SpinnerModel randomVolumeSpinnerModel;
    private JSlider randomVolumeSlider;
    private JLabel randomPanningLabel;
    private JSpinner randomPanningSpinner;              // it and str
    private SpinnerModel randomPanningSpinnerModel;
    private JSlider randomPanningSlider;
    private JLabel randomCutoffLabel;
    private JSpinner randomCutoffSpinner;               // str
    private SpinnerModel randomCutoffSpinnerModel;
    private JSlider randomCutoffSlider;
    private JLabel randomResonanceLabel;
    private JSpinner randomResonanceSpinner;            // str
    private SpinnerModel randomResonanceSpinnerModel;
    private JSlider randomResonanceSlider;

    // constructor
    public RandomOffsets(int modType) {
        this.modType = modType;
        init();
    }

    // getters
    public JSpinner getRandomVolumeSpinner() {
        return randomVolumeSpinner;
    }

    public JSlider getRandomVolumeSlider() {
        return randomVolumeSlider;
    }

    public JSpinner getRandomPanningSpinner() {
        return randomPanningSpinner;
    }

    public JSlider getRandomPanningSlider() {
        return randomPanningSlider;
    }

    public JSpinner getRandomCutoffSpinner() {
        return randomCutoffSpinner;
    }

    public JSlider getRandomCutoffSlider() {
        return randomCutoffSlider;
    }

    public JSpinner getRandomResonanceSpinner() {
        return randomResonanceSpinner;
    }

    public JSlider getRandomResonanceSlider() {
        return randomResonanceSlider;
    }
    
    
    public void init() {

        // set the layout
        randomOffsetsLayout = new GridBagLayout();
        setLayout(randomOffsetsLayout);
        roc = new GridBagConstraints();
        roc.anchor = GridBagConstraints.SOUTHWEST;
        roc.insets = DEF_INSETS;

        // set the border
        randomOffsetsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        randomOffsetsBorder
                = BorderFactory.createTitledBorder(randomOffsetsBorder,
                        "Random offsets", 0, 0, BOLD_FONT);

        // set options border
        setBorder(randomOffsetsBorder);

        // set the random volume label
        randomVolumeLabel = new JLabel("Random volume: ");
        randomVolumeLabel.setFont(DEF_FONT);
        randomVolumeLabel.setPreferredSize(MEDIUM_FIELD_SIZE);
        roc.gridx = 0;
        roc.gridy = 0;
        add(randomVolumeLabel, roc);

        // set random volume spinner model
        randomVolumeSpinnerModel = new SpinnerNumberModel(0, 0, 100, 1);

        // set the random volume value spinner
        randomVolumeSpinner = new JSpinner(randomVolumeSpinnerModel);
        randomVolumeSpinner.setPreferredSize(VALUE_SPINNER_SIZE);
        roc.gridx = 1;
        roc.gridy = 0;
        add(randomVolumeSpinner, roc);

        // set the random volume slider
        randomVolumeSlider = new JSlider(0, 100, 0);
        roc.gridx = 2;
        roc.gridy = 0;
        roc.weightx = 1.0;
        roc.gridwidth = GridBagConstraints.REMAINDER;
        add(randomVolumeSlider, roc);

        // set the random panning label
        randomPanningLabel = new JLabel("Random panning: ");
        randomPanningLabel.setFont(DEF_FONT);
        randomPanningLabel.setPreferredSize(MEDIUM_FIELD_SIZE);
        roc.gridx = 0;
        roc.gridy++;
        roc.gridwidth = 0;
        roc.weightx = 0.0;
        add(randomPanningLabel, roc);

        // set random panning spinner model
        randomPanningSpinnerModel = new SpinnerNumberModel(0, 0, 100, 1);

        // set the random panning value spinner
        randomPanningSpinner = new JSpinner(randomPanningSpinnerModel);
        randomPanningSpinner.setPreferredSize(VALUE_SPINNER_SIZE);
        roc.gridx = 1;
        add(randomPanningSpinner, roc);

        // set the random panning slider
        randomPanningSlider = new JSlider(0, 100, 0);
        roc.gridx = 2;
        roc.weightx = 1.0;
        roc.gridwidth = GridBagConstraints.REMAINDER;
        add(randomPanningSlider, roc);

        if (modType >= 5) {

            // set the random cutoff label
            randomCutoffLabel = new JLabel("Random cutoff: ");
            randomCutoffLabel.setFont(DEF_FONT);
            randomCutoffLabel.setPreferredSize(MEDIUM_FIELD_SIZE);
            roc.gridx = 0;
            roc.gridy++;
            roc.gridwidth = 0;
            roc.weightx = 0.0;
            add(randomCutoffLabel, roc);

            // set random cutoff spinner model
            randomCutoffSpinnerModel = new SpinnerNumberModel(0, 0, 100, 1);

            // set the random cutoff value spinner
            randomCutoffSpinner = new JSpinner(randomCutoffSpinnerModel);
            randomCutoffSpinner.setPreferredSize(VALUE_SPINNER_SIZE);
            roc.gridx = 1;
            add(randomCutoffSpinner, roc);

            // set the random cutoff slider
            randomCutoffSlider = new JSlider(0, 100, 0);
            roc.gridx = 2;
            roc.weightx = 1.0;
            roc.gridwidth = GridBagConstraints.REMAINDER;
            add(randomCutoffSlider, roc);
            
            // set the random resonance label
            randomResonanceLabel = new JLabel("Random resonance: ");
            randomResonanceLabel.setFont(DEF_FONT);
            randomResonanceLabel.setPreferredSize(MEDIUM_FIELD_SIZE);
            roc.gridx = 0;
            roc.gridy++;
            roc.gridwidth = 0;
            roc.weightx = 0.0;
            add(randomResonanceLabel, roc);

            // set random resonance spinner model
            randomResonanceSpinnerModel = new SpinnerNumberModel(0, 0, 100, 1);

            // set the random resonance value spinner
            randomResonanceSpinner = new JSpinner(randomResonanceSpinnerModel);
            randomResonanceSpinner.setPreferredSize(VALUE_SPINNER_SIZE);
            roc.gridx = 1;
            add(randomResonanceSpinner, roc);

            // set the random resonance slider
            randomResonanceSlider = new JSlider(0, 100, 0);
            roc.gridx = 2;
            roc.weightx = 1.0;
            roc.gridwidth = GridBagConstraints.REMAINDER;
            add(randomResonanceSlider, roc);
        }

        // add pannel to bottom column
        roc.gridx = 0;
        roc.gridy = 6;
        roc.weightx = 2;
        roc.weighty = 1;
        roc.gridwidth = GridBagConstraints.REMAINDER;
        roc.gridheight = GridBagConstraints.REMAINDER;
        add(new JPanel(), roc);
    }
    
    // change listeners
    public void addRandomVolSpinnerChangeListener(ChangeListener changePerformed) {
        randomVolumeSpinner.addChangeListener(changePerformed);
    }

    public void addRandomVolSliderChangeListener(ChangeListener changePerformed) {
        randomVolumeSlider.addChangeListener(changePerformed);
    }
    
    public void addRandomPanSpinnerChangeListener(ChangeListener changePerformed) {
        randomPanningSpinner.addChangeListener(changePerformed);
    }

    public void addRandomPanSliderChangeListener(ChangeListener changePerformed) {
        randomPanningSlider.addChangeListener(changePerformed);
    }
    
    public void addRandomCutoffSpinnerChangeListener(ChangeListener changePerformed) {
        randomCutoffSpinner.addChangeListener(changePerformed);
    }

    public void addRandomCutoffSliderChangeListener(ChangeListener changePerformed) {
        randomCutoffSlider.addChangeListener(changePerformed);
    }
    
    public void addRandomResSpinnerChangeListener(ChangeListener changePerformed) {
        randomResonanceSpinner.addChangeListener(changePerformed);
    }

    public void addRandomResSliderChangeListener(ChangeListener changePerformed) {
        randomResonanceSlider.addChangeListener(changePerformed);
    }
}
