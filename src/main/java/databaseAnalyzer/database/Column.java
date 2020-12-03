package databaseAnalyzer.database;

import java.lang.reflect.Field;
import java.util.Objects;

import static databaseAnalyzer.database.DatabaseStandardSpecification.getStandardName;

public class Column {

    private final String columnName;

    private Column() {
        this("");
    }

    Column(String columnName) {
        this.columnName = columnName;
    }

    public Column(Field field, annotations.Column annotation) {
        this(getColumnName(field, annotation));
    }

    private static String getColumnName(Field field, annotations.Column annotation) {
        String name = annotation.name();
        return name.equals("") ? getStandardName(field.getName()) : name;
    }

    public String getColumnName() {
        return columnName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column column = (Column) o;
        return Objects.equals(columnName, column.columnName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnName);
    }

    @Override
    public String toString() {
        return columnName;
    }
}
