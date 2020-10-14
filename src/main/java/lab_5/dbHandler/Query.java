package lab_5.dbHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class Query {

    private QueryType type;
    private Set<String> tables;
    private Set<String> columns;
    private String query;

    Query(QueryType type, Set<String> tables, Set<String> columns, String query) {
        this.type = type;
        this.tables = tables;
        this.columns = columns;
        this.query = query;
    }

    public boolean isExcludingQuery(Query query) {
        if (query.type == QueryType.READ && this.type == QueryType.READ) return false;
        return !(Collections.disjoint(this.tables, query.tables) && Collections.disjoint(this.columns, query.columns));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query query1 = (Query) o;
        return query.equals(query1.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query);
    }

    @Override
    public String toString() {
        return query;
    }
}
