package annotations;

import java.lang.annotation.*;
import java.lang.reflect.Field;

@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraint {
    String name() default "";
    String fieldName() default "";
    String typeName() default "";

    Range range() default @Range(minValue = @MinValue, maxValue = @MaxValue);
}
