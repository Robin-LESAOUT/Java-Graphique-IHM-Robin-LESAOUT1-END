package applicative;

import java.util.ArrayList;
import java.util.HashMap;

public class Earth {
    HashMap<Coordinates,geoZone> zoneList = new HashMap();

    //Constructor
    public Earth(ArrayList<Integer> coord,ArrayList<ArrayList<Float>> anomalies) {

        for (int i=0 ; i<anomalies.size();i++) {
            Coordinates cods = new Coordinates(coord.get(i), coord.get(i+1));
            geoZone geoZ = new geoZone(anomalies.get(i));
            zoneList.put(cods,geoZ);
        }
    }

    //Methodes

    public HashMap<Coordinates, geoZone> displayAllGeoZone(){
        for (Coordinates key : zoneList.keySet()) {
           System.out.println(zoneList.get(key).getTempList());
        }
        return zoneList;
    }

    public HashMap<Coordinates, geoZone> getZoneList() {
        return zoneList;
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
