package applicative;

import java.util.ArrayList;

public class Coordinates {
    private int lon;
    private int lat;

    public Coordinates(int lon, int lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public int getLon() {
        return lon;
    }

    public int getLat() {
        return lat;
    }
}
