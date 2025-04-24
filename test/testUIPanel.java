
// Java program to illustrate 
// working of SwingWorker
import javax.swing.*;
import ui.view.windows.find.FindPanel;
import ui.view.windows.find.FindVolume;
import ui.view.windows.replace.ReplaceInstrument;
import ui.view.windows.replace.ReplacePanel;
import ui.view.windows.replace.ReplaceVolume;

/**
 *
 * @author Edward Jenkins
 */
public class testUIPanel extends JFrame {

    // instance variables
    private JPanel testPanel;

    // constructor
    public testUIPanel() {
        testPanel = new FindPanel((byte)4);
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
