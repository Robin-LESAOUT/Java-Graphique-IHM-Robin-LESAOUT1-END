package applicative;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> coords = new ArrayList<Integer>();
        ArrayList<ArrayList<Float>> anom = new ArrayList<ArrayList<Float>>();


                //We start by reading the CSV file
                fileReader.getDataFromCSVFile(coords, anom);

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////// TEST //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
          /*
            Earth Terre =new Earth(coords,anom);
            Coordinates co = new Coordinates(-178,-88);
            Coordinates cor = new Coordinates(154,12);
            System.out.println(Terre.displayAllGeoZone());
            System.out.println(Terre.valeurMax());
            System.out.println(Terre.valeurMin());
            System.out.println(Terre.getPYear(1900));
            System.out.println(Terre.getPZone(co));
            System.out.println(Terre.getPZoneYear(cor,1881));
            */
        }
    }

