package lab_5.dbHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QueryBuilder {
    private QueryType type;
    private Set<String> tables;
    private Set<String> columns;
    private String query;

    public QueryBuilder() {
        this.tables = new HashSet<>();
        this.columns = new HashSet<>();
        this.query = "";
    }

    public QueryBuilder setType(QueryType type) {
        this.type = type;
        return this;
    }

    public QueryBuilder addTable(String table) {
        this.tables.add(table);
        return this;
    }

    public QueryBuilder addColumn(String column) {
        this.columns.add(column);
        return this;
    }

    public QueryBuilder setQuery(String query) {
        this.query = query;
        return this;
    }

    public Query createQuery() {
        return new Query(type, tables, columns, query);
    }
}