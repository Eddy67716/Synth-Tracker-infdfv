/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.undo;

import javax.swing.JCheckBox;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 *
 * @author Edward Jenkins
 */
public class UndoableCheckBoxChange extends AbstractUndoableEdit {
    
    // instance variables
    // check box
    private final JCheckBox checkBox;
    
    // constructor
    public UndoableCheckBoxChange(JCheckBox checkBox) {
        this.checkBox = checkBox;
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        checkBox.setSelected(!checkBox.isSelected());
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        checkBox.setSelected(!checkBox.isSelected());
    }
}
