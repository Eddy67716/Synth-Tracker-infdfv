/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.instruments;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import static ui.UIProperties.CHECKBOX_INSETS;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.MEDIUM_FIELD_SIZE;
import static ui.UIProperties.SMALL_LABEL_SIZE;
import static ui.UIProperties.VALUE_SPINNER_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class FilterOptions extends JPanel {
    
    // cosntants
    public static final String[] FILTERS = {
        "IT filter", "4 pole cascade", "6 pole cascade", "8 pole cascade", 
        "10 pole cascade", "12 pole cascade", "14 pole cascade", 
        "16 pole cascade", "1000Hz band Sinc", 
        "900Hz band Sinc", "800Hz band Sinc", "700Hz band Sinc", 
        "600Hz band Sinc", "500Hz band Sinc", "400Hz band Sinc"
    };

    // instance variables
    private int modType;
    private GridBagLayout filterOptionsLayout;
    private GridBagConstraints foc;
    private Border filterOptionsBorder;
    private JLabel filterCutoffLabel;
    private JSpinner filterCutoffSpinner;               // it and str
    private SpinnerModel filterCutoffSpinnerModel;
    private JSlider filterCutoffSlider;
    private JLabel filterResonanceLabel;
    private JSpinner filterResonanceSpinner;            // it and str
    private SpinnerModel filterResonanceSpinnerModel;
    private JSlider filterResonanceSlider;
    private JLabel filterPitchLinkLabel;
    private JCheckBox filterPitchLink;                  // str
    private JLabel filterTypeLabel;
    private JComboBox filterTypeComboBox;               // str

    // constructor
    public FilterOptions(int modType) {
        this.modType = modType;
        init();
    }

    // getters
    public JSpinner getFilterCutoffSpinner() {
        return filterCutoffSpinner;
    }

    public JSlider getFilterCutoffSlider() {
        return filterCutoffSlider;
    }

    public JSpinner getFilterResonanceSpinner() {
        return filterResonanceSpinner;
    }

    public JSlider getFilterResonanceSlider() {
        return filterResonanceSlider;
    }

    public JCheckBox getFilterPitchLink() {
        return filterPitchLink;
    }

    public JComboBox getFilterTypeComboBox() {
        return filterTypeComboBox;
    }
    
    public void init() {

        // set the layout
        filterOptionsLayout = new GridBagLayout();
        setLayout(filterOptionsLayout);
        foc = new GridBagConstraints();
        foc.anchor = GridBagConstraints.SOUTHWEST;
        foc.insets = DEF_INSETS;

        // set the border
        filterOptionsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        filterOptionsBorder
                = BorderFactory.createTitledBorder(filterOptionsBorder,
                        "Filter options", 0, 0, BOLD_FONT);

        // add border
        setBorder(filterOptionsBorder);

        // set the filter cuttoff label
        filterCutoffLabel = new JLabel("Filter cutoff: ");
        filterCutoffLabel.setFont(DEF_FONT);
        filterCutoffLabel.setPreferredSize(MEDIUM_FIELD_SIZE);
        foc.gridx = 0;
        foc.gridy = 0;
        add(filterCutoffLabel, foc);

        // set filter cuttoff spinner model
        filterCutoffSpinnerModel = (modType == 6)
                ? new SpinnerNumberModel(255, 0, 255, 1)
                : new SpinnerNumberModel(127, 0, 127, 1);

        // set the filter cuttoff value spinner
        filterCutoffSpinner = new JSpinner(filterCutoffSpinnerModel);
        filterCutoffSpinner.setPreferredSize(VALUE_SPINNER_SIZE);
        foc.gridx = 1;
        foc.gridy = 0;
        add(filterCutoffSpinner, foc);

        // set the filter cuttoff slider
        filterCutoffSlider= (modType == 6) ? new JSlider(0, 255, 255)
                : new JSlider(0, 127, 127);
        foc.gridx = 2;
        foc.gridy = 0;
        foc.weightx = 1.0;
        foc.gridwidth = GridBagConstraints.REMAINDER;
        add(filterCutoffSlider, foc);

        // set the filter resonance label
        filterResonanceLabel = new JLabel("Filter resonance: ");
        filterResonanceLabel.setFont(DEF_FONT);
        filterResonanceLabel.setPreferredSize(MEDIUM_FIELD_SIZE);
        foc.gridx = 0;
        foc.gridy = 1;
        foc.weightx = 0.0;
        foc.gridwidth = 0;
        add(filterResonanceLabel, foc);

        // set filter resonance spinner model
        filterResonanceSpinnerModel = new SpinnerNumberModel(127, 0, 127, 1);

        // set the filter resonance value spinner
        filterResonanceSpinner = new JSpinner(filterResonanceSpinnerModel);
        filterResonanceSpinner.setPreferredSize(VALUE_SPINNER_SIZE);
        foc.gridx = 1;
        add(filterResonanceSpinner, foc);

        // set the filter resonance slider
        filterResonanceSlider = new JSlider(0, 127, 127);
        foc.gridx = 2;
        foc.weightx = 1.0;
        foc.gridwidth = GridBagConstraints.REMAINDER;
        add(filterResonanceSlider, foc);

        if (modType == 6) {
            
            // set the filter pitch link label
            filterPitchLinkLabel = new JLabel("Link to pitch: ");
            filterPitchLinkLabel.setFont(DEF_FONT);
            filterPitchLinkLabel.setPreferredSize(SMALL_LABEL_SIZE);
            foc.gridx = 0;
            foc.gridy++;
            foc.gridwidth = 0;
            foc.weightx = 0.0;
            add(filterPitchLinkLabel, foc);

            // set the use surround checkbox
            filterPitchLink = new JCheckBox();
            filterPitchLink.setSelected(false);
            foc.gridx = 2;
            foc.weightx = 1.0;
            foc.gridwidth = GridBagConstraints.REMAINDER;
            foc.insets = CHECKBOX_INSETS;
            add(filterPitchLink, foc);
            
            // set the filter type label
            filterTypeLabel = new JLabel("Filter type: ");
            filterTypeLabel.setFont(DEF_FONT);
            filterTypeLabel.setPreferredSize(SMALL_LABEL_SIZE);
            foc.gridx = 0;
            foc.gridy++;
            foc.gridwidth = 0;
            foc.weightx = 0.0;
            foc.insets = DEF_INSETS;
            add(filterTypeLabel, foc);
            
            // set the filter type check box
            filterTypeComboBox = new JComboBox(FILTERS);
            foc.gridx = 1;
            foc.gridwidth = 2;
            foc.weightx = 1;
            add(filterTypeComboBox, foc);
        }

        // JPanel at bottom
        foc.gridx = 0;
        foc.gridy++;
        foc.gridwidth = 3;
        foc.weighty = 1.0;
        foc.gridheight = GridBagConstraints.REMAINDER;
        add(new JPanel(), foc);
    }

    // events
    public void addFilterCutoffSpinnerChangeListener(ChangeListener changePerformed) {
        filterCutoffSpinner.addChangeListener(changePerformed);
    }
    
    public void addFilterCutoffSliderChangeListener(ChangeListener changePerformed) {
        filterCutoffSlider.addChangeListener(changePerformed);
    }
    
    public void addFilterResSpinnerChangeListener(ChangeListener changePerformed) {
        filterResonanceSpinner.addChangeListener(changePerformed);
    }
    
    public void addFilterResSliderChangeListener(ChangeListener changePerformed) {
        filterResonanceSlider.addChangeListener(changePerformed);
    }
    
    public void addFilterPitchLinkCheckboxChangeListener(ChangeListener changePerformed) {
        filterPitchLink.addChangeListener(changePerformed);
    }
}
