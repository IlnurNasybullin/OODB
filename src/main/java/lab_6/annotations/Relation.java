package lab_6.annotations;

import lab_6.graph.RelationType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Relation {

    Class<?> DEFAULT_CLASS = Object.class;

    String name() default "";
    Class<?> targetClass() default Object.class;
    RelationType type();
}
