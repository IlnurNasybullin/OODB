package database.table;

import database.column.Column;
import database.column.EntityColumn;
import database.column.IdColumn;
import database.column.RelationColumn;

import java.util.ArrayList;
import java.util.List;

public interface EntityTable extends Table {

    Class<?> entityClass();

    IdColumn getIdColumn();
    List<EntityColumn> getEntityColumns();
    List<RelationColumn> getRelationColumns();
    List<RelationTable> getRelationTables();

    @Override
    default List<Column> getAllColumns() {
        List<Column> columns = new ArrayList<>();
        columns.add(getIdColumn());
        columns.addAll(getEntityColumns());
        columns.addAll(getRelationColumns());
        return List.copyOf(columns);
    }
}
