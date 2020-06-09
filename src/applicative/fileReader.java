package applicative;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class fileReader {
    public static void getDataFromCSVFile(ArrayList<Integer> coord, ArrayList<ArrayList<Float>>anomalies ){

        try {
            FileReader file = new FileReader("src/applicative/tempanomaly_4x4grid.csv");
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

                //creation array;
                ArrayList<Float> val = new ArrayList<Float>();

                //ajout des anomalies dans l'ordre (premiere colonne correspondant à 1880)
                for (int i=2 ; i<140 ; i++) {
                    if(array[i].equals("NA")==false) {
                        val.add(Float.parseFloat(array[i]));
                        anomalies.add(val);
                    }
                }

                //envoi de mes datas vers les classes
                coord.add(lat);
                coord.add(lon);

                line = bufRead.readLine();
            }

            bufRead.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
