package airlines.userTypes.geographic;

public class Longitude extends Coordinate {

    transient public static final double MIN_DEGREE = -180d;
    transient public static final double MAX_DEGREE = 180d;

    public static final Longitude DEFAULT = new Longitude();

    public static Longitude of(double degrees) {
        checkDegree(degrees);
        return new Longitude(degrees);
    }

    public static Longitude of(short degrees, double minutes) {
        checkDegree(degrees);
        checkMinutes(minutes);
        return new Longitude(degrees, minutes);
    }

    public static Longitude of(short degrees, byte minutes, double seconds) {
        checkDegree(degrees);
        checkMinutes(minutes);
        checkSeconds(seconds);
        return new Longitude(degrees, minutes, seconds);
    }

    private static void checkDegree(double degrees) {
        if (degrees < MIN_DEGREE || degrees > MAX_DEGREE) {
            throw new IllegalArgumentException("Градусы заданы некорректно!");
        }
    }

    private Longitude() {
        super();
    }

    private Longitude(double degrees) {
        super(degrees);
    }

    public Longitude(short degrees, double minutes) {
        super(degrees, minutes);
    }

    public Longitude(short degrees, byte minutes, double seconds) {
        super(degrees, minutes, seconds);
    }

    public LongitudeDirection getDirection() {
        return (degrees < 0) ? LongitudeDirection.WEST : LongitudeDirection.EAST;
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), getDirection());
    }
}
