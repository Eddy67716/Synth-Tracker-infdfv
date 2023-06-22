/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.it.format;

/**
 *
 * @author Edward Jenkins
 */
public class NodePoint {
    
    // instance variables
    private byte nodeValue;
    private short tickNumber;
    
    // constructor
    public NodePoint(byte nodeValue, short tickNumber) {
        this.nodeValue = nodeValue;
        this.tickNumber = tickNumber;
    }
    
    // getters
    public byte getNodeValue() {
        return nodeValue;
    }

    public short getTickNumber() {
        return tickNumber;
    }
    
    // setters
    public void setNodeValue(byte nodeValue) {
        this.nodeValue = nodeValue;
    }

    public void setTickNumber(short tickNumber) {
        this.tickNumber = tickNumber;
    }
    
    // to string method
    @Override
    public String toString() {
        
        // string builder
        StringBuilder sb = new StringBuilder();
        
        sb.append("\nNode Point[");
        
        // node value
        sb.append("\nNode value: ");
        sb.append(nodeValue);
        
        // tick number
        sb.append("\nTick number: ");
        sb.append(tickNumber);
        
        sb.append("]");
        
        return sb.toString();
    }
}
