/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package module;

/**
 *
 * @author eddy6
 */
public interface IEnvelopeNode {
    
    public short getNodePoint();
    
    public short getTickIndex();
    
    public byte getRate();
    
    public void setNodePoint(short nodePoint);
    
    public void setTickIndex(short tickIndex);
    
    public void setRate(byte rate);
}
