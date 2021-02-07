import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Main class of simulations
 */
public class SimuMain {
    public int duration;
    public int size;
    public Map mapSim;
    private Date current;

    /**
     * New Simulation object
     * 
     * @param size     size of the map tobe initialized
     * @param duration the duration of whole test (unit: day)
     */
    public SimuMain(int size, int duration, Climate climate) {
        this.duration = duration;
        this.size = size;
        mapSim = new Map(size, climate);
    }

    public void start() {
        this.current = new Date();
        for (int i = 0; i < duration; i++) {
            this.mapSim.spread(); // a step of simulation
            if (i % 10 == 9)
                this.saveProgress(i);
        }
        this.saveStatistic();
        this.saveTimeSerie();
        
    }

    /**
     * Put data into file, including:
     * <ul>
     * <li>boime map,</li>
     * <li>total decomposition map.</li>
     * </ul>
     * All file will be place in <i>".\\data\\dd_hh_mm_ss\\"</i> folder. <br>
     * 
     * @param day input the day that data representing
     */
    public void saveProgress(int day) {
        SimpleDateFormat ft = new SimpleDateFormat("dd_hh_mm_ss"); // Date formatting
        File parentDir = new File(".\\data"); // Create data folder if not exists
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        String path = ".\\data\\" + ft.format(this.current);
        File directory = new File(path);
        directory.mkdir(); // Create test_data folder if not exists
        File biomeFile = new File(path + "\\day" + day + "biome.csv");
        File decomFile = new File(path + "\\day" + day + "decom.csv");
        try {
            biomeFile.createNewFile();// Create the file
            decomFile.createNewFile();
            PrintWriter biomeOut = new PrintWriter(biomeFile);
            PrintWriter decomOut = new PrintWriter(decomFile);
            for (int i = 0; i < this.mapSim.map.length; i++) {
                String biomeLine = "";
                String decomLine = "";
                for (int j = 0; j < this.mapSim.map[0].length; j++) {
                    if (this.mapSim.map[i][j] == null) {
                        biomeLine = biomeLine.concat(" ,");
                        decomLine = decomLine.concat("0,");
                    } else {
                        biomeLine = biomeLine.concat(this.mapSim.map[i][j].fungusId + ",");
                        decomLine = decomLine.concat(this.mapSim.map[i][j].decomp + ",");
                    }
                }
                biomeOut.println(biomeLine.substring(0, biomeLine.length() - 1)); // Delete last comma
                decomOut.println(decomLine.substring(0, decomLine.length() - 1)); // Delete last comma
            }
            biomeOut.close();
            decomOut.close();
        } catch (Exception e) {
            System.err.println("Error at Day " + day);
            e.printStackTrace();
        }

    }

    /**
     * Save statistic file with all species global data, including
     * <ul>
     * <li>fungus id</li>
     * <li>basic v</li>
     * <li>basic x</li>
     * <li>basic B</li>
     * <li>Moisture trade-off</li>
     * <li>total cells</li>
     * <li>total decomposition</li>
     * </ul>
     */
    public void saveStatistic() {
        int count = Fungus.fungiCount;
        FungusStatistic fs[] = new FungusStatistic[count];
        for (int i = 0; i < count; i++) {
            fs[i] = new FungusStatistic();
        }
        for (int i = 0; i < this.mapSim.map.length; i++) {
            for (int j = 0; j < this.mapSim.map[0].length; j++) {
                if (this.mapSim.map[i][j] == null)
                    continue;
                fs[this.mapSim.map[i][j].fungusId].record(this.mapSim.map[i][j]);
            }
        }
        SimpleDateFormat ft = new SimpleDateFormat("dd_hh_mm_ss"); // Date formatting
        String path = ".\\data\\" + ft.format(this.current);
        File file = new File(path + "\\statistic.csv");
        try {
            file.createNewFile();
            PrintWriter out = new PrintWriter(file);
            out.println("id,v,x,B,mtradeoff,cells,decom");
            for (int i = 0; i < count; i++) {
                out.println(fs[i]); // A proper toString method is overriden
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a time serie with fungi data of each species, each day
     */
    public void saveTimeSerie(){
        SimpleDateFormat ft = new SimpleDateFormat("dd_hh_mm_ss"); // Date formatting
        String path = ".\\data\\" + ft.format(this.current);
        File file = new File(path + "\\time_series.csv");
        try {
            file.createNewFile();
            PrintWriter out = new PrintWriter(file);
            while(!this.mapSim.boimeData[0].isEmpty()){ //read all time data
                String line = "";
                for(int i=0; i < this.mapSim.boimeData.length; i++){ //poll a round and a round seperately
                    double count = this.mapSim.boimeData[i].poll()[0];
                    line = line.concat(count + ",");
                }
                out.println(line.substring(0, line.length() - 1)); //delete last comma and print
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static void main(String[] args) throws IOException {
        
        SimuMain simu = new SimuMain(400, 100, new FileClimate("RomeTem.csv", "RomeHum.csv", 91));
        simu.start();
        double totalDecom = 0;
        for (int i=0; i < simu.mapSim.map.length; i++){
            for (int j=0; j < simu.mapSim.map[0].length; j++){
                if(simu.mapSim.map[i][j] == null) continue;
                totalDecom += simu.mapSim.map[i][j].decomp;
            }
        }
        System.out.println(totalDecom);
    }
}