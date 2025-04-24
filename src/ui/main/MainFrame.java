/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ui.main;

import ui.controllers.MainController;
import ui.view.MainMenuBar;
import ui.view.MainUI;
import ui.view.samples.SampleUI;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import lang.LanguageHandler;
import ui.UIProperties;
import static ui.UIProperties.*;
import ui.custom.SynthCustomMetalTheme;

/**
 *
 * @author Edward Jenkins
 */
public class MainFrame extends JFrame {

    // instance variables
    private MainController controller;
    private MainMenuBar mainMenuBar;
    private MainUI mainUI;
    private LanguageHandler languageHandler;

    // constructor
    public MainFrame() {

        try {
            languageHandler = new LanguageHandler();
            this.mainUI = new MainUI(languageHandler);
            this.controller = new MainController(mainUI);
            this.mainMenuBar = new MainMenuBar(controller, languageHandler);
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        MetalLookAndFeel.setCurrentTheme(new SynthCustomMetalTheme());

        // set ui properties
        UIProperties.setUIProperties();

        // run the frame
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    private void init() {

        //System.out.println(UIManager.get("ScrollBar.track"));
        // set main frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1920, 1080));
        setTitle(languageHandler.getLanguageText("synth_tracker"));
        setJMenuBar((JMenuBar) mainMenuBar);
        add(mainUI);
        setLocationRelativeTo(null);
    }

}
