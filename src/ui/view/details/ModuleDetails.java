/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.details;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.ITALIC_FONT;
import static ui.UIProperties.LARGE_FIELD_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class ModuleDetails extends JPanel {

    // instance variables
    private int modType;
    private GridBagLayout detailsLayout;
    private GridBagConstraints dc;
    private Border detailsBorder;
    // all
    private JLabel songNameLabel;
    private JTextField songNameField;
    // STR and OpenMPT files
    private JLabel songAuthorLabel;
    private JTextField songAuthorField;
    // s3m and onwards
    private JLabel createdVersionTitleLabel;
    private JLabel createdVersionLabel;
    // IT and STR
    private JLabel compatibleVersionTitleLabel;
    private JLabel ccompatibleVersionLabel;

    // constructor
    public ModuleDetails(int modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    public JTextField getSongNameField() {
        return songNameField;
    }

    public JTextField getSongAuthorField() {
        return songAuthorField;
    }

    public JLabel getCreatedVersionLabel() {
        return createdVersionLabel;
    }

    public JLabel getCcompatibleVersionLabel() {
        return ccompatibleVersionLabel;
    }

    public void init() {

        // set the layout
        detailsLayout = new GridBagLayout();
        setLayout(detailsLayout);
        dc = new GridBagConstraints();
        dc.anchor = GridBagConstraints.SOUTHWEST;
        dc.insets = DEF_INSETS;

        // set the border
        detailsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        detailsBorder
                = BorderFactory.createTitledBorder(detailsBorder,
                        "Module details", 0, 0, BOLD_FONT);

        // set options border
        setBorder(detailsBorder);

        // song name label
        songNameLabel = new JLabel("Song name: ");
        songNameLabel.setFont(DEF_FONT);

        dc.gridx = 0;
        dc.gridy = 0;
        dc.weightx = 1;
        dc.gridwidth = GridBagConstraints.REMAINDER;
        add(songNameLabel, dc);

        // song name field
        songNameField = new JTextField();
        songNameField.setToolTipText("Name of song");
        songNameField.setPreferredSize(LARGE_FIELD_SIZE);

        dc.fill = GridBagConstraints.HORIZONTAL;
        dc.gridx = 0;
        dc.gridy++;
        add(songNameField, dc);

        // song author label
        songAuthorLabel = new JLabel("Song author: ");
        songAuthorLabel.setFont(DEF_FONT);

        dc.gridx = 0;
        dc.gridy++;
        add(songAuthorLabel, dc);

        // song author field
        songAuthorField = new JTextField();
        songAuthorField.setToolTipText("Writer of song");
        songAuthorField.setPreferredSize(LARGE_FIELD_SIZE);

        dc.fill = GridBagConstraints.HORIZONTAL;
        dc.gridx = 0;
        dc.gridy++;
        add(songAuthorField, dc);

        // created version title label
        createdVersionTitleLabel = new JLabel("Created version: ");
        createdVersionTitleLabel.setFont(DEF_FONT);

        dc.gridx = 0;
        dc.gridy++;
        dc.weightx = 0;
        dc.gridwidth = 1;
        add(createdVersionTitleLabel, dc);

        // created version field
        createdVersionLabel = new JLabel();
        createdVersionLabel.setText("1.0");
        createdVersionLabel.setToolTipText("Version number");
        createdVersionLabel.setFont(ITALIC_FONT);

        dc.fill = GridBagConstraints.HORIZONTAL;
        dc.gridx = 1;
        dc.weightx = 1.0;
        dc.gridwidth = GridBagConstraints.REMAINDER;
        add(createdVersionLabel, dc);

        if (modType >= 4) {
            
            // compatible version title label
            compatibleVersionTitleLabel = new JLabel("Compatible version: ");
            compatibleVersionTitleLabel.setFont(DEF_FONT);

            dc.gridx = 0;
            dc.gridy++;
            dc.weightx = 0;
            dc.gridwidth = 1;
            add(compatibleVersionTitleLabel, dc);

            // compatible version field
            ccompatibleVersionLabel = new JLabel();
            ccompatibleVersionLabel.setText("1.0");
            ccompatibleVersionLabel.setToolTipText("Version number");
            ccompatibleVersionLabel.setFont(ITALIC_FONT);

            dc.fill = GridBagConstraints.HORIZONTAL;
            dc.gridx = 1;
            dc.weightx = 1.0;
            dc.gridwidth = GridBagConstraints.REMAINDER;
            add(ccompatibleVersionLabel, dc);
        }

        // add trailing JPanel
        dc.gridx = 0;
        dc.gridy++;
        dc.weighty = 1.0;
        dc.gridheight = GridBagConstraints.REMAINDER;

        add(new JPanel(), dc);
    }
}
