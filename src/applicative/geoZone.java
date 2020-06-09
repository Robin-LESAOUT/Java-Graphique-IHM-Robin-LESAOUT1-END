package applicative;

import java.util.HashMap;

public class geoZone {
    //Attributs
    HashMap<Integer,Double> tempList = new HashMap();

    //Constructeur
    public geoZone(HashMap<Integer, Double> tempList) {
        this.tempList = tempList;
    }

    //Getters & Setters
    public HashMap<Integer, Double> getTempList() {
        return tempList;
    }
}
