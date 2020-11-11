package annotations;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Renames.class)
public @interface Rename {
    String fieldName();
    String sqlName();
}
