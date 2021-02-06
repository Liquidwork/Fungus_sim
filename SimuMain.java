import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimuMain{
/*first try*/
    public int duration;
    public int size;
    public Map mapSim;
    private Date current;

    /**
     * New Simulation object
     * @param size
     * @param duration
     */
    public SimuMain(int size, int duration){
        this.duration = duration;
        this.size = size;
        mapSim = new Map(size, new ConstantClimate(22, 0));
    }

    public void start(){
        for (int i=0; i < duration; i++){
            current = new Date();
            this.mapSim.spread();
            if(i%5 == 4) this.saveProgress(i);
        }
    }

    public void saveProgress(int day){
        SimpleDateFormat ft = new SimpleDateFormat ("dd_hh_mm_ss");
        File parentDir = new File(".\\data");
        if(!parentDir.exists()){
            parentDir.mkdirs();
        }
        String path = ".\\data\\" + ft.format(this.current);
        File directory = new File(path);
        directory.mkdir();
        File biomeFile = new File(path + "\\day" + day + "biome.csv");
        File decomFile = new File(path + "\\day" + day + "decom.csv");
        try {
            biomeFile.createNewFile();
            decomFile.createNewFile();
            PrintWriter biomeOut = new PrintWriter(biomeFile);
            PrintWriter decomOut = new PrintWriter(decomFile);
            for (int i=0; i < this.mapSim.map.length; i++){
                String biomeLine = "";
                String decomLine = "";
                for (int j=0; j < this.mapSim.map[0].length; j++){
                    if(this.mapSim.map[i][j] == null){
                        biomeLine = biomeLine.concat(" ,");
                        decomLine = decomLine.concat("0,");
                    }else{
                        biomeLine = biomeLine.concat(this.mapSim.map[i][j].fungusId + ",");
                        decomLine = decomLine.concat(this.mapSim.map[i][j].decomp + ",");
                    }
                }
                biomeOut.println(biomeLine.substring(0, biomeLine.length()-1)); //Delete last comma
                decomOut.println(decomLine.substring(0, decomLine.length()-1)); //Delete last comma
            }
            biomeOut.close();
            decomOut.close();
        } catch (Exception e) {
            System.err.println("Day" + day);
            e.printStackTrace();
        }
        
    }

    public static void main(String[] args) {
        SimuMain simu = new SimuMain(200, 20);
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