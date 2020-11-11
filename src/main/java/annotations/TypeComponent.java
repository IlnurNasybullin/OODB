package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Types;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeComponent {

    String DEFAULT_NAME = "";
    int DEFAULT_SQL_TYPE = Types.OTHER;
    String DEFAULT_EXPRESSION = "";

    String name() default DEFAULT_NAME;
    int SQLType() default DEFAULT_SQL_TYPE;
    String defaultExpression() default DEFAULT_EXPRESSION;
}
