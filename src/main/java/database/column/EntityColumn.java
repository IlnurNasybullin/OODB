package database.column;

import annotations.ReflectionUtils;

import java.lang.reflect.Field;

import static database.DatabaseStandardSpecification.getStandardName;

public class EntityColumn extends AbstractColumn {

    public static class IdColumn extends AbstractColumn implements database.column.IdColumn {

        private final EntityColumn column;

        public IdColumn(EntityColumn column) {
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

    public EntityColumn(Field field) {
        this.field = field;
        this.name = getName(field);
    }

    private String getName(Field field) {
        annotations.Column column = ReflectionUtils.getAnnotation(field, annotations.Column.class);
        if (column == null) {
            throw new IllegalArgumentException(String.format("%s isn't entity column!", field));
        }

        return getStandardName(column.name(), field.getName());
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
