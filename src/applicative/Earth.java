package applicative;

import java.util.ArrayList;
import java.util.HashMap;

public class Earth {
    HashMap<Coordinates,geoZone> zoneList = new HashMap();

    //Constructor
    public Earth(ArrayList<Integer> coord, ArrayList<ArrayList<Float>> anomalies) {
        Coordinates cods = new Coordinates(coord.get(0),coord.get(1));
        for(int i=0; i<anomalies.size();i++) {
            geoZone geo = new geoZone(anomalies);
        }
    }


    //Methodes

    public void displayAllGeoZone(){

    }

    /*  for (Coordinates key: zoneList.keySet()){
           System.out.println(zoneList.get(key));
        } */

    public float valeurMin(){
        float res =0.0f;
        return res;
    }

    public float valeurMax(){
        float res =0.0f;
        return res;
    }
/*
    public HashMap<Coordinates,Float>getPYear(){

    };*/
}
