package i.lr31d.G05;

public abstract class GeoPos {

    private double latitude;        // graus
    private double longitude;       // graus
    private int altitude;           // metros
    private final int perto = 1000; // assume-se que "perto" = 1000 metros

    public GeoPos( double la, double lo, int al ){
        latitude = la;
        longitude = lo;
        altitude = al;
    }

    public boolean isNear( GeoPos p ){
        return Math.abs( this.getLatitude() - p.getLatitude() ) * 111.139 < perto ||
                Math.abs( this.getLongitude() - p.getLongitude() ) * 111.139 < perto;
        // * 111.139 para converter graus em metros  // longitude = getLongitude() * 40075  * Math.cos( getLatitude() )
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    @Override
    public String toString(){
        return getLatitude() + " - " + getLongitude() + ", " + getAltitude() + "m";
    }

}
