package database.table;

import database.column.Column;
import database.column.IdColumn;

import java.lang.reflect.Field;
import java.util.List;

public interface RelationTable extends Table {

    IdColumn firstColumn();
    IdColumn secondColumn();

    EntityTable firstTable();
    EntityTable secondTable();

    Field getRelationField();

    @Override
    default List<Column> getAllColumns() {
        return List.of(firstColumn(), secondColumn());
    }
}
