package i.lr31d.G05;

public class AltitudeRoute implements Route{
    private int min;
    private int max;

    public AltitudeRoute(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public int getMinAltitude() {
        return min;
    }

    @Override
    public int getMaxAltitude() {
        return max;
    }
}
