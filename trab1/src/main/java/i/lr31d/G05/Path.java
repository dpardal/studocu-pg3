package i.lr31d.G05;

public abstract class Path {

    private final double distance; // Distância entre as localidades terminais
    protected Path( double distance ) { this.distance = distance; }
    public abstract Place getFirstPlace(); // Localidade inicial da ligação
    public abstract Place getLastPlace(); // Localidade final da ligação
    public abstract Place[] getPlaces(); // Array (sem repetições) com as localidades da ligação
    public String toString( )
    { return getFirstPlace().getName()+" -> "+ getLastPlace().getName()+": "+distance+"Km"; }
    public static double sumDistance( int n, Path ... paths) throws PathException {
        double sum = 0;
        try{
            Path previous = paths[0];
            for( int i = 1; i < n; i++) {
                if (!previous.getLastPlace().equals( paths[i].getFirstPlace() ) ) throw new PathException();
                sum += paths[i].distance;
            }
        }
        catch (PathException e){
            if( n > paths.length ) throw new PathException("Número de ligações inválido");
        }
        return sum;
    }
}
