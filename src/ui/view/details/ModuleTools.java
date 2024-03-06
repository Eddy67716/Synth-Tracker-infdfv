/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.details;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

/**
 *
 * @author Edward Jenkins
 */
public class ModuleTools extends JPanel {
    
    // instance variables
    private int modType;
    private GridBagLayout toolsLayout;
    private GridBagConstraints tc;
    private ModuleDetails moduleDetails;
    private ModuleSoundOptions moduleSoundOptions;
    private ModuleOptions moduleOptions;
    private InitialTiming moduleTiming;
    private ModuleMessage moduleMessage;
    
    // constructor
    public ModuleTools(int modType) {
        this.modType = modType;
        init();
    }
    
    // gettes
    public ModuleDetails getModuleDetails() {
        return moduleDetails;
    }

    public ModuleSoundOptions getModuleSoundOptions() {
        return moduleSoundOptions;
    }

    public ModuleOptions getModuleOptions() {
        return moduleOptions;
    }

    public InitialTiming getModuleTiming() {
        return moduleTiming;
    }

    public ModuleMessage getModuleMessage() {
        return moduleMessage;
    }
    
    private void init() {
        
        // set the layout
        toolsLayout = new GridBagLayout();
        setLayout(toolsLayout);
        tc = new GridBagConstraints();
        tc.fill = GridBagConstraints.BOTH;
        tc.anchor = GridBagConstraints.NORTHWEST;
        
        // mod detials
        moduleDetails = new ModuleDetails(modType);
        
        // layout
        tc.gridx = 0;
        tc.gridy = 0;
        tc.gridheight = 2;
        tc.weighty = 1;
        add(moduleDetails, tc);
        
        // sound options
        moduleSoundOptions = new ModuleSoundOptions(modType);
        
        // layout
        tc.gridx = 1;
        tc.gridy = 0;
        tc.gridheight = 1;
        add(moduleSoundOptions, tc);
        
        // timing
        moduleTiming = new InitialTiming(modType);
        
        // layout
        tc.gridx = 1;
        tc.gridy = 1;
        tc.gridheight = 1;
        tc.weighty = 1;
        add(moduleTiming, tc);
        
        // otions
        moduleOptions = new ModuleOptions(modType);
        
        // layout
        tc.gridx = 2;
        tc.gridy = 0;
        tc.gridheight = 2;
        tc.weighty = 1;
        add(moduleOptions, tc);
        
        // message
        moduleMessage = new ModuleMessage(modType);
        
        // layout
        tc.gridx = 3;
        tc.gridy = 0;
        tc.gridwidth = GridBagConstraints.REMAINDER;
        tc.gridheight = 2;
        tc.weightx = 1;
        tc.weighty = 1;
        add(moduleMessage, tc);
    }
}
