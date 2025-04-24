/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module.proMod;

/**
 *
 * @author Edward Jenkins
 */
public class OrderData {
    
    // instance variables
    // order count
    private short songLength;
    // usually set to 127, but is not needed anymore
    private short oldRestartPosition;
    // order data stored in this array
    private byte[] patternOrder;
    // order string (usually "M.K.")
    private String orderString;
}
