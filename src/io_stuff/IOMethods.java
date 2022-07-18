/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io_stuff;

/**
 *
 * @author Edward Jenkins
 */
public class IOMethods {
    
    // constants
    public static final boolean LITTLE_ENDIAN = true;
    public static final boolean BIG_ENDIAN = false;
    
    // reverseEndian method
    public static byte[] reverseEndian(byte[] oldArray) {

        // new array
        byte[] newArray = new byte[oldArray.length];

        // reverse the order of byes
        for (int i = 0, j = oldArray.length - 1; i < oldArray.length; i++, j--) {
            newArray[i] = oldArray[j];
        }

        // return the new bytes
        return newArray;
    }
}
