package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Types;

@Column
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Numeric {

    short DEFAULT_SCALE = 0;

    SQLType type();
    int precision();
    short scale() default DEFAULT_SCALE;

    enum SQLType {
        NUMERIC(Types.NUMERIC), DECIMAL(Types.DECIMAL);

        SQLType(int SQLType) {
            this.SQLType = SQLType;
        }

        int SQLType;
    }
}
