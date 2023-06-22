/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.compression;

import io.IWritable;

/**
 *
 * @author Edward Jenkins
 */
public class SampleCompressor extends SampleCompressSpec {

    // constructor
    public SampleCompressor(byte bitRate, boolean floating,
            boolean doubleDelta) {
        super(bitRate, floating, doubleDelta);
    }

    // compress
    public boolean compress(long[] sampleData, IWritable w) {

        // method variables
        int index, end, length;
        byte bitWidth;

        // delta encode the samples
        deltaEncode(sampleData);

        // IT 15 comressed
        if (isDoubleDelta()) {
            deltaEncode(sampleData);
        }

        return true;
    }

    // delta encode
    public void deltaEncode(long[] sampleData) {

        long deltaValue = 0, newDeltaValue;

        for (int i = 0; i < sampleData.length; i++) {
            newDeltaValue = sampleData[i];
            sampleData[i] = newDeltaValue - deltaValue;
            deltaValue = newDeltaValue;
        }
    }

    
}
