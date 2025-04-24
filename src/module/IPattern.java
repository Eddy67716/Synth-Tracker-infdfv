/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module;

/**
 *
 * @author Edward Jenkins
 */
public interface IPattern {
    
    public String getName();
    
    public int getLength();

    public short getRows();

    public byte[][][] getUnpackedData();

    public byte getNumberOfChannels();
    
    public byte getGlobalChannelCount();
}
