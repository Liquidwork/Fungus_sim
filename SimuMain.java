import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimuMain{
/*first try*/
    public int duration;
    public int size;
    public Map mapSim;

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
            this.mapSim.spread();
            if(i%2==1) this.saveProgress(i);
        }
    }

    public void saveProgress(int day){
        Date current = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("dd_hh_mm_ss");
        File parentDir = new File(".\\data");
        if(!parentDir.exists()){
            parentDir.mkdirs();
        }
        String path = ".\\data\\" + ft.format(current);
        File directory = new File(path);
        directory.mkdir();
        File file = new File(path + "\\day" + day + ".csv");
        try {
            file.createNewFile();
            PrintWriter out = new PrintWriter(file);
            for (int i=0; i < this.mapSim.map.length; i++){
                for (int j=0; j < this.mapSim.map[0].length; j++){
                    if(this.mapSim.map[i][j] == null){
                        out.print(" ,");
                    }else{
                        out.print(this.mapSim.map[i][j].fungusId + ",");
                    }
                }
                out.println();
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static void main(String[] args) {
        SimuMain simu = new SimuMain(60, 10);
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