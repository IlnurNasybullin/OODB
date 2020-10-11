package lab_5;

import org.postgresql.util.PGobject;

import java.sql.SQLException;
import java.util.Objects;

public abstract class AbstractPGObjectWrapper<T> extends PGobject {

    protected T object;

    protected AbstractPGObjectWrapper(String name) {
        super();
        setType(name);
    }

    protected AbstractPGObjectWrapper(T object, String name) {
        this(name);
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public abstract void setValue(String value) throws SQLException;

    @Override
    public abstract String getValue();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AbstractPGObjectWrapper<?>)) return false;
        AbstractPGObjectWrapper<?> that = (AbstractPGObjectWrapper<?>) obj;
        return Objects.equals(this.object, that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(object);
    }

    @Override
    public String toString() {
        return object.toString();
    }

    protected String getOutputPreparedValue(Object ... args) {
        StringBuffer buffer = new StringBuffer(args.length > 1 ? "(" : "ROW(");
        for (Object object: args) {
            if (object.getClass() == String.class) {
                buffer.append(String.format("'%s',", object));
            } else {
                buffer.append(String.format("%s,", object));
            }
        }

        int length = buffer.length();
        if (buffer.charAt(length - 1) == ',') {
            buffer.deleteCharAt(length - 1);
        }

        buffer.append(')');
        return buffer.toString();
    }

    public RowString getRowString() {
        return new RowString(getValue());
    }

    protected String preparedStatement(String statement) {
        return statement.replace("'", "''");
    }

    protected static class RowString {

        private final String string;

        public RowString(String string) {
            this.string = string;
        }

        public String getString() {
            return string;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RowString rowString = (RowString) o;
            return string.equals(rowString.string);
        }

        @Override
        public int hashCode() {
            return Objects.hash(string);
        }

        @Override
        public String toString() {
            return string;
        }
    }

    protected String tokenPrepare(String value) {
        return value.substring(1, value.length() - 1).replaceAll("\"", "");
    }
}
