import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * This is a map that conducts the simulation of fungi spreading model.
 */

public class Map {
    public Fungus[][] map;
    public Climate climate;
    private Queue<int[]> spreadable = new LinkedList<>(); //A queue to implement the spreading
    /**
     * Initialize a map with a preset size.
     * @param size the size of the map
     * @param climate the climate of the test
     */
    public Map(int size, Climate climate){
        map = new Fungus[size][size];
        this.climate = climate;
        Random random = new Random();
        int i = 0;
        int numOfFungi = random.nextInt(5) + 1; //Randomly determine how many fungi species(between 1-5)  in this experiment 
        int xAxis, yAxis;
        for(i=0; i < numOfFungi; i++) //Generate different fungi species
        {   
            do{
                xAxis = random.nextInt(size); //generate the initial location on x-Axis of a species of fungi(between 0 to size-1)
                yAxis = random.nextInt(size); //generate the initial location on y-Axis of a species of fungi(between 0 to size-1)
            }while(map[xAxis][yAxis] != null); //check if the location alreay be taken
            
            map[xAxis][yAxis] = new Fungus();//Put in a type of fungi
        }
    }

    /**
     * Spread and decomposition all the fungus biomes in this map.
     */
    public void spread(){
        double[] climate = this.climate.getClimate();
        for (int i=0; i < map.length; i++){
            for (int j=0; j < map[0].length; j++){
                if(map[i][j] == null) continue;
                map[i][j].grow(climate); // gain spread progress and decompositing
                if(map[i][j].spreadProgress >= 1){ // if spreadable
                    int[] pos = {i, j};
                    spreadable.offer(pos); // Record the spreadable cells position
                }
            }
        }
        while(!spreadable.isEmpty()){ //Spread all the cells which is spreadable, the queue may extend itself while spreading
            int[] pos = spreadable.poll();
            spreadCell(pos[0], pos[1]);
        }
    }

    /**
     * Spread a cell if possible.
     * It may offer several new elements into the spreadable queue if the new cell is still spreadable (process > 1).
     * @param i cell coordinate i
     * @param j cell coordinate j
     */
    private void spreadCell(int i, int j){
        double savedProcess = (int)map[i][j].spreadProgress - 1;
        map[i][j].spreadProgress = 0; map[i][j].spreaded = true; // Useful for optimization as no species can be removed or die out
        //Test 6 spreading direction
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
        if(i != map.length-1 && j != map[0].length-1 && this.map[i+1][j+1] == null){
            this.fungiClone(i, j, i+1, j+1, savedProcess);
        }
    }
    /**
     * A method called in spreadCell.
     * Clone a fungi into another place, saved process can be introduced.
     * It may offer several new elements into the spreadable queue if the new cell is still spreadable (process > 1).
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

    
