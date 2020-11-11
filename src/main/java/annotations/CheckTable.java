package annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ChecksTable.class)
public @interface CheckTable {
    String DEFAULT_CONSTRAINT_NAME = "";

    String constraintName() default DEFAULT_CONSTRAINT_NAME;

    Expression first();
    CheckType type();
    Expression second();
}
