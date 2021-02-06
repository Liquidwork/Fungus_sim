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
    }

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
     * All the fungi decomposite 1-day-quantity
     */
    public void decomposition(){
        //TODO: let all fungi composite
    }

    /**
     * Spread a cell if possible
     */
    private void spreadCell(int i, int j){
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

    
