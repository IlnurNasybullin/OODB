package airlines.utilData;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class TechnicalPassportID {
    private final String ID;
    private final static Predicate<String> length;
    private final static Predicate<String> pattern;

    public static final int COUNT = 8;

    static {
        length = string -> string.length() == COUNT;
        pattern = length.and(Pattern.compile("[A-Z0-9]{8}").asPredicate());
    }

    private TechnicalPassportID(String ID) {
        this.ID = ID;
    }

    public static TechnicalPassportID of(String ID) {
        checkID(ID);
        return new TechnicalPassportID(ID);
    }

    private static void checkID(String id) {
        if (Objects.isNull(id) || pattern.negate().test(id)) {
            throw new IllegalArgumentException("%s не является ID технического паспорта!");
        }
    }

    public String getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechnicalPassportID that = (TechnicalPassportID) o;
        return ID.equals(that.ID);
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
