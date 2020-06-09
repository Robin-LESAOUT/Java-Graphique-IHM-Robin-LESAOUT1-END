package applicative;

import java.util.ArrayList;
import java.util.HashMap;

public class Earth {
    HashMap<Coordinates,geoZone> zoneList = new HashMap();


    //Constructor
    public Earth(ArrayList<Integer> coord,ArrayList<ArrayList<Float>> anomalies) {
        Coordinates cods = new Coordinates(coord.get(0),coord.get(1));
        geoZone geoZ = new geoZone(anomalies);
        zoneList.put(cods,geoZ);
    }


    //Methodes

    public void displayAllGeoZone(ArrayList<ArrayList<Float>> anomalies){
        for (Coordinates key: zoneList.keySet()){
            for(int i=0 ; i<anomalies.size();i++){
                System.out.println(zoneList.get(key).getTempList().get(i));

            }

        }
    }



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
