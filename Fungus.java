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
    public double decomp;// total decomposition of fungus on this area
    /**
     * Generate a random species
     */
    public Fungus(){
        this.fungusId = fungiCount++;// Generate an id for this new species, add the count
        //TODO: Generate a random species here
    }
}
