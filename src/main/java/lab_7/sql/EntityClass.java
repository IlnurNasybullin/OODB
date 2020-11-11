package lab_7.sql;

import annotations.*;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EntityClass implements SQLType{

    @Entity
    private static class EmptyEntity {}

    private final Class<?> entityClass;

    private final Set<Field> primaryKey;

    private final Set<Field> autoIncrementable;
    private final Set<Field> foreignKeys;
    private final Set<Field> attributes;
    private EntityClass() {
        this(EmptyEntity.class);
    }

    public EntityClass(Class<?> entityClass) {
        checkClass(entityClass);

        this.entityClass = entityClass;
        this.primaryKey = new HashSet<>();
        this.autoIncrementable = new HashSet<>();
        this.foreignKeys = new HashSet<>();
        this.attributes = new HashSet<>();

        fillFields();
    }

    private void fillFields() {
        for (Field field: entityClass.getDeclaredFields()) {
            if (field.getDeclaredAnnotation(PrimaryKey.class) != null) {
                addPrimaryKey(field);
            }
            if (field.getDeclaredAnnotation(AutoIncrementable.class) != null) {
                addID(field);
            }
            if (field.getDeclaredAnnotation(Relation.class) != null) {
                addForeignKey(field);
            }
            if (field.getDeclaredAnnotation(Column.class) != null) {
                addAttribute(field);
            }
        }
    }

    private void addAttribute(Field field) {
        this.attributes.add(field);
    }

    private void addForeignKey(Field field) {
        this.foreignKeys.add(field);
    }

    private void addID(Field field) {
        this.autoIncrementable.add(field);
    }

    private void addPrimaryKey(Field field) {
        if (!primaryKey.isEmpty()) {
            checkPrimaryKey(field);
        }
        primaryKey.add(field);
    }

    private void checkPrimaryKey(Field field) {
        String constraintName = field.getAnnotation(PrimaryKey.class).constraintName();
        IllegalArgumentException differentPrimaryKeyConstraintName = new IllegalArgumentException("Class contains two primary key fields with different constraint name!");
        if (constraintName.equals("")) {
            throw differentPrimaryKeyConstraintName;
        }

        for (Field key: primaryKey) {
            if (!key.getDeclaredAnnotation(PrimaryKey.class).constraintName().equals(constraintName)) {
                throw differentPrimaryKeyConstraintName;
            }
        }
    }

    private void checkClass(Class<?> entityClass) {
        if (entityClass.getDeclaredAnnotation(Entity.class) == null) {
            throw new IllegalArgumentException("Class is not entity!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityClass that = (EntityClass) o;
        return entityClass == that.entityClass;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(entityClass);
    }

    @Override
    public String toString() {
        return entityClass.getSimpleName();
    }

    @Override
    public Set<Class<?>> getFieldClasses() {
        Set<Class<?>> fieldClasses = new HashSet<>(primaryKey.size() + autoIncrementable.size() +
                foreignKeys.size() + attributes.size());

        for (Field field: primaryKey) {
            fieldClasses.add(field.getType());
        }

        for (Field field: autoIncrementable) {
            fieldClasses.add(field.getType());
        }

        for (Field field: foreignKeys) {
            Class<?> fieldClass = field.getAnnotation(Relation.class).target();
            if (fieldClass == Object.class) {
                fieldClass = field.getType();
            }

            fieldClasses.add(fieldClass);
        }

        for (Field field: attributes) {
            Class<?> fieldClass = field.getAnnotation(Column.class).target();
            if (fieldClass == Object.class) {
                fieldClass = field.getType();
            }

            fieldClasses.add(fieldClass);
        }

        return Set.copyOf(fieldClasses);
    }
}
