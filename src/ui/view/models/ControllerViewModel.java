/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.models;

import ui.controllers.DetailsController;
import ui.controllers.IUndoable;
import ui.controllers.InstrumentController;
import ui.controllers.PatternController;
import ui.controllers.SampleController;

/**
 *
 * @author Edward Jenkins
 */
public class ControllerViewModel {

    // controllers
    private DetailsController detailsController;
    private InstrumentController instrumentController;
    private SampleController sampleController;
    private PatternController patternController;

    // constructor
    public ControllerViewModel(DetailsController headerController,
            InstrumentController instrumentController,
            SampleController sampleController,
            PatternController patternController) {
        this.detailsController = headerController;
        this.instrumentController = instrumentController;
        this.sampleController = sampleController;
        this.patternController = patternController;
    }

    // no args constructor
    public ControllerViewModel() {

    }

    // getters
    public DetailsController getDetailsController() {
        return detailsController;
    }

    public InstrumentController getInstrumentController() {
        return instrumentController;
    }

    public SampleController getSampleController() {
        return sampleController;
    }

    public PatternController getPatternController() {
        return patternController;
    }

    // settters
    public void setDetailsController(DetailsController detailsController) {
        this.detailsController = detailsController;
    }

    public void setInstrumentController(InstrumentController instrumentController) {
        this.instrumentController = instrumentController;
    }

    public void setSampleController(SampleController sampleController) {
        this.sampleController = sampleController;
    }

    public void setPatternController(PatternController patternController) {
        this.patternController = patternController;
    }

    // undo
    public void undo(int index) {

        getSelectedPanelController(index).undo();
    }

    // redo
    public void redo(int index) {

        getSelectedPanelController(index).redo();
    }

    // get selected panel controller
    public IUndoable getSelectedPanelController(int index) {

        IUndoable returnController = null;
        
        switch (index) {
            case 0:
                returnController = detailsController;
                break;
            case 1:
                returnController = patternController;
                break;
            case 2:
                returnController = sampleController;
                break;
            case 3:
                returnController = instrumentController;
                break;
            default:
                break;
        }
        
        return returnController;
    }
}
