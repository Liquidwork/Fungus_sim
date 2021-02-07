import java.util.Random;

/**
 * Fungus class Cloneable as it need to spread by cloning(mitosis)
 */
public class Fungus implements Cloneable{
    public static int fungiCount = 0;
    private static double lastTemp;
    private static double tempPenalty;
    private static final double[] A ={0.3769, 0.5144, 0.2450};  //Several Constants for temperature penalty
    private static final double[] B ={0.3311, 0.05467, -0.4457};
    private static final double[] C ={0.2746, 0.5334, 0.9016};

    public int fungusId; 
    //Basic value is the attributes of the fungi at 22deg, no humidity change.
    public double basicV;// Basic value of horizontal spreading speed
    public double basicX;// Basic value of Hyphal extenrion rate
    public double basicB;// Basic value of decomponent mass per unit area
    public double mTradeOff;// Moisture trade-off of this species
    public double spreadProgress = 0;
    public double decomp = 0;// total decomposition of fungus on this area
    public boolean spreaded = false;
    

    /**
     * Generate a random species
     */
    public Fungus(){
        this.fungusId = fungiCount++;// Generate an id for this new species, add the count
        this.basicV = generateBasicV();        //Generate a horizontal spreading speed
        this.basicX = generateBasicX();        //Generate a Hyphal extenrion rate
        this.mTradeOff = generateMTradeOff();  //Generate a Moisture trade-off factor
        this.basicB = generatebasicB(this.basicX);        //Calculate a Basic value of decomponent mass per unit area
    }

    /**
     * Clone a fungus from another one
     * @param f fungi to be clone
     */
    private Fungus(Fungus f){
        this.basicB = f.basicB;
        this.basicV = f.basicV;
        this.basicX = f.basicX;
        this.mTradeOff = f.mTradeOff;
        this.fungusId = f.fungusId;
    }

    @Override
    public Fungus clone(){
        return new Fungus(this);
    }

    /**
     * Let the fungi grow, adding the spread progress while decompositing
     * @param climate the array representing environment including temperature and humidity changes
     */
    public void grow(double[] climate){
        double penalty = tempPenalty(climate[0]) * humidityPenalty(climate[1], this.mTradeOff);
        if(this.spreaded == false){
            this.spreadProgress += this.basicV * penalty;
        }
        //TODO: Decomposition increment
        this.decomp += this.basicB;
    }

    //Generation Methods:
    /*Generate a horizontal spreading speed with Normal distribution mean = 1 Variance = 0.15*/
    private static double generateBasicV()
    {
        double mean = 3.0;
        double deviation = 0.15;
        double basicV = deviation * Map.random.nextGaussian() + mean;
        return basicV;
    }
    /*Generate a Hyphal extenrion rate with Uniform distribution from 1 to 10*/
    private static double generateBasicX()
    {
        double basicX = 9.0*Map.random.nextDouble() + 1.0;
        return basicX;
    }
    /*Generate a Moisture trade-off factor with Uniform distribution from -1 to 1*/
    private static double generateMTradeOff()
    {
        double mTradeOff = 2.0*Map.random.nextDouble() - 1;
        return mTradeOff;
    }
    /*Calculate a Basic value of decomponent mass per unit area by basicX*/
    private static double generatebasicB(double basicX)
    {
       double basicB;
       double x = basicX;
       basicB = 3 * Math.log(x+1)/Math.PI;
       return basicB;
    }

    /**
     * Get temperature penalty. It is noted that the method is memoriable but idempotent.
     * @param temp temperature
     * @return the tempPenalty
     */
    public static double tempPenalty(double temp){
        if (temp == lastTemp) return tempPenalty;
        lastTemp = temp;
        temp = 0.0629723 * (-22.5 + temp);
        double result = 0;
        for(int i=0; i<3; i++){
            result += A[i] * Math.exp(-Math.pow((temp-B[i])/C[i], 2));
        }
        tempPenalty = result / 0.765457;
        return tempPenalty;
    }

    /**
     * 
     * @param humidity humidity change, it is an environment factor
     * @param mTradeOff Moisture trade off, it is defined by the fungus
     * @return The humidity penalty
     */
    public static double humidityPenalty(double humidity, double mTradeOff){
        double result = Math.exp(0.233 * (1 - 2 * humidity) * mTradeOff + 1.8) / 6.04965;
        return result;
    }
    

}
