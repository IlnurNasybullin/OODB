package airlines.utilData.geographic;

public class Latitude extends Coordinate {

    transient public static final int MIN_DEGREE;
    transient public static final int MAX_DEGREE;

    static {
        MIN_DEGREE = -90;
        MAX_DEGREE = 90;
    }

    public static Latitude of(double degrees) {
        checkDegree(degrees);
        return new Latitude(degrees);
    }

    public static Latitude of(int degrees, double minutes) {
        checkDegree(degrees);
        checkMinutes(minutes);
        return new Latitude(degrees, minutes);
    }

    public static Latitude of(int degree, int minutes, double seconds) {
        checkDegree(degree);
        checkMinutes(minutes);
        checkSeconds(seconds);
        return new Latitude(degree, minutes, seconds);
    }

    private static void checkDegree(double degrees) {
        if (degrees < MIN_DEGREE || degrees > MAX_DEGREE) {
            throw new IllegalArgumentException("Градусы заданы некорректно!");
        }
    }

    private Latitude(double degrees) {
        super(degrees);
    }

    private Latitude(int degrees, double minutes) {
        super(degrees, minutes);
    }

    private Latitude(int degrees, int minutes, double seconds) {
        super(degrees, minutes, seconds);
    }

    public LatitudeDirection getDirection() {
        return (degrees < 0) ? LatitudeDirection.SOUTH : LatitudeDirection.NORTH;
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), getDirection());
    }
}
