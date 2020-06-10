package applicative;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class geoZone {
    //Attributs
    HashMap<Integer,Double> tempList = new HashMap();
    ArrayList<Integer> Year = new ArrayList<Integer>();

    public geoZone(ArrayList<Float> anomal) {
        for(int i=1880; i<=2020; i++){
            Year.add(i);
        }
            for (int j = 0; j <anomal.size() ; j++) {
                tempList.put(Year.get(j),(double)anomal.get(j));
            }
    }


    //Getters & Setters
    public HashMap<Integer, Double> getTempList() {
        return tempList;
    }
}
