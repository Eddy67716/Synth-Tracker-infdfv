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
import lang.LanguageHandler;

/**
 *
 * @author Edward Jenkins
 */
public class MainMenuBar extends JMenuBar {

    // instance variables
    private LanguageHandler languageHandler;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu viewMenu;
    private JMenu optionsMenu;
    private JMenu helpMenu;
    // file
    private JMenuItem newFile;
    private JMenuItem openFile;
    private JMenuItem saveFile;
    // edit
    private JMenuItem undo;
    private JMenuItem redo;
    // options
    private JMenuItem settings;

    // constructor
    public MainMenuBar(MainController controller, 
            LanguageHandler languageHandler) {
        this.languageHandler = languageHandler;
        fileMenu = new JMenu();
        editMenu = new JMenu();
        viewMenu = new JMenu();
        optionsMenu = new JMenu();
        helpMenu = new JMenu();
        newFile = new JMenuItem();
        openFile = new JMenuItem();
        saveFile = new JMenuItem();
        undo = new JMenuItem();
        redo = new JMenuItem();
        settings = new JMenuItem();
        init(controller);
    }

    private void init(MainController controller) {
        fileMenu.setText(languageHandler
                                .getLanguageText("file"));

        // new file
        newFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_N,
                java.awt.event.InputEvent.CTRL_DOWN_MASK));
        newFile.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/New.png")));
        newFile.setText(languageHandler.getLanguageText("file.new"));
        newFile.addActionListener(e -> controller.newFile());
        fileMenu.add(newFile);

        // open file
        openFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_O,
                java.awt.event.InputEvent.CTRL_DOWN_MASK));
        openFile.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Open.png")));
        openFile.setText(languageHandler.getLanguageText("file.open"));
        openFile.addActionListener(e -> controller.loadFile());
        fileMenu.add(openFile);

        // save file
        saveFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_S,
                java.awt.event.InputEvent.CTRL_DOWN_MASK));
        saveFile.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Filled save icon.png")));
        saveFile.setText(languageHandler.getLanguageText("file.save"));
        saveFile.addActionListener(e -> controller.saveFile());
        fileMenu.add(saveFile);

        add(fileMenu);

        editMenu.setText(languageHandler.getLanguageText("edit"));
        
        // undo
        undo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_Z,
                java.awt.event.InputEvent.CTRL_DOWN_MASK));
        undo.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Undo.png")));
        undo.setText(languageHandler.getLanguageText("edit.undo"));
        undo.addActionListener(e -> controller.undo());
        editMenu.add(undo);
        
        // redo
        redo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_Z,
                java.awt.event.InputEvent.SHIFT_DOWN_MASK |
                java.awt.event.InputEvent.CTRL_DOWN_MASK));
        redo.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Redo.png")));
        redo.setText(languageHandler.getLanguageText("edit.redo"));
        redo.addActionListener(e -> controller.redo());
        editMenu.add(redo);
        
        add(editMenu);

        viewMenu.setText(languageHandler.getLanguageText("view"));
        add(viewMenu);

        optionsMenu.setText(languageHandler.getLanguageText("options"));
        
        settings.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_S,
                java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        settings.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Gear.png")));
        settings.setText(languageHandler.getLanguageText("settings"));
        settings.addActionListener(e -> controller.openSettings());
        optionsMenu.add(settings);
        
        add(optionsMenu);

        helpMenu.setText(languageHandler.getLanguageText("help"));
        add(helpMenu);
    }

}
