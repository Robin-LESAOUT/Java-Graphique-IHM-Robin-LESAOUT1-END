package applicative;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class fileReader {
    public static void getDataFromCSVFile(){

        try {
            FileReader file = new FileReader("applicative.tempanomaly_4x4grid.csv");
            BufferedReader bufRead = new BufferedReader(file);

            //read the first line
            String line = bufRead.readLine();

            //Read the file line by line
            while ( line != null) {

                String[] array = line.split(",");

                //get the latitude
                int lat = Integer.parseInt(array[0]);

                //get the longitude
                int lon = Integer.parseInt(array[1]);

                //création array;
                ArrayList<Float> val = new ArrayList<Float>();

                for (int i=2 ; i<140 ; i++) {
                    val.add(Float.parseFloat(array[i]));
                }

                line = bufRead.readLine();
            }

            bufRead.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
