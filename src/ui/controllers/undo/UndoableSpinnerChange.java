/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.undo;

import javax.swing.JSpinner;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

/**
 *
 * @author Edward Jenkins
 */
public class UndoableSpinnerChange extends AbstractUndoableEdit {

    // instance variables
    // spinner that action was performed on
    private final JSpinner spinner;
    // the value before an update
    private final Object oldValue;
    // the value performed on the spinner;
    private final Object value;

    // constructor
    public UndoableSpinnerChange(JSpinner spinner, Object oldValue) {
        this.spinner = spinner;
        this.oldValue = oldValue;
        this.value = spinner.getValue();
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        spinner.setValue(value);
    }
    
    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        spinner.setValue(oldValue);
    }
}
