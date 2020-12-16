package lab_8.dbManager.query;

import java.util.Objects;

public class Assignation {

    private final static String DEFAULT_EXPRESSION = "?";

    private final String column;
    private final String castType;
    private final String expression;

    public Assignation(String column) {
        this(column, DEFAULT_EXPRESSION);
    }

    public Assignation(String column, String castType) {
        this(column, castType, DEFAULT_EXPRESSION);
    }

    public Assignation(String column, String castType, String expression) {
        this.column = column;
        this.castType = castType;
        this.expression = expression;
    }

    public String getColumn() {
        return column;
    }

    public String getCastType() {
        return castType;
    }

    public String getExpression() {
        return expression;
    }

    public String toCast() {
        String cast = getCastType();
        cast = cast == null ? "" : String.format("::%s", cast);
        return cast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignation that = (Assignation) o;
        return Objects.equals(column, that.column) && Objects.equals(castType, that.castType) && Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, castType, expression);
    }

    @Override
    public String toString() {
        return String.format("\"%s\" = %s%s", getColumn(), getExpression(), toCast());
    }
}
