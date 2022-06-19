package i.lr31d.G05;



public class CivilAircraft extends Aircraft {

    private int passengersNum;

    public CivilAircraft( String operatorId, int flightNumber, GeoPos p, Route r, int passengersNum ) throws AirTrafficException {
        super( operatorId + flightNumber, p, r );
        this.passengersNum = passengersNum;
    }

    @Override
    public boolean isArmed() {
        return false;
    }

    public int getNumberOfPassengers(){
        return passengersNum;
    }
}
