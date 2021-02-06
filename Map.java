import java.util.LinkedList;
import java.util.Queue;

/**
 * This is a map that conducts the simulation of fungi spreading model.
 */

public class Map {
    public Fungus[][] map;
    public Climate climate;
    private Queue<int[]> spreadable = new LinkedList<>(); //A queue to implement the spreading model
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
                    int[] pos = {i, j};
                    spreadable.offer(pos); // Record the spreadable cells
                }
            }
        }
        while(!spreadable.isEmpty()){ //Spread all the cells which is spreadable, the queue may extend itself while spreading
            int[] pos = spreadable.poll();
            spreadCell(pos[0], pos[1]);
        }
    }

    /**
     * Spread a cell if possible
     * It may offer several new elements into the spreadable queue if the new cell is still spreadable
     * @param i cell coordinate i
     * @param j cell coordinate j
     */
    private void spreadCell(int i, int j){
        double savedProcess = (int)map[i][j].spreadProgress - 1;
        map[i][j].spreadProgress = 0; map[i][j].spreaded = true;
        if(i != 0 && this.map[i-1][j] == null){
            this.fungiClone(i, j, i-1, j, savedProcess);
        }
        if(j != 0 && this.map[i][j-1] == null){
            this.fungiClone(i, j, i, j-1, savedProcess);
        }
        if(i != 0 && j != 0 && this.map[i-1][j-1] == null){
            this.fungiClone(i, j, i-1, j-1, savedProcess);
        }
        if(i != map.length-1 && this.map[i+1][j] == null){
            this.fungiClone(i, j, i+1, j, savedProcess);
        }
        if(j != map[0].length-1 && this.map[i][j+1] == null){
            this.fungiClone(i, j, i, j+1, savedProcess);
        }
        if(i != map.length-1 && j != map[0].length && this.map[i+1][j+1] == null){
            this.fungiClone(i, j, i+1, j+1, savedProcess);
        }
    }
    /**
     * A method called in spreadCell
     * @param i coordinate i being cloned
     * @param j coordinate j being cloned
     * @param newI coordinate i to be cloned into
     * @param newJ coordinate j to be cloned into
     * @param savedProcess a saved 
     */
    private void fungiClone(int i, int j, int newI, int newJ, double savedProcess){
        this.map[newI][newJ] = (Fungus)this.map[i][j].clone();
        this.map[newI][newJ].spreadProgress = savedProcess;
        if(savedProcess >= 1){  // If the cell is still spreadable
            int[] pos = {newI, newJ};
            spreadable.offer(pos); // Add new spreadable into the queue
        }
    }
}

    
