package annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface CompositeType {
    String DEFAULT_NAME = "";

    String name() default DEFAULT_NAME;
}
