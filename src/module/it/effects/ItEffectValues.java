/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module.it.effects;

import module.Effect;

/**
 *
 * @author Edward Jenkins
 */
public class ItEffectValues {
    
    // volume effects
    public static final char VOLUME = 'v';
    public static final char VOLUME_SLIDE_UP = 'a';
    public static final char VOLUME_SLIDE_DOWN = 'b';
    public static final char VOL_FINE_SLIDE_UP = 'c';
    public static final char VOL_FINE_SLIDE_DOWN = 'd';
    public static final char VOL_PORT_SLIDE_UP = 'e';
    public static final char VOL_PORT_SLIDE_DOWN = 'f';
    public static final char VOLUME_PAN = 'p';
    public static final char VOL_TONE_PORT = 'g';
    public static final char VOL_VIBRATO = 'h';
    
    // effects
    public static final Effect SPEED = new Effect('A', (short)1);
    public static final Effect ORDER_JUMP = new Effect('B', (short) 2);
    public static final Effect ROW_BREAK= new Effect('C', (short)3);
    public static final Effect VOLUME_SLIDE = new Effect('D', (short)4);
    public static final Effect PORTAMENTO_UP= new Effect('E', (short)5);
    public static final Effect PORTAMENTO_DOWN = new Effect('F', (short)6);
    public static final Effect TONE_PORTAMENTO = new Effect('G', (short)7);
    public static final Effect VIBRATO = new Effect('H', (short)8);
    public static final Effect TREMOR = new Effect('I', (short)9);
    public static final Effect ARPEGGIO = new Effect('J', (short)10);
    public static final Effect VOL_SLIDE_VIB = new Effect('K', (short)11);
    public static final Effect VOL_SLIDE_TONE_PORT = new Effect('L', (short)12);
    public static final Effect CHANNEL_VOLUME = new Effect('M', (short)13);
    public static final Effect CHANNEL_VOL_SLIDE= new Effect('N', (short)14);
    public static final Effect OFFSET = new Effect('O', (short)15);
    public static final Effect PAN_SLIDE = new Effect('P', (short)16);
    public static final Effect NOTE_RETRIGGER = new Effect('Q', (short)17);
    public static final Effect TREMULANT = new Effect('R', (short)18);
    public static final Effect SPECIAL = new Effect('S', (short)19);
    public static final Effect TEMPO = new Effect('T', (short)20);
    public static final Effect FINE_VIBRATO = new Effect('U', (short)21);
    public static final Effect GLOBAL_VOLUME = new Effect('V', (short)22);
    public static final Effect GLOB_VOL_SLIDE = new Effect('W', (short)23);
    public static final Effect PANNING = new Effect('X', (short)24);
    public static final Effect PANBRELLO = new Effect('Y', (short)25);
    public static final Effect MIDI_MACRO = new Effect('Z', (short)26);
    public static final Effect PARAMETER_EXTENSION = new Effect('#', (short)27);
    public static final Effect SMOOTH_MIDI_MACRO = new Effect('\\', (short)28);
    
    public static short encodeVolumeByte(String value)
            throws IllegalArgumentException {

        // method variables
        char decodedChar;
        int volumeValue;

        // return blank volume
        if (value.equals("")) {
            return (short) 255;
        }

        try {

            decodedChar = Character.toLowerCase(value.charAt(0));
            volumeValue = Integer.parseInt(value.substring(1));

            switch (decodedChar) {
                case VOLUME:
                    return (short) volumeValue;
                case VOLUME_SLIDE_UP:
                    return (short) (volumeValue + 65);
                case VOLUME_SLIDE_DOWN:
                    return (short) (volumeValue + 75);
                case VOL_FINE_SLIDE_UP:
                    return (short) (volumeValue + 85);
                case VOL_FINE_SLIDE_DOWN:
                    return (short) (volumeValue + 95);
                case VOL_PORT_SLIDE_UP:
                    return (short) (volumeValue + 105);
                case VOL_PORT_SLIDE_DOWN:
                    return (short) (volumeValue + 115);
                case VOLUME_PAN:
                    return (short) (volumeValue + 128);
                case VOL_TONE_PORT:
                    return (short) (volumeValue + 193);
                case VOL_VIBRATO:
                    return (short) (volumeValue + 203);
                default:
                    return (short) 255;
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value is not an integer");
        }

    }

    public static short encodeEffectByte(char value)
            throws IllegalArgumentException {

        value = Character.toUpperCase(value);
        
        if (Character.isLetter(value)) {
            return (short) ((short) value - 64);
        } else if (value == '#') {
            return 27;
        } else if (value == '\\') {
            return 28;
        } else {
            throw new IllegalArgumentException("Input must be a character");
        }
    }
    
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
            return "h" + String.format("%02d", decodeValue - 203);
        } else {
            return "_00";
        }
    }

    public static char decodeEffectByte(short b) {

        if (b > 0 && b < 27) {
            return (char) (b + 64);
        } else if (b == 27) {
            return '#';
        } else if (b == 28) {
            return '\\';
        } else {
            return '_';
        }
    }
}
