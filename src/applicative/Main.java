package applicative;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> coords = new ArrayList<Integer>();
        ArrayList<ArrayList<Float>> anom = new ArrayList<ArrayList<Float>>();


                //We start by reading the CSV file
                fileReader.getDataFromCSVFile(coords, anom);

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////// TEST //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            Earth Terre =new Earth(coords,anom);
            //System.out.println(Terre.displayAllGeoZone());
            System.out.println(Terre.valeurMax());
            System.out.println(Terre.valeurMin());

        }
    }

