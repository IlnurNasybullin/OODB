package lab_8.dbManager.queryManager;

import database.column.Column;
import database.column.IdColumn;
import database.column.RelationColumn;
import database.table.EntityTable;
import database.table.TableFactory;
import lab_8.dbManager.query.*;

import java.util.Objects;

public class EntityTableQueryManager implements QueryManager {

    private final EntityTable table;
    private final Query selectById;
    private final Query insertQuery;
    private final Query updateQuery;
    private final Query deleteQuery;

    public EntityTableQueryManager(EntityTable table) {
        this.table = table;
        this.selectById = createSelectByID();
        this.insertQuery = createInsertQuery();
        this.updateQuery = createUpdateQuery();
        this.deleteQuery = createDeleteQuery();
    }

    private Query createDeleteQuery() {
        DeleteQuery.Builder builder = new DeleteQuery.Builder(table.getName());
        IdColumn column = table.getIdColumn();

        if (column == null) {
            throw new IllegalArgumentException("Delete query isn't created, because table hasn't id column!");
        }
        builder.where(column.getName(), column.getUserType(), Condition.Type.EQ);
        return builder.build();
    }

    private Query createUpdateQuery() {
        UpdateQuery.Builder builder = new UpdateQuery.Builder(table.getName());

        for (Column column: table.getAllColumns()) {
            String name = column.getName();
            String userType = column.getUserType();
            if (column instanceof IdColumn) {
                builder.where(column.getName(), Condition.Type.EQ);
            } else {
                builder.set(name, userType);
            }
        }

        return builder.build();
    }

    private Query createSelectByID() {
        SelectQuery.Builder builder = new SelectQuery.Builder(table.getName());
        select(builder);
        where(builder);
        return builder.build();
    }

    private void select(SelectQuery.Builder builder) {
        for (Column column: table.getAllColumns()) {
            builder.select(column.getName());
        }
    }

    private void where(SelectQuery.Builder builder) {
        IdColumn column = table.getIdColumn();
        if (column == null) {
            return;
        }
        builder.where(column.getName(), column.getUserType(), Condition.Type.EQ);
    }

    private Query createInsertQuery() {
        InsertQuery.Builder builder = new InsertQuery.Builder(table.getName());
        for (Column column: table.getAllColumns()) {
            if (column instanceof RelationColumn) {
                insertRelationColumn(builder, (RelationColumn) column);
            } else {
                insertColumn(builder, column);
            }
        }

        return builder.build();
    }

    private void insertRelationColumn(InsertQuery.Builder builder, RelationColumn column) {
        EntityTable table = TableFactory.createOf(column.getField().getType());
        IdColumn idColumn = table.getIdColumn();
        insertWithCast(builder, column.getName(), idColumn.getUserType());
    }

    private void insertColumn(InsertQuery.Builder builder, Column column) {
        insertWithCast(builder, column.getName(), column.getUserType());
    }

    private void insertWithCast(InsertQuery.Builder builder, String columnName, String userType) {
        builder.column(columnName, userType);
    }

    @Override
    public Query selectById() {
        return selectById;
    }

    @Override
    public Query insert() {
        return insertQuery;
    }

    @Override
    public Query update() {
        return updateQuery;
    }

    @Override
    public Query delete() {
        return deleteQuery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityTableQueryManager that = (EntityTableQueryManager) o;
        return Objects.equals(table, that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table);
    }
}
