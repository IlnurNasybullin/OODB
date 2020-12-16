package database.column;

import annotations.Column;
import annotations.ReflectionUtils;
import annotations.Relation;
import annotations.RelationType;
import database.DatabaseStandardSpecification;

import java.lang.reflect.Field;

public class RelationColumn extends AbstractColumn {

    public static class IdColumn extends AbstractColumn implements database.column.IdColumn {

        private final RelationColumn column;

        public IdColumn(RelationColumn column) {
            this.column = column;
        }

        @Override
        public String getName() {
            return column.getName();
        }

        @Override
        public Field getField() {
            return column.getField();
        }
    }

    private final Field field;
    private final String name;

    public RelationColumn(Field field) {
        this.field = field;
        this.name = getName(field);
    }

    private String getName(Field field) {
        annotations.Column column = ReflectionUtils.getAnnotation(field, Column.class);
        Relation relation = ReflectionUtils.getAnnotation(field, Relation.class);
        if (column == null || relation == null || !isRelationColumn(relation.type())) {
            throw new IllegalArgumentException(String.format("%s isn't relation column!", field));
        }

        return DatabaseStandardSpecification.getStandardName(column.name(), field.getName());
    }

    private boolean isRelationColumn(RelationType type) {
        return type == RelationType.ONE_TO_ONE || type == RelationType.MANY_TO_ONE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Field getField() {
        return field;
    }
}
