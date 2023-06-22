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
public class MidiMacros {
    
    // instance variables
    private String[] globalMacros;
    private String[] parametricMacros;
    private String[] fixedMacros;
    
    public MidiMacros(String[] globalMacros, String[] parametricMacros, 
            String[] fixedMacros) {
        this.globalMacros = globalMacros;
        this.parametricMacros = parametricMacros;
        this.fixedMacros = fixedMacros;
    }

    // getters
    public String[] getGlobalMacros() {
        return globalMacros;
    }

    public String[] getParametricMacros() {
        return parametricMacros;
    }

    public String[] getFixedMacros() {
        return fixedMacros;
    }
    
    // to string
    @Override
    public String toString() {
        
        // string builder
        StringBuilder sb = new StringBuilder();
        
        sb.append("MidiMacros[");
        
        // global
        sb.append("\nGlobal: ");
        
        for (int i = 0; i < globalMacros.length; i++) {
            sb.append("\nMacro ");
            sb.append(i);
            sb.append(": ");
            sb.append(globalMacros[i]);
        }
        
        // parametric
        sb.append("\nParametric: ");
        
        for (int i = 0; i < parametricMacros.length; i++) {
            sb.append("\nMacro ");
            sb.append(i);
            sb.append(": ");
            sb.append(parametricMacros[i]);
        }
        
        // fixed
        sb.append("\nFixed: ");
        
        for (int i = 0; i < fixedMacros.length; i++) {
            sb.append("\nMacro ");
            sb.append(i);
            sb.append(": ");
            sb.append(fixedMacros[i]);
        }
        
        sb.append("]");
        
        return sb.toString();
    }
}
