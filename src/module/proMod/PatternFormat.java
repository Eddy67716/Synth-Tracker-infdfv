/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module.proMod;

/**
 *
 * @author Edward Jenkins
 */
public class PatternFormat {
    
    // instance variables
    private byte[] packedData;
    // [row][channel][1-4, note (stored as a MIDI value) to effect value]
    private byte[][][] unpackedData;
}
