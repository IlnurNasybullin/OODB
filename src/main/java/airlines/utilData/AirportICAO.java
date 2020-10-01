package airlines.utilData;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class AirportICAO {
    private final String ID;
    private final static Predicate<String> lengthCheck =
            (element -> !Objects.isNull(element) && element.length() == 4);
    private final static Predicate<String> pattern = Pattern.compile("[A-Z0-9]{4}").asPredicate();

    public static AirportICAO of(String ID) {
        check(ID);
        return new AirportICAO(ID);
    }

    private static void check(String ID) {
        if (!lengthCheck.and(pattern).test(ID)) {
            throw new IllegalArgumentException(String.format("Строка %s не представима в виде кода ICAO!", ID));
        }
    }

    private AirportICAO(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirportICAO icao = (AirportICAO) o;
        return ID.equals(icao.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return getID();
    }
}
