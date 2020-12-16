package database.column;

import annotations.ID;
import annotations.Relation;

import java.lang.reflect.Field;

public class ColumnFactory {
    public static Column createOf(Field field, Relation relation) {
        return relation == null ? getEntityColumn(field) : getRelationColumn(field);
    }

    private static Column getRelationColumn(Field field) {
        RelationColumn column = new RelationColumn(field);
        if (isIdColumn(column)) {
            return new RelationColumn.IdColumn(column);
        }
        return column;
    }

    private static Column getEntityColumn(Field field) {
        EntityColumn column = new EntityColumn(field);
        if (isIdColumn(column)) {
            return new EntityColumn.IdColumn(column);
        }
        return column;
    }

    private static boolean isIdColumn(Column column) {
        return column.getAnnotation(ID.class) != null;
    }
}
