package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Column
public @interface AutoIncrement {
    String SERIAL = "SERIAL";

    long DEFAULT = 1L;
    long MIN_VALUE = 1L;
    long MAX_VALUE = Long.MAX_VALUE;

    long DEFAULT_START = MIN_VALUE;
    byte DEFAULT_CACHE = 1;

    boolean DEFAULT_CYCLES = false;

    Class<?> DEFAULT_OWNED_BY = NullObject.class;

    String name() default SERIAL;

    long incrementBy() default DEFAULT;
    long minValue() default MIN_VALUE;
    long maxValue() default MAX_VALUE;

    long start() default DEFAULT_START;
    byte cache() default DEFAULT_CACHE;

    boolean cycle() default DEFAULT_CYCLES;

    Class<?> ownedBy() default NullObject.class;

    class NullObject {};
}
