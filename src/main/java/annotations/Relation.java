package annotations;

import lab_6.graph.RelationType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Column
public @interface Relation {

    Class<?> DEFAULT_CLASS = Object.class;

    String name() default "";
    Class<?> target() default Object.class;
    RelationType type();
}
