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
public class EditHistoryInfo {
    
    // instance varialbes
    int fatDate;                            // used in edit history
    int fatTime;                            // used in edit history
    long runTime;                           // used in edit history
    
    // constructor
    public EditHistoryInfo(int fatDate, int fatTime, long runTime) {
        this.fatDate = fatDate;
        this.fatTime = fatTime;
        this.runTime = runTime;
    }
    
    // getters
    public int getFatDate() {
        return fatDate;
    }

    public int getFatTime() {
        return fatTime;
    }

    public long getRunTime() {
        return runTime;
    }
    
    // string converters
    public String getDateAsString() {
        int date, month, year;
        
        // get the date
        date = fatDate & 0b11111;
        
        // get month
        month = (fatDate >>> 5) &0xf;
        
        // get year
        year = (fatDate >>> 9) + 1980;
        
        StringBuilder aStringBuilder = new StringBuilder();
        aStringBuilder.append(date);
        aStringBuilder.append("/");
        aStringBuilder.append(month);
        aStringBuilder.append("/");
        aStringBuilder.append(year);
        
        return aStringBuilder.toString();
    }
    
    public String getTimeAsString() {
        int hour, minute, second;
        
        // get the date
        second = (fatTime & 0b11111) * 2;
        
        // get month
        minute = (fatTime >>> 5) &0b111111;
        
        // get year
        hour = (fatTime >>> 11);
        
        StringBuilder aStringBuilder = new StringBuilder();
        aStringBuilder.append(hour);
        aStringBuilder.append(":");
        aStringBuilder.append(String.format("%02d", minute));
        aStringBuilder.append(":");
        aStringBuilder.append(String.format("%02d", second));
        
        return aStringBuilder.toString();
    }
    
    public String getRunTimeAsString() {
        
        long totalSeconds; 
        int remaindingSeconds, totalMinutes, remaindingMinutes, hours;
        
        totalSeconds = (long)(runTime * (1/18.2));
        totalMinutes = (int)(totalSeconds / 60);
        remaindingSeconds = (int)(totalSeconds % 60);
        hours = totalMinutes / 60;
        remaindingMinutes = totalMinutes % 60;
        
        StringBuilder aStringBuilder = new StringBuilder();
        aStringBuilder.append(String.format("%02d", hours));
        aStringBuilder.append(":");
        aStringBuilder.append(String.format("%02d", remaindingMinutes));
        aStringBuilder.append(":");
        aStringBuilder.append(String.format("%02d", remaindingSeconds));
        
        return aStringBuilder.toString();
    }
    
    // To string methods
    @Override
    public String toString() {
        
        StringBuilder aStringBuilder = new StringBuilder();
        aStringBuilder.append("Edit Date: ");
        aStringBuilder.append(getDateAsString());
        aStringBuilder.append(" Edit Time: ");
        aStringBuilder.append(getTimeAsString());
        aStringBuilder.append(" Edit Duration: ");
        aStringBuilder.append(getRunTimeAsString());
        
        return aStringBuilder.toString();
    }
    
}
