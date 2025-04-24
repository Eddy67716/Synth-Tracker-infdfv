/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module;

import java.util.Calendar;

/**
 *
 * @author Edward Jenkins
 */
public abstract class EditHistoryTime {
    
    // instance variables
    // Callendar
    Calendar dateTime;
    // time in ns
    private long nsTime;
    // has been edited
    private boolean edited;
    
    // constructor
    public EditHistoryTime() {
        dateTime = Calendar.getInstance();
        nsTime = System.nanoTime();
        edited = false;
    }

    // getter
    public Calendar getDateTime() {
        return dateTime;
    }
    
    public long getNsTime() {
        return nsTime;
    }

    public boolean isEdited() {
        return edited;
    }

    // setter
    public void setNsTime(long nsTime) {
        this.nsTime = nsTime;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }
}
