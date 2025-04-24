/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.it.format;

import module.IEnvelopeNode;

/**
 *
 * @author Edward Jenkins
 */
public class ItNodePoint implements IEnvelopeNode {
    
    // instance variables
    private byte nodePoint;
    private short tickIndex;
    
    // constructor
    public ItNodePoint(byte nodePoint, short tickIndex) {
        this.nodePoint = nodePoint;
        this.tickIndex = tickIndex;
    }
    
    // getters
    @Override
    public short getNodePoint() {
        return nodePoint;
    }

    @Override
    public short getTickIndex() {
        return tickIndex;
    }
    
    // setters
    @Override
    public void setNodePoint(short nodeValue) {
        this.nodePoint = (byte) nodeValue;
    }

    @Override
    public void setTickIndex(short tickNumber) {
        this.tickIndex = tickNumber;
    }
    
    // to string method
    @Override
    public String toString() {
        
        // string builder
        StringBuilder sb = new StringBuilder();
        
        sb.append("\nNode Point[");
        
        // node value
        sb.append("\nNode value: ");
        sb.append(nodePoint);
        
        // tick number
        sb.append("\nTick number: ");
        sb.append(tickIndex);
        
        sb.append("]");
        
        return sb.toString();
    }

    @Override
    public byte getRate() {
        return 0;
    }

    @Override
    public void setRate(byte rate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
