package annotations;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraint {
    String name() default "";
    String fieldName() default "";

    Range range() default @Range(minValue = @MinValue, maxValue = @MaxValue);
}
