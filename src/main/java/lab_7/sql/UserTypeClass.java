package lab_7.sql;

import annotations.TypeComponent;
import annotations.UserType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserTypeClass implements SQLType {

    @UserType
    private static class EmptyUserType{};

    private final Class<?> userTypeClass;
    private final Set<Field> components;

    private UserTypeClass() {
        this(EmptyUserType.class);
    }

    public UserTypeClass(Class<?> userTypeClass) {
        checkClass(userTypeClass);
        this.userTypeClass = userTypeClass;
        this.components = createComponents(userTypeClass);
    }

    private Set<Field> createComponents(Class<?> userTypeClass) {
        Set<Field> fields = new HashSet<>();

        for (Field field: userTypeClass.getDeclaredFields()) {
            if (field.getDeclaredAnnotation(TypeComponent.class) != null) {
                fields.add(field);
            }
        }

        Annotation declaredAnnotation = userTypeClass.getDeclaredAnnotation(UserType.class);
        if (declaredAnnotation == null) {
            return Set.copyOf(fields);
        }

        for (Class<?> targetClass: ((UserType) declaredAnnotation).targetClass()) {
            fields.addAll(createComponents(targetClass));
        }

        return Set.copyOf(fields);
    }

    private void checkClass(Class<?> userTypeClass) {
        if (userTypeClass.getDeclaredAnnotation(UserType.class) == null) {
            throw new IllegalArgumentException("Class is not user type!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTypeClass that = (UserTypeClass) o;
        return userTypeClass == that.userTypeClass;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userTypeClass);
    }

    @Override
    public String toString() {
        return userTypeClass.getSimpleName();
    }

    @Override
    public Set<Class<?>> getFieldClasses() {
        return components.stream().map(Field::getType).collect(Collectors.toUnmodifiableSet());
    }
}
