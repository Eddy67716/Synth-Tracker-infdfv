/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Module.IT.Decoders;

/**
 *
 * @author Edward Jenkins
 */
public class effectDecoders {

    public static String decodeVolumeByte(short b) {

        short decodeValue = 0;

        // or b so it is unsigned
        decodeValue |= b;

        if (decodeValue == 0) {
            return "_00";
        } else if (decodeValue > 0 && decodeValue <= 64) {
            return "v" + decodeValue;
        } else if (decodeValue <= 74) {
            return "a" + (decodeValue - 65);
        } else if (decodeValue <= 84) {
            return "b" + (decodeValue - 75);
        } else if (decodeValue <= 94) {
            return "c" + (decodeValue - 85);
        } else if (decodeValue <= 104) {
            return "d" + (decodeValue - 95);
        } else if (decodeValue <= 114) {
            return "e" + (decodeValue - 105);
        } else if (decodeValue <= 124) {
            return "f" + (decodeValue - 115);
        } else if (decodeValue >= 128 && decodeValue <= 192) {
            return "p" + (decodeValue - 128);
        } else if (decodeValue <= 202) {
            return "g" + (decodeValue - 193);
        } else if (decodeValue <= 212) {
            return "g" + (decodeValue - 203);
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
