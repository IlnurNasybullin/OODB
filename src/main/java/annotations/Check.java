package annotations;

import java.lang.annotation.*;
import java.sql.Types;

@Column
@TypeComponent
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Checks.class)
public @interface Check {
    String DEFAULT_CONSTRAINT_NAME = "";

    String constraintName() default DEFAULT_CONSTRAINT_NAME;
    CheckType type();

    Expression expression();
}
