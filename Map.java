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
        //TODO: spread each fungus biomes
    }

    /**
     * All the fungi decomposite 1-day-quantity
     */
    public void decomposition(){
        //TODO: let all fungi composite
    }
}
