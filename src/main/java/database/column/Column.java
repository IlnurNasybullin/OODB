package database.column;

import annotations.ReflectionUtils;
import annotations.UserType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static database.DatabaseStandardSpecification.getStandardName;

public interface Column {
    String getName();
    Field getField();

    default String getUserType() {
        Field field = getField();
        Class<?> fieldType = field.getType();
        UserType type = ReflectionUtils.getAnnotation(fieldType, UserType.class);
        return type == null ? null : getStandardName(type.name(), fieldType.getSimpleName());
    }

    default <T extends Annotation> T getAnnotation(Class<T> annotation) {
        return ReflectionUtils.getAnnotation(getField(), annotation);
    }
}
