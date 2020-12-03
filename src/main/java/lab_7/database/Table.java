package lab_7.database;

import annotations.*;
import lab_7.analyzer.TableFactory;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static lab_7.database.DatabaseStandardSpecification.getStandardName;

public class Table {

    protected String tableName;
    protected Set<lab_7.database.Column> idColumns;
    private Set<lab_7.database.Column> columns;
    private Set<ExtendedTable> extendedTables;

    protected Table() {}

    public Table(Class<?> tClass) {
        this.tableName = getTableName(tClass);

        this.idColumns = new HashSet<>();
        this.columns = new HashSet<>();
        this.extendedTables = new HashSet<>();

        fillColumns(tClass);
    }

    private void fillColumns(Class<?> tClass) {
        for (Field field : tClass.getDeclaredFields()) {
            ID id = ReflectionUtils.getAnnotation(field, ID.class);
            if (id != null) {
                lab_7.database.Column column = getColumn(field);
                this.idColumns.add(column);
                this.columns.add(column);
                continue;
            }

            Relation relation = ReflectionUtils.getAnnotation(field, Relation.class);
            if (relation != null && hasExtendedTable(relation.type())) {
                Class<?> target = getTargetClass(field, relation);
                Table table = new Table(target);
                TableFactory.put(target, table);
                this.extendedTables.add(new ExtendedTable(this, table));
                continue;
            }

            annotations.Column columnAnnotation = ReflectionUtils.getAnnotation(field, annotations.Column.class);
            if (columnAnnotation != null) {
                this.columns.add(getColumn(field, columnAnnotation));
            }
        }

        Class<?> superClass = tClass.getSuperclass();
        if (superClass != Object.class) {
            fillColumns(superClass);
        }
    }

    private boolean hasExtendedTable(RelationType type) {
        return type == RelationType.ONE_TO_MANY || type == RelationType.MANY_TO_MANY;
    }

    private lab_7.database.Column getColumn(Field field) {
        return getColumn(field, ReflectionUtils.getAnnotation(field, annotations.Column.class));
    }

    private lab_7.database.Column getColumn(Field field, annotations.Column annotation) {
        if (annotation == null) {
            throw new IllegalArgumentException(String.format("%s isn't column", field));
        }
        return new lab_7.database.Column(field, annotation);
    }

    private Class<?> getTargetClass(Field field, Relation relation) {
        Class<?> target = relation.target();
        if (target == Object.class) {
            target = field.getType();
        }
        return target;
    }

    private String getTableName(Class<?> tClass) {
        Entity annotation = ReflectionUtils.getAnnotation(tClass, Entity.class);

        if (annotation == null) {
            throw new IllegalArgumentException(String.format("%s isn't entity", tClass));
        }
        String name = annotation.name();

        return name.equals("") ? getStandardName(tClass.getSimpleName()) : name;
    }

    public String getTableName() {
        return tableName;
    }

    public Set<lab_7.database.Column> getIdColumns() {
        return idColumns;
    }

    public Set<lab_7.database.Column> getColumns() {
        return columns;
    }

    public Set<ExtendedTable> getExtendedTables() {
        return extendedTables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table)) return false;
        Table table = (Table) o;
        return Objects.equals(tableName, table.tableName) && Objects.equals(idColumns, table.idColumns) && Objects.equals(columns, table.columns) && Objects.equals(extendedTables, table.extendedTables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, idColumns, columns, extendedTables);
    }

    @Override
    public String toString() {
        return tableName;
    }

    private final static class ExtendedTable extends Table {

        private ExtendedTable(Table one, Table two) {
            this.tableName = getTableName(one, two);
            this.idColumns = getIdColumns(one, two);
        }

        private Set<lab_7.database.Column> getIdColumns(Table one, Table two) {
            Set<lab_7.database.Column> columns = new HashSet<>(getIdColumns(one));
            columns.addAll(getIdColumns(two));

            return columns;
        }

        private Set<lab_7.database.Column> getIdColumns(Table table) {
            Set<lab_7.database.Column> columns = new HashSet<>();
            for (lab_7.database.Column column: table.getIdColumns()) {
                columns.add(new lab_7.database.Column(String.format("%s_%s", table, column)));
            }
            return columns;
        }

        private String getTableName(Table one, Table two) {
            return String.format("%s_to_%s", one.tableName, two.tableName);
        }

        @Override
        public String getTableName() {
            return this.tableName;
        }

        @Override
        public Set<lab_7.database.Column> getIdColumns() {
            return this.idColumns;
        }

        @Override
        public Set<lab_7.database.Column> getColumns() {
            return this.idColumns;
        }

        @Override
        public Set<ExtendedTable> getExtendedTables() {
            return Collections.EMPTY_SET;
        }
    }
}
