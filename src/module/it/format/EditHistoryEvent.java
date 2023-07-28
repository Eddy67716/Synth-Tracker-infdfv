/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.it.format;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Edward Jenkins
 */
public class EditHistoryEvent {

    // constants
    public static final short IBM_OFFSET_YEAR = 1980;

    // instance varialbes
    // date in FAT format
    private int fatDate;
    // time in FAT format
    private int fatTime;
    // run time in DOS ticks (seconds * 18.2)
    private long runTime;                           

    // constructor
    public EditHistoryEvent(int fatDate, int fatTime, long runTime) {
        this.fatDate = fatDate;
        this.fatTime = fatTime;
        this.runTime = runTime;
    }

    // 1 arg constructor
    public EditHistoryEvent(Calendar dateTime, long runTime) {
        this.runTime = runTime;

        // date (1-31)
        fatDate = dateTime.get(Calendar.DATE);

        // month (1-12)
        fatDate |= (dateTime.get(Calendar.MONTH) + 1) << 5;

        // year
        fatDate |= (dateTime.get(Calendar.YEAR) - IBM_OFFSET_YEAR) << 9;

        // seconds
        fatTime = dateTime.get(Calendar.SECOND) / 2;
        
        // minutes
        fatTime |= dateTime.get(Calendar.MINUTE) << 5;
        
        // hours
        int hours = dateTime.get(Calendar.HOUR);
        
        if (hours == 0) {
            hours = 12;
        }
        
        fatTime |= hours << 11;
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
        month = (fatDate >>> 5) & 0xf;

        // get year
        year = (fatDate >>> 9) + IBM_OFFSET_YEAR;

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

        // get the seconds
        second = (fatTime & 0b11111) * 2;

        // get the minutes
        minute = (fatTime >>> 5) & 0b111111;

        // get the hours
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

        totalSeconds = (long) (runTime * (1 / 18.2));
        totalMinutes = (int) (totalSeconds / 60);
        remaindingSeconds = (int) (totalSeconds % 60);
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
