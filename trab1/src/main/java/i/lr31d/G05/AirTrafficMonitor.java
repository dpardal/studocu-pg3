package i.lr31d.G05;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class AirTrafficMonitor{

    private List<Aircraft> allAircrafts;

    public AirTrafficMonitor( List<Aircraft> aircrafts ){
        allAircrafts = aircrafts;
    }

    public List<Aircraft> getAllAircrafts(){
        return allAircrafts;
    }

    public List<Aircraft> find( Predicate<Aircraft> filter ){
        List<Aircraft> result = new ArrayList<>();
        for( Aircraft a : allAircrafts ){
            if( filter.test(a) ) result.add(a);
        }
        return result;
    }

    public AirTrafficMonitor append( Aircraft a ) throws AirTrafficException {
        for( Aircraft plane : allAircrafts ){
            if( plane.getGeoPos().isNear( a.getGeoPos() ) )     // isNear( Geopos p )
                throw new AirTrafficException("não foi adicionada porque está próxima de uma das aeronaves monitorizadas");
        }
        allAircrafts.add( a );
        return this;
    }

    public List<Aircraft> checkRouteAltitudes(){
        Predicate<Aircraft> isOutOfBounds = a -> {
            int al = a.getGeoPos().getAltitude();
            return al < a.route.getMinAltitude() || al > a.route.getMaxAltitude();
        };

        List<Aircraft> aircraftsOutOfBounds = this.find( isOutOfBounds );

        aircraftsOutOfBounds.sort(
                (o1, o2) -> o2.getGeoPos().getAltitude() - o1.getGeoPos().getAltitude()
        );                      // já fica em ordem decrescente
        return aircraftsOutOfBounds;
    }

    public void removeCivilNoPassenger(){
        Predicate<Aircraft> pred = ( Aircraft a ) -> a instanceof CivilAircraft &&
                ((CivilAircraft) a).getNumberOfPassengers() == 0;
        allAircrafts.removeIf( pred );
    }

    public String toString(){
        StringBuilder str = new StringBuilder("Aeronaves monitorizadas: \n\t");
        for ( Aircraft a : getAllAircrafts() ) {
            str.append( a.toString() + ";\n");
        }
        return str.toString();
    }

}
