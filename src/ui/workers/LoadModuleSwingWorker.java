/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.workers;

import module.IModuleFile;
import ui.view.ProgressionBar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author Edward Jenkins
 */
public class LoadModuleSwingWorker extends SwingWorker{
    
    // instance variables
    private IModuleFile fileToLoad;
    private ProgressionBar progressFrame;
    
    public LoadModuleSwingWorker(IModuleFile fileToLoad, 
            ProgressionBar progressFrame) {
        this.fileToLoad = fileToLoad;
        this.progressFrame = progressFrame;
    }

    @Override
    protected IModuleFile doInBackground() throws Exception {
        
        progressFrame.init();
        
        fileToLoad.read();

        return fileToLoad;
    }
    
    @Override
    public void done() {
        progressFrame.dispose();
    }
    
}
