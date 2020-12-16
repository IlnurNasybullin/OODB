package airlines.userTypes.geographic;

import annotations.TypeComponent;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

public abstract class Coordinate {
    protected final short degrees;
    protected final byte minutes;
    protected final double seconds;

    public static final int DEGREE_TO_MINUTES = 60;
    public static final int MINUTES_TO_SECONDS = 60;

    protected Coordinate() {
        this(0d);
    }

    protected Coordinate(double degrees) {
        this((short) degrees, getMinutes(degrees));
    }

    protected Coordinate(short degrees, double minutes) {
        this(degrees, (byte) minutes, getSeconds(minutes));
    }

    private static double getMinutes(double degrees) {
        return getValue(degrees, DEGREE_TO_MINUTES);
    }

    private static double getSeconds(double minutes) {
        return getValue(minutes, MINUTES_TO_SECONDS);
    }

    private static double getValue(double value, int multiply) {
        return new BigDecimal(Math.abs(value), new MathContext(getLength(value))).remainder(BigDecimal.ONE).multiply(new BigDecimal(multiply)).doubleValue();
    }

    private static int getLength(double minutes) {
        return Double.toString(Math.abs(minutes)).length() - 1;
    }

    protected Coordinate(short degrees, byte minutes, double seconds) {
        this.degrees = degrees;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    protected static void checkSeconds(double seconds) {
        if (seconds < 0 || seconds > Coordinate.MINUTES_TO_SECONDS) {
            throw new IllegalArgumentException("Секунды заданы некорректно!");
        }
    }

    protected static void checkMinutes(double minutes) {
        if (minutes < 0 || minutes > Coordinate.DEGREE_TO_MINUTES) {
            throw new IllegalArgumentException("Минуты заданы некорректно!");
        }
    }

    public short getDegrees() {
        return degrees;
    }

    public byte getMinutes() {
        return minutes;
    }

    public double getSeconds() {
        return seconds;
    }

    @TypeComponent
    public double getFullDegrees() {
        double min = (double) (minutes) + seconds / Coordinate.MINUTES_TO_SECONDS;
        return (double) (degrees) + min / Coordinate.DEGREE_TO_MINUTES;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return degrees == that.degrees &&
                minutes == that.minutes &&
                seconds == that.seconds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(degrees, minutes, seconds, getClass());
    }

    @Override
    public String toString() {
        return String.format("%d%c%d%c%f%c", Math.abs(degrees), '\u00B0', minutes, '\u2032', seconds, '\u2033');
    }
}
