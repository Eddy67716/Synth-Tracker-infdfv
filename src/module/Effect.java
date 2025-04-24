/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module;

/**
 *
 * @author Edward Jenkins
 */
public class Effect {
    
    // instance variabels
    private char character;
    private short value;
    
    public Effect(char character, short value) {
        this.character = character;
        this.value = value;
    }
    
    // getters
    public char getCharacter() {
        return character;
    }

    public short getValue() {
        return value;
    }
}
