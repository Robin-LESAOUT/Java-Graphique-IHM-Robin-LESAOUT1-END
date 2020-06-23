package applicative;



import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import java.util.*;

public class Earth {
    LinkedHashMap<Coordinates,geoZone> zoneList = new LinkedHashMap();
    LinkedHashMap<Coordinates, MeshView> meshList = new LinkedHashMap();
    LinkedHashMap<Coordinates, Cylinder> cylinderList = new LinkedHashMap();
    ArrayList<Integer> coord = new ArrayList<Integer>();
    ArrayList<Integer> lat = new ArrayList<Integer>();
    ArrayList<Integer> lon = new ArrayList<Integer>();
    ArrayList<ArrayList<Float>> anomalies = new ArrayList<ArrayList<Float>>();
    boolean isQuadri = true;
    int Anneechoisie=1880;
    int latView;
    int lonView;
    double valVitesse=1;


    /*
     *   Constructeur de classe qui permet de créer les différents objets du modèle.
     *   @param coord , anomalies
     *
     */
    //Constructor
    public Earth() {
        fileReader.getDataFromCSVFile(coord, anomalies);
        int j=0;
        for (int i=0; i<anomalies.size();i++) {
            Coordinates cods = new Coordinates(coord.get(j+1), coord.get(j));
            lon.add(coord.get(j+1));
            lat.add(coord.get(j));
            j+=2;
            geoZone geoZ = new geoZone(anomalies.get(i));
            zoneList.put(cods,geoZ);
        }
    }

    //Getters & setters

    public LinkedHashMap<Coordinates, geoZone> getZoneList() {
        return zoneList;
    }

    public ArrayList<Integer> getLat() {
        return lat;
    }

    public ArrayList<Integer> getLon() {
        return lon;
    }

    public int getAnneechoisie() {
        return Anneechoisie;
    }

    public LinkedHashMap<Coordinates, MeshView> getMeshList() {
        return meshList;
    }

    public void setAnneechoisie(int anneechoisie) {
        Anneechoisie = anneechoisie;
    }

    public LinkedHashMap<Coordinates, Cylinder> getCylinderList() {
        return cylinderList;
    }

    public boolean isQuadri() {
        return isQuadri;
    }

    public void setQuadri(boolean quadri) {
        isQuadri = quadri;
    }

    public void setValVitesse(double valVitesse) {
        this.valVitesse = valVitesse;
    }

    public double getValVitesse() {
        return valVitesse;
    }

    public void setLatView(int latView) {
        this.latView = latView;
    }

    public void setLonView(int lonView) {
        this.lonView = lonView;
    }

    public int getLatView() {
        return latView;
    }

    public int getLonView() {
        return lonView;
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
    public Double valeurMax() {
       Double max=0.0;
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
    public Double valeurMin(){
        Double min=0.0;
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
                array=zoneList.get(co).getTempList();
        return array;
    }

    /*
     * Permet d'afficher toutes les anomalies pour une coordonée et une année donnée
     * @param co , year
     *          Les coordonnées pour laquelle la méthode affiche les anomalies ainsi que l'année
     * @return L'anomalie pour une coordonnée et une année
     *
     */
    public Double getPZoneYear(Coordinates co , int year){
        Double res=0.0;
            res = zoneList.get(co).getTempList().get(year).doubleValue();
        return res;
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
