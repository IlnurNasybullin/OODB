package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.sql.Types;
@Retention(RetentionPolicy.RUNTIME)
public @interface Expression {
    int DEFAULT_SQL_TYPE = Types.OTHER;

    String expression();
    int SQLType() default DEFAULT_SQL_TYPE;
}
