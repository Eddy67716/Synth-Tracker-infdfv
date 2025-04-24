/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.formats;

/**
 *
 * @author eddy6
 */
public class GenericEnvelopeSpec {
    
    // constants
    public static final GenericEnvelopeSpec EMPTY_ENVELOPE 
            = new GenericEnvelopeSpec();
    
    // instance variabels
    private double delayTime;
    private double attackTime;
    private double lowVolumeAttackTime;
    private double holdTime;
    private double holdKeyWeight;
    private double decayTime;
    private double decayKeyWeight;
    private boolean sustained;
    private double sustainAmplitude;
    private double releaseTime;
    
    // 7 args constructor
    public GenericEnvelopeSpec(double delayTime, double attackTime, 
            double holdTime, double decayTime, boolean sustained,
            double sustainAmplitude, double releaseTime) {
        this.delayTime = delayTime;
        this.attackTime = attackTime;
        this.holdTime = holdTime;
        this.decayTime = decayTime;
        this.sustained = sustained;
        this.sustainAmplitude = sustainAmplitude;
        this.releaseTime = releaseTime;
    }
    
    // 6 args constructor
    public GenericEnvelopeSpec(double delayTime, double attackTime, 
            double holdTime, double decayTime, double sustainAmplitude, 
            double releaseTime) {
        this(delayTime, attackTime, holdTime, decayTime, true, 
                sustainAmplitude, releaseTime);
    }
    
    // 5 args constructor
    public GenericEnvelopeSpec(double attackTime, double decayTime, 
            boolean sustained, double sustainAmplitude, double releaseTime) {
        this(0, attackTime, 0, decayTime, sustained, sustainAmplitude, 
                releaseTime);
    }
    
    // 4 args constructor
    public GenericEnvelopeSpec(double attackTime, double decayTime, 
            double sustainAmplitude, double releaseTime) {
        this(0, attackTime, 0, decayTime, sustainAmplitude, releaseTime);
    }
    
    // ges constructor
    public GenericEnvelopeSpec(GenericEnvelopeSpec ges) {
        this.delayTime = ges.delayTime;
        this.attackTime = ges.attackTime;
        this.lowVolumeAttackTime = ges.lowVolumeAttackTime;
        this.holdTime = ges.holdTime;
        this.holdKeyWeight = ges.holdKeyWeight;
        this.decayTime = ges.decayTime;
        this.decayKeyWeight = ges.decayKeyWeight;
        this.sustained = ges.sustained;
        this.sustainAmplitude = ges.sustainAmplitude;
        this.releaseTime = ges.releaseTime;
    }
    
    // empty envelope constructor
    public GenericEnvelopeSpec() {
        this(0, 0, 1, 0);
    }
    
    // getters
    public double getDelayTime() {
        return delayTime;
    }

    public double getAttackTime() {
        return attackTime;
    }

    public double getLowVolumeAttackTime() {
        return lowVolumeAttackTime;
    }

    public double getHoldTime() {
        return holdTime;
    }

    public double getHoldKeyWeight() {
        return holdKeyWeight;
    }

    public double getDecayTime() {
        return decayTime;
    }

    public double getDecayKeyWeight() {
        return decayKeyWeight;
    }

    public double getHoldDecayWeight() {
        return decayKeyWeight;
    }

    public boolean isSustained() {
        return sustained;
    }

    public double getSustainAmplitude() {
        return sustainAmplitude;
    }

    public double getReleaseTime() {
        return releaseTime;
    }
    
    // setters
    public void setDelayTime(double delayTime) {
        this.delayTime = delayTime;
    }

    public void setAttackTime(double attackTime) {
        this.attackTime = attackTime;
    }

    public void setLowVolumeAttackTime(double lowVolumeAttackTime) {
        this.lowVolumeAttackTime = lowVolumeAttackTime;
    }

    public void setHoldTime(double holdTime) {
        this.holdTime = holdTime;
    }

    public void setHoldKeyWeight(double holdKeyWeight) {
        this.holdKeyWeight = holdKeyWeight;
    }

    public void setDecayTime(double decayTime) {
        this.decayTime = decayTime;
    }

    public void setDecayKeyWeight(double decayKeyWeight) {
        this.decayKeyWeight = decayKeyWeight;
    }

    public void setSustained(boolean sustained) {
        this.sustained = sustained;
    }

    public void setSustainAmplitude(double sustainAmplitude) {
        this.sustainAmplitude = sustainAmplitude;
    }

    public void setReleaseTime(double releaseTime) {
        this.releaseTime = releaseTime;
    }
    
    
    // equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GenericEnvelopeSpec other = (GenericEnvelopeSpec) obj;
        if (Double.doubleToLongBits(this.delayTime) 
                != Double.doubleToLongBits(other.delayTime)) {
            return false;
        }
        if (Double.doubleToLongBits(this.attackTime) 
                != Double.doubleToLongBits(other.attackTime)) {
            return false;
        }
        if (Double.doubleToLongBits(this.holdTime) 
                != Double.doubleToLongBits(other.holdTime)) {
            return false;
        }
        if (Double.doubleToLongBits(this.holdKeyWeight) 
                != Double.doubleToLongBits(other.holdKeyWeight)) {
            return false;
        }
        if (Double.doubleToLongBits(this.decayTime) 
                != Double.doubleToLongBits(other.decayTime)) {
            return false;
        }
        if (Double.doubleToLongBits(this.decayKeyWeight) 
                != Double.doubleToLongBits(other.decayKeyWeight)) {
            return false;
        }
        if (this.sustained != other.sustained) {
            return false;
        }
        if (Double.doubleToLongBits(this.sustainAmplitude) 
                != Double.doubleToLongBits(other.sustainAmplitude)) {
            return false;
        }
        return Double.doubleToLongBits(this.releaseTime) 
                == Double.doubleToLongBits(other.releaseTime);
    }
}
