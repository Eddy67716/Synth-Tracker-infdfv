/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module.it.decoders;

/**
 *
 * @author Edward Jenkins
 */
public class effectDecoders {

    public static String decodeVolumeByte(short b) {

        short decodeValue = 0;

        // or b so it is unsigned
        decodeValue |= b & 0xff;

        if (decodeValue <= 64) {
            return "v" + String.format("%02d", decodeValue);
        } else if (decodeValue <= 74) {
            return "a" + String.format("%02d", decodeValue - 65);
        } else if (decodeValue <= 84) {
            return "b" + String.format("%02d", decodeValue - 75);
        } else if (decodeValue <= 94) {
            return "c" + String.format("%02d", decodeValue - 85);
        } else if (decodeValue <= 104) {
            return "d" + String.format("%02d", decodeValue - 95);
        } else if (decodeValue <= 114) {
            return "e" + String.format("%02d", decodeValue - 105);
        } else if (decodeValue <= 124) {
            return "f" + String.format("%02d", decodeValue - 115);
        } else if (decodeValue >= 128 && decodeValue <= 192) {
            return "p" + String.format("%02d", decodeValue - 128);
        } else if (decodeValue <= 202) {
            return "g" + String.format("%02d", decodeValue - 193);
        } else if (decodeValue <= 212) {
            return "g" + String.format("%02d", decodeValue - 203);
        } else {
            return "_00";
        }
    }

    public static char decodeEffectByte(short b) {

        if (b > 0) {
            return (char) (b + 64);
        } else {
            return '_';
        }
    }
}
