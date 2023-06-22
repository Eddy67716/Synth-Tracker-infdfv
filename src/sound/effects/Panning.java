/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound.effects;

/**
 *
 * @author Edward Jenkins
 */
public class Panning {

    // constants
    public static final byte DEF_PANNING = 0;
    public static final boolean DEF_SURROUND = false;

    // instance variables
    private double panning;             // panning value -1 = right pan
                                        // 0 = central pan and 1 = left pan
    private double leftPanAmplitude;    // used to set left point
    private double rightPanAmplitude;   // used to set right point
    private boolean surround;           // inverts the right channel
    private double panbrelloOffset;     // used for panbrellos

    // all-args constructor
    public Panning(double panning, boolean surround) {
        this.panning = panning;
        this.surround = surround;
        panbrelloOffset = panning;
        leftPanAmplitude = 1 + panning;
        rightPanAmplitude = 1 - panning;
    }

    // one-arg constructor
    public Panning(double panning) {
        this(panning, DEF_SURROUND);
    }

    // getters
    public double getLeftPanAmplitude() {
        return this.leftPanAmplitude;
    }

    public double getRightPanAmplitude() {
        return this.rightPanAmplitude;
    }

    public boolean isSurround() {
        return this.surround;
    }

    // setters
    public void setPanning(double panning) {
        this.panning = panning;
        panbrelloOffset = panning;
        leftPanAmplitude = 1 + panning;
        rightPanAmplitude = 1 - panning;
    }

    public void setSurround(boolean surround) {
        this.surround = surround;
    }
    
    public void panbrelloShift(double panbrelloAmount) {
        panning = panbrelloOffset + panbrelloAmount;
        leftPanAmplitude = 1 + panning;
        if (leftPanAmplitude > 2) {
            leftPanAmplitude = 2;
        }
        rightPanAmplitude = 1 - panning;
        if (rightPanAmplitude > 2) {
            rightPanAmplitude = 2;
        }
    }
}
