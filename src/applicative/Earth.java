package applicative;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Earth {
    HashMap<Coordinates,geoZone> zoneList = new HashMap();

    //Constructor
    public Earth(ArrayList<Integer> coord,ArrayList<ArrayList<Float>> anomalies) {
        int j=0;
        for (int i=0; i<anomalies.size();i++) {
            Coordinates cods = new Coordinates(coord.get(j), coord.get(j+1));
            j+=2;
            geoZone geoZ = new geoZone(anomalies.get(i));
            zoneList.put(cods,geoZ);
        }
    }

    //Methodes

    public HashMap<Coordinates, geoZone> displayAllGeoZone(){


    //test de la lecture des fichiers
       /* for (Map.Entry mapentry : zoneList.entrySet()) {
            System.out.println("clé: "+mapentry.getKey()
                    + " | valeur: " + mapentry.getValue());

        }
        for (Coordinates key : zoneList.keySet()){
            System.out.println(zoneList.get(key).getTempList());
        }*/

        return zoneList;
    }

    public HashMap<Coordinates, geoZone> getZoneList() {
        return zoneList;
    }

    public double valeurMax() {
       double max=0.0;
        for (Coordinates key : zoneList.keySet()){
            for (int key1 : zoneList.get(key).getTempList().keySet()){
                if(zoneList.get(key).getTempList().get(key1).doubleValue()>max){
                    max = zoneList.get(key).getTempList().get(key1).doubleValue();
                }
            }
        }
        return max;
    }

    public double valeurMin(){
        double min=0.0;
        for (Coordinates key : zoneList.keySet()){
            for (int key1 : zoneList.get(key).getTempList().keySet()){
                if(zoneList.get(key).getTempList().get(key1).doubleValue()<min){
                    min = zoneList.get(key).getTempList().get(key1).doubleValue();
                }
            }
        }
        return min;
    }
    /*
    public HashMap<Coordinates,Float>getPYear(){

    };*/
}
