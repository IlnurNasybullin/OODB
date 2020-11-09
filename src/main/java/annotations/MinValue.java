package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface MinValue {
    byte byteValue() default Byte.MIN_VALUE;
    short shortValue() default Short.MIN_VALUE;
    int intValue() default Integer.MIN_VALUE;
    long longValue() default Long.MIN_VALUE;

    float floatValue() default Float.MIN_VALUE;
    double doubleValue() default Double.MIN_VALUE;
}
