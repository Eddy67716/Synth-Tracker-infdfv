
// Java program to illustrate 
// working of SwingWorker
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import ui.controllers.MainController;
import ui.main.MainFrame;
import ui.view.pattens.PatternCell;
import ui.view.MainMenuBar;
import ui.view.MainUI;
import ui.view.details.InitialTiming;
import ui.view.details.ModuleDetails;
import ui.view.details.ModuleMessage;
import ui.view.details.ModuleOptions;
import ui.view.details.ModuleSoundOptions;
import ui.view.details.ModuleTools;
import ui.view.instruments.MidiOptions;
import ui.view.instruments.NoteMapView;
import ui.view.samples.SampleSoundOptions;


/**
 *
 * @author Edward Jenkins
 */
public class testUIPanel extends JFrame {
      
    // instance variables
    private JPanel testPanel;
    private byte[] data = {61, 1, 64, 8, 0};
    
    // constructor
    public testUIPanel() {
        testPanel = new ModuleTools(4);
        init();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // run the frame
        SwingUtilities.invokeLater(() -> {
            new testUIPanel().setVisible(true);
        });
        
    }
    
    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Synth Tracker");
        add(testPanel);
        pack();
        setLocationRelativeTo(null);
    }
}
