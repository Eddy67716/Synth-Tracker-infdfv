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
import static ui.UIProperties.*;

/**
 *
 * @author Edward Jenkins
 */
public class MainFrame extends JFrame {

    // instance variables
    private MainController controller;
    private MainMenuBar mainMenuBar;
    private MainUI mainUI;

    // constructor
    public MainFrame() {

        this.mainUI = new MainUI();
        this.controller = new MainController(mainUI);
        this.mainMenuBar = new MainMenuBar(controller);

        init();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // run the frame
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1920, 1080));
        setTitle("Synth Tracker");
        setJMenuBar((JMenuBar) mainMenuBar);
        add(mainUI);
        setLocationRelativeTo(null);
    }

}
