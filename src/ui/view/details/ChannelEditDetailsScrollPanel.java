/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.details;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;
import lang.LanguageHandler;

/**
 *
 * @author Edward Jenkins
 */
public class ChannelEditDetailsScrollPanel extends JPanel {
    
    // instance variables
    private int modType;
    private final LanguageHandler languageHandler;
    private int channelCount;
    private JScrollPane scrollPane;
    private ChannelDetailsPanel channelsPanel;
    private EditHistoryPanel editHistoryPanel;
    
    public ChannelEditDetailsScrollPanel(int modType, LanguageHandler languageHandler, int channels) {
        super();
        this.modType = modType;
        this.languageHandler = languageHandler;
        this.channelCount = channels;
        init();
    }

    public ChannelDetailsPanel getChannelsPanel() {
        return channelsPanel;
    }

    public EditHistoryPanel getEditHistoryPanel() {
        return editHistoryPanel;
    }
    
    public void init() {
        
        channelsPanel = new ChannelDetailsPanel(modType, languageHandler, 
                channelCount);
        
        setLayout(new BorderLayout());
        
        scrollPane = new JScrollPane(channelsPanel, VERTICAL_SCROLLBAR_NEVER, 
                HORIZONTAL_SCROLLBAR_ALWAYS);
        
        add(scrollPane, BorderLayout.NORTH);
        
        editHistoryPanel = new EditHistoryPanel(modType);
        
        add(editHistoryPanel, BorderLayout.CENTER);
    }
}
