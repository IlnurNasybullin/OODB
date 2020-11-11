package airlines.userTypes;

import annotations.CompositeType;
import annotations.NotNull;
import annotations.POSIX;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
@CompositeType
public class TechnicalPassportID {
    @NotNull
    @POSIX(regex = REGEX, length = COUNT)
    private final String ID;

    private final static Predicate<String> length;
    private final static Predicate<String> pattern;

    public static final int COUNT = 8;
    public static final TechnicalPassportID DEFAULT = new TechnicalPassportID();

    public static final String REGEX = "[A-Z0-9]{8}";

    static {
        length = string -> string.length() == COUNT;
        pattern = length.and(Pattern.compile(REGEX).asPredicate());
    }

    private TechnicalPassportID() {
        this("00000000");
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
            throw new IllegalArgumentException("%s не является AutoIncrementable технического паспорта!");
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
        return Objects.equals(ID, that.ID);
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
