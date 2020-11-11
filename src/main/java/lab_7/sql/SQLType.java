package lab_7.sql;

import java.lang.reflect.Field;
import java.util.Set;

public interface SQLType {
    Set<Class<?>> getFieldClasses();
}
