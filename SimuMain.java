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
        }
    }

    public void saveProgress(){
        
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