package applicative;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class geoZone {
    //Attributs

    LinkedHashMap<Integer,Double> tempList = new LinkedHashMap();
    ArrayList<Integer> Year = new ArrayList<Integer>();

    //Constructeur

    /*
     *   Constructeur de classe qui permet de créer les zones géographiques et les ajouter dans la hashmap correspondante.
     *   @param anomal
     *          pris par référence via le filereader
     *
     */
    public geoZone(ArrayList<Float> anomal) {
        for(int i=1880; i<=2020; i++){
            Year.add(i);
        }
            for (int j = 0; j <anomal.size() ; j++) {
                tempList.put(Year.get(j),(double)anomal.get(j));
            }
    }


    //Getters & Setters
    public LinkedHashMap<Integer, Double> getTempList() {
        return tempList;
    }
}
