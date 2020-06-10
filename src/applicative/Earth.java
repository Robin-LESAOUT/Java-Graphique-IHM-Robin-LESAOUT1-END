package applicative;

import java.util.*;

public class Earth {
    LinkedHashMap<Coordinates,geoZone> zoneList = new LinkedHashMap();

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

    public LinkedHashMap<Coordinates, geoZone> displayAllGeoZone(){


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

    public LinkedHashMap<Coordinates, geoZone> getZoneList() {
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

    public LinkedHashMap<Coordinates,Double> getPYear(int year){
        LinkedHashMap<Coordinates,Double> array = new LinkedHashMap<Coordinates,Double>();
        List<String> ByKey = new ArrayList<String>(Collections.singleton(array.keySet().toString()));

        for (Coordinates key : zoneList.keySet()) {
            if(zoneList.get(key).getTempList().containsKey(year)){
               array.put(key,zoneList.get(key).getTempList().get(year));
            }
            else{
                System.out.println(" L'année entrée n'est pas la bonne ");
                break;
            }
        }
        return array;
    }

    public LinkedHashMap<Integer,Double> getPZone(Coordinates co){
        LinkedHashMap<Integer,Double> array = new LinkedHashMap<Integer,Double>();
        array.put(,zoneList.get(co).getTempList().values());
        return array;
    }
}
