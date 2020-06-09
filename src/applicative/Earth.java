package applicative;

import java.util.ArrayList;
import java.util.HashMap;

public class Earth {
    HashMap<Coordinates,geoZone> zoneList = new HashMap();

    //Constructor
    public Earth(ArrayList<Integer> coord) {
        Coordinates cods = new Coordinates(coord.get(0),coord.get(1));
    }


    //Methodes

    public void displayAllGeoZone(){
        for (Coordinates key: zoneList.keySet()){
           System.out.println(zoneList.get(key));
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
