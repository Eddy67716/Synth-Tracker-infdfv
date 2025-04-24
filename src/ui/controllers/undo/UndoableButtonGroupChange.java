/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.undo;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 *
 * @author Edward Jenkins
 */
public class UndoableButtonGroupChange extends AbstractUndoableEdit{
    
    // intance varables
    private ButtonGroup buttonGroup;
    private ButtonModel oldButtonModel;
    private ButtonModel newButtonModel;
    
    // constructor
    public UndoableButtonGroupChange(ButtonGroup buttonGroup, 
            ButtonModel oldButtonModel) {
        this.buttonGroup = buttonGroup;
        this.oldButtonModel = oldButtonModel;
        this.newButtonModel = buttonGroup.getSelection();
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        buttonGroup.setSelected(newButtonModel, true);
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        buttonGroup.setSelected(oldButtonModel, true);
    }
}
