package lab_8.dbManager.query;

import java.util.Objects;

public class Condition {
    public enum Type {
        EQ("="), NE("<>"), LE("<"), LQ("<="), ME(">"), MQ(">=");

        private final String condition;

        Type(String condition) {
            this.condition = condition;
        }

        public String getCondition() {
            return condition;
        }
    }

    public enum Union {
        AND, OR
    }

    private final String column;
    private final String cast;
    private final Type type;

    public Condition(String column, String cast, Type type) {
        this.column = column;
        this.cast = cast;
        this.type = type;
    }

    public String getColumn() {
        return column;
    }

    public String getCast() {
        return cast;
    }

    public Type getType() {
        return type;
    }

    public String toCast() {
        String cast = getCast();
        cast = cast == null ? "?" : String.format("?::%s", cast);
        return cast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Condition condition = (Condition) o;
        return Objects.equals(column, condition.column) && type == condition.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, type);
    }

    @Override
    public String toString() {
        return String.format("\"%s\" %s %s", getColumn(), getType().getCondition(), toCast());
    }
}
