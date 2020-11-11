package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface POSIX {
    int DEFAULT_LENGTH = -1;
    int DEFAULT_MIN_LENGTH = -1;
    int DEFAULT_MAX_LENGTH = -1;

    String regex();

    int minLength() default DEFAULT_MIN_LENGTH;
    int maxLength() default DEFAULT_MAX_LENGTH;

    int length() default DEFAULT_LENGTH;
}
