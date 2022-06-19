package i.lr31d.G05;

public abstract class Aircraft {

    private final String flightId;
    private GeoPos geoPos;
    public final Route route;

    public Aircraft(String flightId, GeoPos p, Route r) throws AirTrafficException{
        this.flightId = flightId;
        this.geoPos = p;
        this.route = r;
        int altitude_do_p = p.getAltitude();
        if ( altitude_do_p < r.getMinAltitude() || altitude_do_p > r.getMaxAltitude() )
            throw new AirTrafficException();
    }

    public GeoPos getGeoPos(){
        return geoPos;
    }

    public GeoPos setGeoPos(GeoPos p){
        return geoPos = p;
    }

    public abstract boolean isArmed();

    public final boolean isFlightId(String id){ return id.compareTo(flightId) == 0; }

    public String toString() { return flightId + " at " + geoPos; }

}
