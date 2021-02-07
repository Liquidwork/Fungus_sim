public class FungusStatistic {
    public int fungusId; 
    //Basic value is the attributes of the fungi at 22deg, no humidity change.
    public double basicV;// Basic value of horizontal spreading speed
    public double basicX;// Basic value of Hyphal extention rate
    public double basicB;// Basic value of decomponent mass per unit area
    public double mTradeOff;// Moisture trade-off of this species
    public int totalCells = 0;
    public double totalDecom = 0;
    
    private boolean basic = false;

    public void record(Fungus f){
        if(this.basic == false){
            this.fungusId = f.fungusId;
            this.basicV = f.basicV;
            this.basicX = f.basicX;
            this.basicB = f.basicB;
            this.mTradeOff = f.mTradeOff;
        }
        this.totalCells++;
        this.totalDecom += f.decomp;
    }

    public String toString(){
        return String.format("%d,%e,%e,%e,%e,%d,%e", this.fungusId, this.basicV, this.basicX, this.basicB, this.mTradeOff,
                        this.totalCells, this.totalDecom);
    }
}
