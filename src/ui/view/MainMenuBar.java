/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view;

import ui.controllers.MainController;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @author Edward Jenkins
 */
public class MainMenuBar extends JMenuBar {

    // instance variables
    private MainController controller;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu viewMenu;
    private JMenu optionsMenu;
    private JMenu helpMenu;
    private JMenuItem newFile;
    private JMenuItem openFile;
    private JMenuItem saveFile;
    private JMenuItem settings;

    // constructor
    public MainMenuBar(MainController controller) {

        fileMenu = new JMenu();
        editMenu = new JMenu();
        viewMenu = new JMenu();
        optionsMenu = new JMenu();
        helpMenu = new JMenu();
        newFile = new JMenuItem();
        openFile = new JMenuItem();
        saveFile = new JMenuItem();
        settings = new JMenuItem();
        
        this.controller = controller;
        init();
    }

    private void init() {
        fileMenu.setText("File");

        newFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_N,
                java.awt.event.InputEvent.CTRL_DOWN_MASK));
        newFile.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/New.png")));
        newFile.setText("New file");
        newFile.addActionListener(e -> controller.newFile());
        fileMenu.add(newFile);

        openFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_O,
                java.awt.event.InputEvent.CTRL_DOWN_MASK));
        openFile.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Open.png")));
        openFile.setText("Open file");
        openFile.addActionListener(e -> controller.loadFile());
        fileMenu.add(openFile);

        saveFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_S,
                java.awt.event.InputEvent.CTRL_DOWN_MASK));
        saveFile.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Filled save icon.png")));
        saveFile.setText("Save file");
        saveFile.addActionListener(e -> controller.saveFile());
        fileMenu.add(saveFile);

        add(fileMenu);

        editMenu.setText("Edit");
        add(editMenu);

        viewMenu.setText("View");
        add(viewMenu);

        optionsMenu.setText("Options");
        
        settings.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_S,
                java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        settings.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Gear.png")));
        settings.setText("Settings");
        settings.addActionListener(e -> controller.openSettings());
        optionsMenu.add(settings);
        
        add(optionsMenu);

        helpMenu.setText("Help");
        add(helpMenu);
    }

}
