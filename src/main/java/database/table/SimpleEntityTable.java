package database.table;

import annotations.Entity;
import annotations.ReflectionUtils;
import annotations.Relation;
import annotations.RelationType;
import database.column.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static database.DatabaseStandardSpecification.getStandardName;

public class SimpleEntityTable implements EntityTable {

    private class RelationTable implements database.table.RelationTable {

        private class RelationIDColumn implements IdColumn {

            private final IdColumn column;

            private final String name;
            private RelationIDColumn(IdColumn column, String tableName) {
                this.column = column;
                this.name = String.format("%s_%s", tableName, column.getName());
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Field getField() {
                return column.getField();
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                RelationIDColumn that = (RelationIDColumn) o;
                return Objects.equals(column, that.column) && Objects.equals(name, that.name);
            }

            @Override
            public int hashCode() {
                return Objects.hash(column, name);
            }

            @Override
            public String toString() {
                return getName();
            }

        }
        private final String name;

        private final EntityTable firstTable;

        private final EntityTable secondTable;
        private final IdColumn firstColumn;
        private final IdColumn secondColumn;

        private final Field relationField;

        private RelationTable(Class<?> targetClass, Field relationField) {
            this.relationField = relationField;
            this.firstTable = SimpleEntityTable.this;
            this.secondTable = TableFactory.createOf(targetClass);
            this.name = createName();
            this.firstColumn = new RelationIDColumn(firstTable.getIdColumn(), firstTable.getName());
            this.secondColumn = new RelationIDColumn(secondTable.getIdColumn(), secondTable.getName());
        }

        private String createName() {
            return String.format("%s_to_%s", firstTable.getName(), secondTable.getName());
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public IdColumn firstColumn() {
            return firstColumn;
        }

        @Override
        public IdColumn secondColumn() {
            return secondColumn;
        }

        @Override
        public EntityTable firstTable() {
            return firstTable;
        }

        @Override
        public EntityTable secondTable() {
            return secondTable;
        }

        @Override
        public Field getRelationField() {
            return relationField;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RelationTable that = (RelationTable) o;
            return Objects.equals(getName(), that.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }

        @Override
        public String toString() {
            return getName();
        }
    }

    private final Class<?> entityClass;
    private final String name;

    private IdColumn idColumn;
    private List<EntityColumn> entityColumns;
    private List<RelationColumn> relationColumns;

    private List<database.table.RelationTable> tables;

    public SimpleEntityTable(Class<?> tClass) {
        this.entityClass = tClass;
        this.name = getName(tClass);

        this.entityColumns = new ArrayList<>();
        this.relationColumns = new ArrayList<>();

        this.tables = new ArrayList<>();
        fillTable();
    }

    private void fillTable() {
        fillTable(this.entityClass);

        this.entityColumns = List.copyOf(this.entityColumns);
        this.relationColumns = List.copyOf(this.relationColumns);

        this.tables = List.copyOf(this.tables);
    }

    private void fillTable(Class<?> entityClass) {
        for (Field field: entityClass.getDeclaredFields()) {
            Relation relation = ReflectionUtils.getAnnotation(field, Relation.class);
            if (relation != null && isRelationTable(relation.type())) {
                this.tables.add(new RelationTable(getTargetClass(field, relation.target()), field));
                continue;
            }

            Column col = ColumnFactory.createOf(field, relation);
            if (col instanceof IdColumn) {
                checkId();
                this.idColumn = (IdColumn) col;
                continue;
            }

            if (col instanceof RelationColumn) {
                this.relationColumns.add((RelationColumn) col);
                continue;
            }

            if (col instanceof EntityColumn) {
                this.entityColumns.add((EntityColumn) col);
            }
        }

        Class<?> superClass = entityClass.getSuperclass();
        if (superClass != Object.class) {
            fillTable(superClass);
        }
    }

    private void checkId() {
        if (this.idColumn != null) {
            throw new IllegalArgumentException("This entity has more than 1 id column!");
        }
    }

    private Class<?> getTargetClass(Field field, Class<?> target) {
        return target == Object.class ? field.getType() : target;
    }

    private boolean isRelationTable(RelationType type) {
        return type == RelationType.ONE_TO_MANY || type == RelationType.MANY_TO_MANY;
    }

    private String getName(Class<?> tClass) {
        Entity entity = ReflectionUtils.getAnnotation(tClass, Entity.class);
        if (entity == null) {
            throw new IllegalArgumentException(String.format("%s isn't entity!", tClass));
        }

        return getStandardName(entity.name(), tClass.getSimpleName());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<?> entityClass() {
        return entityClass;
    }

    @Override
    public IdColumn getIdColumn() {
        return idColumn;
    }

    @Override
    public List<EntityColumn> getEntityColumns() {
        return entityColumns;
    }

    @Override
    public List<RelationColumn> getRelationColumns() {
        return relationColumns;
    }

    @Override
    public List<database.table.RelationTable> getRelationTables() {
        return tables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleEntityTable table = (SimpleEntityTable) o;
        return Objects.equals(entityClass, table.entityClass);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(entityClass);
    }

    @Override
    public String toString() {
        return getName();
    }
}
