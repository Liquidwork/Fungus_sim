/**
 * Fungus class
 * Cloneable as it need to spread by cloning(mitosis)
 */
public class Fungus implements Cloneable{
    public static int fungiCount = 0;

    public int fungusId; 
    public double basicV;// Basic value of horizontal spreading speed
    public double basicX;// Basic value of Hyphal extenrion rate
    public double basicB;// Basic value of decomponent mass per unit area
    public double mTradeOff;// Moisture trade-off of this species
    public double spreadProgress = 0;
    public double decomp;// total decomposition of fungus on this area
    /**
     * Generate a random species
     */
    /*Generate a horizontal spreading speed with Normal distribution mean = 1 Variance = 0.15*/
    public double generateBasicV()
    {
        double mean = 1.0;
        double variance = 0.15;
        Random random = new Random();
        double basicV = Math.sqrt(variance)*random.nextGaussian() + mean;
        return basicV;
    }
    /*Generate a Hyphal extenrion rate with Uniform distribution from 1 to 10*/
    public double generateBasicX()
    {
        double basicX = 9.0*Math.random() + 1.0;
        return basicX;
    }
    /*Generate a Moisture trade-off factor with Uniform distribution from -1 to 1*/
    public double generateMTradeOff()
    {
        double mTradeOff = 2.0*Math.random() - 1;
        return mTradeOff;
    }
    /*Calculate a Basic value of decomponent mass per unit area*/
    public double generatebasicB()
    {
       double basicB;
       double x = this.basicX;
       basicB = 3 * Math.log(x+1)/Math.PI;
    }

    public Fungus(){
        this.fungusId = fungiCount++;// Generate an id for this new species, add the count
        //TODO: Generate a random species here
        this.basicV = this.generateBasicV();        //Generate a horizontal spreading speed
        this.basicX = this.generateBasicX();        //Generate a Hyphal extenrion rate
        this.mTradeOff = this.generateMTradeOff();  //Generate a Moisture trade-off factor
        this.basicB = this.generatebasicB();        //Calculate a Basic value of decomponent mass per unit area
    }
}
