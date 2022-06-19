package i.lr31d.G05;

public class MilitaryAircraft extends Aircraft {

    boolean armed;

    public MilitaryAircraft( String flightId, GeoPos p, Route r ) throws AirTrafficException {
        super( flightId, p, r );
        armed = false;
    }

    public MilitaryAircraft( String flightId, GeoPos p, Route r, boolean armed ) throws AirTrafficException {
        super( flightId, p, r );
        this.armed = armed;
    }

    @Override
    public boolean isArmed() {
        return armed;
    }

    @Override
    public String toString(){
        String str = "Military FAPT" + super.toString();
        if( armed ) str += " (armed)";
        return str;
    }
}
