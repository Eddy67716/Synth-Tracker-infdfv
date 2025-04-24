/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module.xm.format;

/**
 *
 * @author Edward Jenkins
 */
public class XMPattern {
    
    // instance variables
    private int patternHeaderLength;
    private byte packingType;
    private short rowNumber;
    private short packedDataSize;
    private byte[] packedData;
    private byte[][][] unpackedData;
}
