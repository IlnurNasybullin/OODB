package airlines.userTypes;

import annotations.TypeComponent;
import annotations.UserType;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@UserType
public class IATA {

    @TypeComponent
    private final String ID;

    private final static Predicate<String> lengthCheck =
            (element -> !Objects.isNull(element) && element.length() == 3);
    private final static Predicate<String> pattern = Pattern.compile("[A-Z0-9]{3}").asPredicate();

    private IATA() {
        this("000");
    }

    private IATA(String id) {
        ID = id;
    }

    public static IATA of(String ID) {
        check(ID);
        return new IATA(ID);
    }

    private static void check(String ID) {
        if (lengthCheck.and(pattern).negate().test(ID)) {
            throw new IllegalArgumentException(String.format("%s не соответствует коду IATA!", ID));
        }
    }

    public String getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IATA that = (IATA) o;
        return ID.equals(that.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ID);
    }

    @Override
    public String toString() {
        return getID();
    }
}
