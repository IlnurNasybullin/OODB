package airlines.utilData.geographic;

public class Longitude extends Coordinate {

    transient public static final int MIN_DEGREE;
    transient public static final int MAX_DEGREE;

    static {
        MIN_DEGREE = -180;
        MAX_DEGREE = 180;
    }

    public static Longitude of(double degrees) {
        checkDegree(degrees);
        return new Longitude(degrees);
    }

    public static Longitude of(int degrees, double minutes) {
        checkDegree(degrees);
        checkMinutes(minutes);
        return new Longitude(degrees, minutes);
    }

    public static Longitude of(int degrees, int minutes, double seconds) {
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

    private Longitude(double degrees) {
        super(degrees);
    }

    public Longitude(int degrees, double minutes) {
        super(degrees, minutes);
    }

    public Longitude(int degrees, int minutes, double seconds) {
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
