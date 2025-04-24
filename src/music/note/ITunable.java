/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package music.note;

/**
 *
 * @author Edward Jenkins
 */
public interface ITunable {
    
    public double getOffsetFrequency(byte note);
    
    public double getNoteIndexRatio(double frequency);
}
