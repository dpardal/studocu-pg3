package i.lr31d.G05;

public class SimplePath extends Path{
    private final Place first;
    private final Place last;


    public SimplePath( Place f, Place l, double d ){
        super(d);
        first = f;
        last = l;
    }


    @Override
    public Place getFirstPlace() {
        return this.first;
    }

    @Override
    public Place getLastPlace() {
        return this.last;
    }

    @Override
    public Place[] getPlaces() {
        Place[] places = { getFirstPlace(), getLastPlace() };
        return places;
    }
}
