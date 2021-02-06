/**
 * A climate environment that will not change
 */
public class ConstantClimate extends Climate{

    public double[] climate = new double[2];
    /**
     * Constructor of ConstentClimate
     * @param temp constent temperature
     * @param humunity constent humunity change
     */
    public ConstantClimate(double temp, double humunity){
        this.climate[0] = temp;
        this.climate[1] = humunity;
    }

    public double[] getClimate(){
        return this.climate;
    }
    
}
