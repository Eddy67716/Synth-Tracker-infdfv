/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.undo;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 *
 * @author Edward Jenkins
 */
public class UndoableRadioButtonChange extends AbstractUndoableEdit{
    
    // intance varables
    private JRadioButton radioButton;
    
    // constructor
    public UndoableRadioButtonChange(JRadioButton radioButton) {
        this.radioButton = radioButton;
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        radioButton.setSelected(!radioButton.isSelected());
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        radioButton.setSelected(!radioButton.isSelected());
    }
}
