/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module.it.format;

import io.IReadable;
import io.IWritable;
import java.io.IOException;

/**
 *
 * @author Edward Jenkins
 */
public class ITSampleCache {
    
    // instance variables
    private long fileSize;
    private short cacheDate;
    private short time;
    private byte format;
    
    // constructor
    public ITSampleCache() {
        
    }
    
    // getters
    public long getFileSize() {
        return fileSize;
    }

    public short getCacheDate() {
        return cacheDate;
    }
    
    // string converters
    public String getDateAsString() {
        int date, month, year;
        
        // get the date
        date = cacheDate & 0b11111;
        
        // get month
        month = (cacheDate >>> 5) &0xf;
        
        // get year
        year = (cacheDate >>> 9) + 1980;
        
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
        second = (time & 0b11111) * 2;
        
        // get month
        minute = (time >>> 5) &0b111111;
        
        // get year
        hour = (time >>> 11);
        
        StringBuilder aStringBuilder = new StringBuilder();
        aStringBuilder.append(hour);
        aStringBuilder.append(":");
        aStringBuilder.append(String.format("%02d", minute));
        aStringBuilder.append(":");
        aStringBuilder.append(String.format("%02d", second));
        
        return aStringBuilder.toString();
    }

    public short getTime() {
        return time;
    }

    public byte getFormat() {
        return format;
    }
    
    
    public boolean read(IReadable reader) throws IOException {
        
        // read fileSize
        fileSize = reader.getUIntAsLong();
        
        // read date
        cacheDate = reader.getShort();
        
        // read time
        time = reader.getShort();
        
        // read format
        format = reader.getByte();
        
        return true;
    }
    
    public boolean write(IWritable writer) throws IOException {
        
        // write fileSize
        writer.writeInt((int)fileSize);
        
        // write date
        writer.writeShort(cacheDate);
        
        // write time
        writer.writeShort(time);
        
        // write format
        writer.writeByte(format);
        
        return true;
    }
}
