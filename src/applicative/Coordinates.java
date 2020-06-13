package applicative;

import java.util.ArrayList;
import java.util.Objects;

public class Coordinates {
    private int lon;
    private int lat;

    //Constructeur

    /*
     *   Constructeur de classe qui permet d'initialiser la longitude et la latitude.
     *   @param lon , lat
     *
     */
    public Coordinates(int lon, int lat) {
        this.lon = lon;
        this.lat = lat;
    }

    //Méthodes usuelles

    @Override
    public String toString() {
        return ""+ lon + lat ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return lon == that.lon &&
                lat == that.lat;
    }

    //Getters

    public int getLon() {
        return lon;
    }

    public int getLat() {
        return lat;
    }

    //Methodes
}
