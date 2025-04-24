/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.details;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import lang.LanguageHandler;
import module.IModuleHeader;

/**
 *
 * @author Edward Jenkins
 */
public class DetailsUI extends JPanel {
    
    // instance variables
    private int modType;
    private LanguageHandler languageHandler;
    private IModuleHeader moduleHeader;
    private int channelCount;
    private BorderLayout channelPanelLayout;
    private ModuleTools moduleTools;
    private ChannelEditDetailsScrollPanel channelsPanel;
    
    // constructor
    public DetailsUI(IModuleHeader moduleHeader, int modType, 
            LanguageHandler languageHandler) {
        this.languageHandler = languageHandler;
        this.moduleHeader = moduleHeader;
        this.modType = modType;
        this.channelCount = moduleHeader.getChannelCount();
        init();
    }
    
    // getters
    public ModuleTools getModuleTools() {
        return moduleTools;
    }

    public ChannelEditDetailsScrollPanel getChannelsPanel() {
        return channelsPanel;
    }
    
    
    public void init() {
        
        channelPanelLayout = new BorderLayout();
        
        setLayout(channelPanelLayout);
        
        // module tools
        moduleTools = new ModuleTools(modType, languageHandler);
        
        add(moduleTools, BorderLayout.NORTH);
        
        // channels panel
        channelsPanel = new ChannelEditDetailsScrollPanel(modType, 
                languageHandler, channelCount);
        
        add(channelsPanel, BorderLayout.CENTER);
        
        // kind of pack so the channels don't force the panel to be too big
        setPreferredSize(new Dimension(1000, 600));
    }
}
