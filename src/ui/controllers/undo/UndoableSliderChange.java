/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.undo;

import javax.swing.JSlider;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 *
 * @author Edward Jenkins
 */
public class UndoableSliderChange extends AbstractUndoableEdit{
    
    // instance variables
    // the slider that is updated
    private final JSlider slider;
    // the old value of the slider
    private final int oldValue;
    // the current value of the slider
    private final int value;
    
    public UndoableSliderChange(JSlider slider, int oldValue) {
        this.slider = slider;
        this.value = slider.getValue();
        this.oldValue = oldValue;
    }
    
    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        slider.setValue(value);
    }
    
    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        slider.setValue(oldValue);
    }
}
