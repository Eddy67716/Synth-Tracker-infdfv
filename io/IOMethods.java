/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

/**
 *
 * @author Edward Jenkins
 */
public class IOMethods {
    
    // constants
    public static final boolean LITTLE_ENDIAN = true;
    public static final boolean BIG_ENDIAN = false;
    
    /**
     * Reverses the byte order of an array of bytes for converting big to little
     * endian or vice versa
     * 
     * @param oldArray The array of bytes to reverse
     * @return The reversed array of bytes
     */
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
    
    /**
     * Set string to a specified length, adds " " if it is smaller than length
     * 
     * @param oldString The string to change length of
     * @param length The length to enlarge or cut down to
     * @return The new string with the specified length
     */
    public static String setStringToLength(String oldString, int length) {
        String newString;
        
        if (oldString.length() > length) {
            newString = oldString.substring(0, length);
        } else if (oldString.length() < length) {
            StringBuilder sb = new StringBuilder(oldString);
            
            while (sb.length() < length) {
                sb.append(" ");
            }
            
            return sb.toString();
            
        } else {
            newString = oldString;
        }
        
        return newString;
    }
    
    /**
     * Set string to a specified length, adds [null] if it is smaller than length
     * 
     * @param oldString The string to change length of
     * @param length The length to enlarge or cut down to
     * @return The new string with the specified length
     */
    public static String setStringToNullLength(String oldString, int length) {
        String newString;
        
        if (oldString.length() > length) {
            newString = oldString.substring(0, length);
        } else if (oldString.length() < length) {
            StringBuilder sb = new StringBuilder(oldString);
            
            while (sb.length() < length) {
                sb.append("\0");
            }
            
            return sb.toString();
            
        } else {
            newString = oldString;
        }
        
        return newString;
    }
}
