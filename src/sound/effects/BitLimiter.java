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
public class BitLimiter {

    // instance variables
    private int bitRate;
    private boolean isFloat;

    // constructor
    public BitLimiter(int bitRate, boolean isFloat) {
        this.bitRate = bitRate;
        this.isFloat = isFloat;
    }

    // calculate amplitude
    public double calculateAmpLimit(double point) {

        double newPoint;

        // get the peak amplitude
        switch (bitRate) {
            // 64 bit
            case 64:
                newPoint = point;
                break;
            // 32 bit
            case 32:
                if (isFloat) {
                    newPoint = (float)point;
                } else {
                    newPoint = (int) Math.round(2147483647.5 * point - 0.5);
                }
                break;
            // 24 bit
            case 24:
                newPoint = (int) Math.round(8388607.5 * point - 0.5);
                break;
            // 16 bit
            case 16:
                newPoint = (int) Math.round(32767.5 * point - 0.5);
                break;
            // 8 bit
            default:
                newPoint = (int) Math.round(127.5 * point - 0.5);
                break;
        }

        return newPoint;
    }
}
