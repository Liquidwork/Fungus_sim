/**
 * Fungus class
 * Cloneable as it need to spread by cloning(mitosis)
 */
public class Fungus implements Cloneable{
    public static int fungiCount = 0;

    public int fungusId; 
    public double basicV;// Basic value of horizontal spreading speed
    public double basicB;// Basic value of decomponent mass per unit area
    public double mTradeOff;// Moisture trade-off of this species
    public double spreadProgress = 0;
    public double decomp = 0;// total decomposition of fungus on this area
    /**
     * Generate a random species
     */
    public Fungus(){
        this.fungusId = fungiCount++;// Generate an id for this new species, add the count
        //TODO: Generate a random species here
    }

    private Fungus(Fungus f){
        this.basicB = f.basicB;
        this.basicV = f.basicV;
        this.decomp = f.decomp;
        this.fungusId = f.fungusId;
    }

    public Fungus clone(){
        return new Fungus(this);
    }

    public void grow(double[] climate){

    }
}
