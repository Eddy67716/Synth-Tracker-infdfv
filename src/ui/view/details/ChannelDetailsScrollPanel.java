/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.details;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;

/**
 *
 * @author Edward Jenkins
 */
public class ChannelDetailsScrollPanel extends JPanel {
    
    // instance variables
    private int modType;
    private int channelCount;
    private JScrollPane scrollPane;
    private ChannelDetailsPanel channelsPanel;
    
    public ChannelDetailsScrollPanel(int modType, int channels) {
        super();
        this.modType = modType;
        this.channelCount = channels;
        init();
    }
    
    public void init() {
        
        channelsPanel = new ChannelDetailsPanel(modType, channelCount);
        
        setLayout(new BorderLayout());
        
        scrollPane = new JScrollPane(channelsPanel, VERTICAL_SCROLLBAR_NEVER, 
                HORIZONTAL_SCROLLBAR_ALWAYS);
        
        add(scrollPane, BorderLayout.NORTH);
    }
}
