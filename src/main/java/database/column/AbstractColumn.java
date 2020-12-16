package database.column;

import java.util.Objects;

public abstract class AbstractColumn implements Column {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Column that = (Column) obj;
        return getField() == that.getField();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getField());
    }

    @Override
    public String toString() {
        return getName();
    }
}
