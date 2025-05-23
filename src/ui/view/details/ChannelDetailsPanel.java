/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.details;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import lang.LanguageHandler;

/**
 *
 * @author Edward Jenkins
 */
public class ChannelDetailsPanel extends JPanel {
    
    // instance variables
    private int modType;
    private final LanguageHandler languageHandler;
    private int channelCount;
    private GridBagLayout channelsPanelLayout;
    private GridBagConstraints cpc;
    private ChannelPanel[] channelPanels;
    
    public ChannelDetailsPanel(int modType, LanguageHandler languageHandler, 
            int channels) {
        this.modType = modType;
        this.languageHandler = languageHandler;
        this.channelCount = channels;
        init();
    }

    public int getChannelCount() {
        return channelCount;
    }

    public ChannelPanel[] getChannelPanels() {
        return channelPanels;
    }
    
    public void init() {
        
        channelsPanelLayout = new GridBagLayout();
        
        cpc = new GridBagConstraints();
        cpc.anchor = GridBagConstraints.WEST;
        
        setLayout(channelsPanelLayout);
        
        // create channels
        channelPanels = new ChannelPanel[channelCount];
        
        for (int i = 0; i < channelPanels.length; i++) {
            channelPanels[i] = new ChannelPanel(modType, languageHandler, i + 1);
        }
        
        // populate channels panel
        cpc.gridx = 0;
        cpc.gridy = 0;
        cpc.weighty = 1;
        cpc.gridheight = GridBagConstraints.REMAINDER;
        
        int i = 0;
        
        for (ChannelPanel channelPanel : channelPanels) {
            add(channelPanel, cpc);
            if (i < channelCount - 1) {
                cpc.gridx++;
                i++;
            } else {
                cpc.weightx = 1.0;
                cpc.gridwidth = GridBagConstraints.REMAINDER;
                add(new JPanel(), cpc);
            }
        }
    }
}
