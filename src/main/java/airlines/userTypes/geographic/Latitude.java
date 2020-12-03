package airlines.userTypes.geographic;

public class Latitude extends Coordinate {

    transient public static final double MIN_DEGREE = -90d;
    transient public static final double MAX_DEGREE = 90d;

    public static final Latitude DEFAULT = new Latitude();

    public static Latitude of(double degrees) {
        checkDegree(degrees);
        return new Latitude(degrees);
    }

    public static Latitude of(short degrees, double minutes) {
        checkDegree(degrees);
        checkMinutes(minutes);
        return new Latitude(degrees, minutes);
    }

    public static Latitude of(short degree, byte minutes, double seconds) {
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

    private Latitude() {
        super();
    }

    private Latitude(double degrees) {
        super(degrees);
    }

    private Latitude(short degrees, double minutes) {
        super(degrees, minutes);
    }

    private Latitude(short degrees, byte minutes, double seconds) {
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
