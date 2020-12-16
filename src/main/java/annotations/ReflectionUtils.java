package annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ReflectionUtils {
    public static boolean hasAnnotation(Field field, Class<? extends Annotation> annotation) {
        if (field.getDeclaredAnnotation(annotation) != null) {
            return true;
        }

        Set<Class<? extends Annotation>> annotations = new HashSet<>();
        for (Annotation ann: field.getDeclaredAnnotations()) {
            if (hasAnnotation(ann.annotationType(), annotation, annotations)) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasAnnotation(Class<? extends Annotation> ann, Class<? extends Annotation> annotation, Set<Class<? extends Annotation>> annotations) {
        if (ann.getDeclaredAnnotation(annotation) != null) {
            return true;
        }
        annotations.add(ann);

        for (Annotation an: ann.getDeclaredAnnotations()) {
            Class<? extends Annotation> annotationType = an.annotationType();
            if (!annotations.contains(annotationType) && hasAnnotation(annotationType, annotation, annotations)) {
                return true;
            }
        }

        return false;
    }

    public static <T extends Annotation> T getAnnotation(Class<?> tClass, Class<T> annotation) {
        return getAnnotation(tClass, annotation, new HashSet<>());
    }

    private static <T extends Annotation> T getAnnotation(Class<?> tClass, Class<T> annotation, Set<Class<?>> annotations) {
        T an = tClass.getDeclaredAnnotation(annotation);
        if (an != null) {
            return an;
        }

        annotations.add(tClass);

        for (Annotation annot : tClass.getDeclaredAnnotations()) {
            if (!annotations.contains(annot.annotationType())) {
                an = getAnnotation(annot.annotationType(), annotation, annotations);
                if (an != null) {
                    return an;
                }
            }
        }

        return null;
    }

    public static <T extends Annotation> T getAnnotation(Field field, Class<T> annotation) {
        T an = field.getDeclaredAnnotation(annotation);
        if (an != null) {
            return an;
        }

        return getAnnotation(field.getDeclaredAnnotations(), annotation);
    }

    private static <T extends Annotation> T getAnnotation(Annotation[] annotations, Class<T> annotation) {
        T an;
        Set<Class<?>> annotationSet = new HashSet<>();

        for (Annotation annot : annotations) {
            if (!annotationSet.contains(annot.annotationType())) {
                an = getAnnotation(annot.annotationType(), annotation, annotationSet);
                if (an != null) {
                    return an;
                }
            }
        }

        return null;
    }

    public static <T extends Annotation> T getAnnotation(Method method, Class<T> annotation) {
        T an = method.getDeclaredAnnotation(annotation);
        if (an != null) {
            return an;
        }

        return getAnnotation(method.getDeclaredAnnotations(), annotation);
    }

    public static <T extends Annotation> T getAnnotation(Constructor<?> constructor, Class<T> annotation) {
        T an = constructor.getDeclaredAnnotation(annotation);
        if (an != null) {
            return an;
        }

        return getAnnotation(constructor.getDeclaredAnnotations(), annotation);
    }
}
