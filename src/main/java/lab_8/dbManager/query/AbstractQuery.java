package lab_8.dbManager.query;

import java.util.Objects;

public class AbstractQuery implements Query {

    protected String query;

    protected AbstractQuery(String query) {
        this.query = query;
    }

    @Override
    public String getQuery() {
        return query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractQuery that = (AbstractQuery) o;
        return Objects.equals(query, that.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query);
    }

    @Override
    public String toString() {
        return getQuery();
    }
}
