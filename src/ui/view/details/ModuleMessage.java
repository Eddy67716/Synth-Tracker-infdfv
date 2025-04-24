/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.details;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import javax.swing.border.Border;
import lang.LanguageHandler;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.ITALIC_FONT;

/**
 *
 * @author Edward Jenkins
 */
public class ModuleMessage extends JPanel {

    // instance variables
    private int modType;
    private final LanguageHandler languageHandler;
    private GridBagLayout messageLayout;
    private GridBagConstraints mmc;
    private Border messageBorder;
    // IT and STR
    private JLabel messageLabel;
    private JScrollPane messagePane;
    private JTextArea messageArea;

    public ModuleMessage(int modType, LanguageHandler languageHandler) {
        this.modType = modType;
        this.languageHandler = languageHandler;
        init();
    }

    // getters
    public JTextArea getMessageArea() {
        return messageArea;
    }

    public void init() {

        // set the layout
        messageLayout = new GridBagLayout();
        setLayout(messageLayout);
        mmc = new GridBagConstraints();
        mmc.anchor = GridBagConstraints.SOUTHWEST;
        mmc.insets = DEF_INSETS;

        // set the border
        messageBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        messageBorder
                = BorderFactory.createTitledBorder(messageBorder,
                        languageHandler.getLanguageText("module.options"), 
                        0, 0, BOLD_FONT);

        // set options border
        setBorder(messageBorder);

        // message label
        messageLabel = new JLabel(languageHandler
                .getLanguageText("module.message.contained"));
        messageLabel.setFont(DEF_FONT);

        mmc.gridx = 0;
        mmc.gridy = 0;
        mmc.weighty = 0;
        mmc.weightx = 1;
        mmc.gridwidth = GridBagConstraints.REMAINDER;
        add(messageLabel, mmc);

        // message area
        messageArea = new JTextArea(languageHandler
                .getLanguageText("module.message.placeholder"));
        messageArea.setFont(ITALIC_FONT);

        messagePane = new JScrollPane(messageArea, VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // set size of message pane to prevent oversizing
        messagePane.setPreferredSize(new Dimension(300, 150));

        mmc.fill = GridBagConstraints.BOTH;
        mmc.gridy++;
        add(messagePane, mmc);

        // add trailing JPanels
        mmc.gridx = 0;
        mmc.gridy++;
        mmc.weighty = 1.0;
        mmc.gridheight = GridBagConstraints.REMAINDER;

        add(new JPanel(), mmc);
    }

    // events and listeners
    public void addMessageAreaKeyListner(KeyListener keyPerformed) {
        messageArea.addKeyListener(keyPerformed);
    }
}
