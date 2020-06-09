package applicative;

import java.util.ArrayList;
import java.util.HashMap;

public class geoZone {
    //Attributs
    HashMap<Integer,Double> tempList = new HashMap();
    ArrayList<Integer> Year = new ArrayList<Integer>();

    //Constructeur
    public geoZone(ArrayList<ArrayList<Float>> anomalies) {
        for(int i=1880; i<=2020; i++){
            Year.add(i);
        }
        for(int i =0; i<=140 ;i++) {
            for (int j = 0; j < anomalies.size(); j++) {
                tempList.put(Year.get(i), (double) anomalies.get(i).get(j));
            }
        }
    }

    //Getters & Setters
    public HashMap<Integer, Double> getTempList() {
        return tempList;
    }
}
