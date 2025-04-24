/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.undo;

import javax.swing.JComboBox;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 *
 * @author Edward Jenkins
 */
public class UndoableComboBoxChange extends AbstractUndoableEdit{
    
    // instance variables
    // Combo box
    private final JComboBox comboBox;
    // the old index of the combo box
    private final int oldIndex;
    // the current index of the combo box
    private final int index;
    
    // constructor
    public UndoableComboBoxChange(JComboBox comboBox, int oldIndex) {
        this.comboBox = comboBox;
        this.oldIndex = oldIndex;
        this.index = comboBox.getSelectedIndex();
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo(); 
        comboBox.setSelectedIndex(index);
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        comboBox.setSelectedIndex(oldIndex);
    }
    
}
