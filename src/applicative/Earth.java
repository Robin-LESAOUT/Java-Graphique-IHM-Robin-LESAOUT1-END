package applicative;

import java.util.*;

public class Earth {
    LinkedHashMap<Coordinates,geoZone> zoneList = new LinkedHashMap();

    /*
     *   Constructeur de classe qui permet de créer les différents objets du modèle.
     *   @param coord , anomalies
     *
     */
    //Constructor
    public Earth(ArrayList<Integer> coord,ArrayList<ArrayList<Float>> anomalies) {
        int j=0;
        for (int i=0; i<anomalies.size();i++) {
            Coordinates cods = new Coordinates(coord.get(j+1), coord.get(j));
            j+=2;
            geoZone geoZ = new geoZone(anomalies.get(i));
            zoneList.put(cods,geoZ);
        }
    }

    //Getters & setters

    public LinkedHashMap<Coordinates, geoZone> getZoneList() {
        return zoneList;
    }

    //Methodes

    /*
    *   Permet de récupérer une liste des zones géographiques d'une planete avec leurs coordonnees
    * @return l'ensemble des zones géographiques d'une planète. (dans une Hashmap)
    *
     */
    public LinkedHashMap<Coordinates, geoZone> displayAllGeoZone(){
        return zoneList;
    }


    /*
     *   Récupère la valeur maximale d'anomalie d'une planète
     *   Parcours de la Hashmap zoneList, puis de la Hashmap TempList des zones geographiques
     * @return la valeur maximale d'anomalie de temperature des zones géographiques d'une planète.
     *
     */
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

    /*
     *   Récupère la valeur minimale d'anomalie d'une planète
     *   Parcours de la Hashmap zoneList, puis de la Hashmap TempList des zones geographiques
     * @return la valeur minimale d'anomalie de temperature des zones géographiques d'une planète.
     *
     */
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
     * Permet d'afficher toutes les anomalies de toutes les zones du globe selon une année rentrée en paramètres
     * Test si l'année entrée en paramètre appartient bien à l'intervalle disponible.
     * @param year
     *          L'année pour laquelle la méthode affiche les anomalies
     * @return Les anomalies de toutes les zones géographiques pour l'année entrée en paramètres dans une Hashmap
     *
     */
    public LinkedHashMap<Coordinates,Double> getPYear(int year){
        LinkedHashMap<Coordinates,Double> array = new LinkedHashMap<Coordinates,Double>();
        List<String> ByKey = new ArrayList<String>(Collections.singleton(array.keySet().toString()));

        for (Coordinates key : zoneList.keySet()) {
            if(zoneList.get(key).getTempList().containsKey(year)){
               array.put(key,zoneList.get(key).getTempList().get(year));
            }
            else{
                System.out.println(" L'année entrée n'est pas la bonne, entrez une année comprise entre 1880 et 2020. ");
                break;
            }
        }
        return array;
    }

    /*
     * Permet d'afficher toutes les anomalies d'une seule coordonnée pour toutes les années disponibles
     * Test si les coordonnées en entrée existent
     * @param co
     *          Les coordonnées pour laquelle la méthode affiche les anomalies
     * @return Les anomalies pour une coordonnée avec toutes les années dans une Hashmap
     *
     */
    public LinkedHashMap<Integer,Double> getPZone(Coordinates co){
        LinkedHashMap<Integer,Double> array = new LinkedHashMap<Integer,Double>();
        for (Coordinates key : zoneList.keySet()) {
            if (key.equals(co)) {
                array=zoneList.get(key).getTempList();
            }
            else{
                System.out.println("Les coordonnées ne correspondent pas. ");
                break;
            }
        }
        return array;
    }
}

// COMMENTAIRES

//test de la lecture des fichiers
       /* for (Map.Entry mapentry : zoneList.entrySet()) {
            System.out.println("clé: "+mapentry.getKey()
                    + " | valeur: " + mapentry.getValue());

        }
        for (Coordinates key : zoneList.keySet()){
            System.out.println(zoneList.get(key).getTempList());
        }*/
