package lab_8.dbManager.queryManager;

import database.column.Column;
import database.table.RelationTable;
import lab_8.dbManager.query.*;

import java.util.Objects;

public class RelationTableQueryManager implements QueryManager {

    private final Query selectQuery;
    private final Query insertQuery;
    private final Query deleteQuery;

    private final RelationTable table;

    public RelationTableQueryManager(RelationTable table) {
        this.table = table;
        this.selectQuery = createSelectQuery();
        this.insertQuery = createInsertQuery();
        this.deleteQuery = createDeleteQuery();
    }

    private Query createDeleteQuery() {
        DeleteQuery.Builder builder = new DeleteQuery.Builder(table.getName());
        builder.where(table.firstColumn().getName(), Condition.Type.EQ);
        return builder.build();
    }

    private Query createInsertQuery() {
        InsertQuery.Builder builder = new InsertQuery.Builder(table.getName());

        for (Column column: table.getAllColumns()) {
            builder.column(column.getName());
        }
        return builder.build();
    }

    private Query createSelectQuery() {
        return new SelectQuery.Builder(table.getName())
                .select(table.secondColumn().getName())
                .where(table.firstColumn().getName(), Condition.Type.EQ).build();
    }

    @Override
    public Query selectById() {
        return selectQuery;
    }

    @Override
    public Query insert() {
        return insertQuery;
    }

    @Override
    public Query update() {
       throw new IllegalArgumentException("Relation table isn't supported update operation!");
    }

    @Override
    public Query delete() {
        return deleteQuery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationTableQueryManager that = (RelationTableQueryManager) o;
        return Objects.equals(table, that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table);
    }
}
