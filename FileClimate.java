import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class FileClimate extends Climate {

    public int date = 0;
    public Queue<double[]> pairs;// climate pair, {temp, humidity}
    /**
     * Initialize a climate class from 2 files exported from Mathematica.
     * The climate file should be put into climate folder.
     * @param temFileName Filename of temperature file 
     * @param humFileName Filename of humidity file 
     * @param beginDate it will generate climate data from this day
     */
    public FileClimate(String temFileName, String humFileName, int beginDate) throws IOException{
        this.pairs = new LinkedList<double[]>();
        double[] pair = {0, 0};
        String temLine, humLine;
        File tem = new File(".\\climate\\" + temFileName);
        File hum = new File(".\\climate\\" + humFileName);
        BufferedReader temReader = new BufferedReader(new FileReader(tem));
        BufferedReader humReader = new BufferedReader(new FileReader(hum));
        for (int i = beginDate; i > 0; i--){
            temReader.readLine();
            humReader.readLine();
        }
        while((temLine = temReader.readLine()) != null && (humLine = humReader.readLine()) != null){
            pair[0] = Double.parseDouble(temLine.split("Quantity[")[1].split(",")[0]); //Get the number
            pair[1] = Double.parseDouble(humLine.split(", 8.]\",")[1]); //Get the number
            this.pairs.offer(pair);
        }
        temReader.close();
        humReader.close();
    }

    public FileClimate(String temFileName, String humFileName) throws IOException{
        this(temFileName, humFileName, 0);
    }

    @Override
    public double[] getClimate() {
        if(this.pairs.size()==1) this.pairs.offer(this.pairs.peek()); // Out put the last one if there is no more data available
        return this.pairs.poll();
    }
}
