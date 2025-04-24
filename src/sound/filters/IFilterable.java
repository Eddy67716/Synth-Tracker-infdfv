/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound.filters;

/**
 *
 * @author Edward Jenkins
 */
public interface IFilterable {
    
    // set cutoff
    public void setCutoff(double cutoffFrequency);
    
    // filter
    public double filter(double point);
}
