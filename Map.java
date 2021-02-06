/**
 * This is a map that conducts the simulation of fungi spreading model.
 */

public class Map {
    Fungus[][] map;
    Climate climate;
    /**
     * Initialize a map with a preset size
     * @param size the size of the map
     * @param climate the climate of the test
     */
    public Map(int size, Climate climate){
        map = new Fungus[size][size];
        this.climate = climate;
        //TODO: generate several different spiecies randomly at the map
        Random random = new Random();
        int i = 0;
        int numOfFungi = random.nextInt(5) + 1; //Randomly determine how many fungi species(between 1-5)  in this experiment 
        for(i=0; i<numOfFungi; i++) //Generate different fungi species
        {   
            do{
            xAxis = random.nextInt(size); //generate the initial location on x-Axis of a species of fungi(between 0 to size-1)
            yAxis = random.nextInt(size); //generate the initial location on y-Axis of a species of fungi(between 0 to size-1)
            }while(map[xAxis][yAxis] != null) //check if the location alreay be taken
            
            map[xAxis][yAxis] = new Fungus();//Put in a type of fungi


        }




    }

    /**
     * Spread and decomposition all the fungus biomes in this map
     */
    public void spread(){
        double[] climate = this.climate.getClimate();
        for (int i=0; i < map.length; i++){
            for (int j=0; j < map[0].length; j++){
                if(map[i][j] == null) continue;
                map[i][j].grow(climate);
                if(map[i][j].spreadProgress >= 1){
                    this.spreadCell(i, j);
                    map[i][j].spreadProgress -= 1.;
                }
            }
        }
    }

    /**
     * Spread a cell if possible
     */
    private void spreadCell(int i, int j){
        int savedProcess = (int)map[i][j].spreadProgress - 1;
        if(i != 0){
            this.map[i-1][j] = this.map[i-1][j] == null ? (Fungus)this.map[i][j].clone() :  this.map[i-1][j];
            
        }
        if(j != 0){
            this.map[i][j-1] = this.map[i][j-1] == null ? (Fungus)this.map[i][j].clone() :  this.map[i][j-1];
        }
        if(i != 0 && j != 0){
            this.map[i-1][j-1] = this.map[i-1][j-1] == null ? (Fungus)this.map[i][j].clone() :  this.map[i-1][j-1];
        }
        if(i != map.length-1){
            this.map[i+1][j] = this.map[i+1][j] == null ? (Fungus)this.map[i][j].clone() :  this.map[i+1][j];
        }
        if(j != map[0].length-1){
            this.map[i][j+1] = this.map[i][j+1] == null ? (Fungus)this.map[i][j].clone() :  this.map[i][j+1];
        }
        if(i != map.length-1 && j != map[0].length-1){
            this.map[i+1][j+1] = this.map[i+1][j+1] == null ? (Fungus)this.map[i][j].clone() :  this.map[i+1][j+1];
        }
    }
}

    
