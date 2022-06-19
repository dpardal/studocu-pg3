package i.lr31d.G05;


public class CompoundPath extends Path{

    private Path[] paths;

    public CompoundPath(int n, Path ... paths) throws PathException {
        super( Path.sumDistance(n, paths) );
        if( n < 2 || n > paths.length ) throw new PathException();
        Path previous = paths[0];
        for( int i = 1; i < n; i++){
            if( ! previous.getLastPlace().equals( paths[i].getFirstPlace() ) ) throw new PathException();   // se não são contiguos
        }
    }

    @Override
    public Place getFirstPlace(){
        return paths[0].getFirstPlace();
    }

    @Override
    public Place getLastPlace() {
        return paths[paths.length-1].getLastPlace();
    }

    @Override
    public Place[] getPlaces(){
        Place[][] allPlaces = {};  // { {AB} {BC} {CD} {DE} {EF} }
        Place[] result = {};
        result[0] = paths[0].getPlaces()[0];        // guarda o primeiro firstPlace
        for( int i = 0; i < paths.length-1; ++i){   // guarda todos os lastPlaces
            allPlaces[i] = paths[i].getPlaces();
            result[i+1] = allPlaces[i][1];
        }
        return result;
    }

    @Override
    public String toString() {
        Place[] allPlaces = getPlaces();
        String str = super.toString() + " ( " + allPlaces[0].toString();
        for( int i = 1; i < getPlaces().length-1; ++i  ){
            str +=  " -> " + allPlaces[i].toString() ;
        }
        str += " )";
        return str;
    }

}
