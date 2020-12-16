package lab_8.dbManager.type;

import annotations.DefaultEnum;
import annotations.ReflectionUtils;
import annotations.UserType;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static lab_8.dbManager.type.SQLObjectStringConverter.readObject;
import static lab_8.dbManager.type.SQLObjectStringConverter.writeString;

public class RowUserType implements TypeComponent<Object> {

    private final Class<?> component;
    private final List<Field> fields;
    private final List<Method> methods;

    public RowUserType(Class<?> component) {
        this.component = component;
        this.fields = getFields(component);
        this.methods = getMethods(component);
    }

    private Constructor<?> getConstructor(Class<?> component) {
        for (Constructor<?> constructor: component.getDeclaredConstructors()) {
            if (ReflectionUtils.getAnnotation(constructor, annotations.TypeComponent.class) != null) {
                return constructor;
            }
        }

        return null;
    }

    private List<Method> getMethods(Class<?> component) {
        List<Method> methods = new ArrayList<>();
        for (Method method: component.getDeclaredMethods()) {
            if (ReflectionUtils.getAnnotation(method, annotations.TypeComponent.class) != null) {
                methods.add(method);
            }
        }

        Class<?> superClass = component.getSuperclass();
        if (superClass != Object.class  && !component.isEnum()) {
            methods.addAll(getMethods(superClass));
        }

        return List.copyOf(methods);
    }

    private List<Field> getFields(Class<?> component) {
        List<Field> fields = new ArrayList<>();
        for (Field field: component.getDeclaredFields()) {
            if (ReflectionUtils.getAnnotation(field, annotations.TypeComponent.class) != null) {
                fields.add(field);
            }
        }

        Class<?> superClass = component.getSuperclass();
        if (superClass != Object.class  && !component.isEnum()) {
            fields.addAll(getFields(superClass));
        }

        return List.copyOf(fields);
    }

    public String toString(Object object) {
        StringBuilder builder = new StringBuilder();
        boolean moreOne = fields.size() + methods.size() > 1;
        if (moreOne) {
            builder.append("(");
        }

        try {
            writeFields(builder, object);
            writeMethods(builder, object);
            if (component.isEnum()) {
                writeEnum(builder, object, component);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (moreOne) {
            builder.append(')');
        }
        return builder.toString();
    }

    private void writeFields(StringBuilder builder, Object object) throws IllegalAccessException, NoSuchMethodException {
        if (fields.isEmpty()) {
            return;
        }
        Iterator<Field> fieldIterator = fields.listIterator();
        for (;;) {
            Field field = fieldIterator.next();
            field.setAccessible(true);
            writeField(builder, object, field);
            field.setAccessible(false);
            if (!fieldIterator.hasNext()) {
                break;
            }
            builder.append(",");
        }
    }

    private void writeField(StringBuilder builder, Object object, Field field) throws IllegalAccessException, NoSuchMethodException {
        Class<?> type = field.getType();
        Object writeObject = object == null ? null : field.get(object);
        if (ReflectionUtils.getAnnotation(type, UserType.class) != null) {
            writeClass(builder, type, writeObject);
            return;
        }

        writeObject(builder, writeObject, type);
    }

    private void writeEnum(StringBuilder builder, Object object, Class<?> type) throws IllegalAccessException {
        if (object == null) {
            writeString(builder, getDefaultEnumValue(type).toString().toUpperCase(Locale.ROOT));
            return;
        }

        writeString(builder, object.toString().toUpperCase(Locale.ROOT));
    }

    private Object getDefaultEnumValue(Class<?> type) throws IllegalAccessException {
        Object value = null;
        for (Field field: type.getDeclaredFields()) {
            if (ReflectionUtils.getAnnotation(field, DefaultEnum.class) != null) {
                field.setAccessible(true);
                value = field.get(type);
                break;
            }
        }

        return value;
    }

    private void writeMethods(StringBuilder builder, Object object) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (methods.isEmpty()) {
            return;
        }
        Iterator<Method> methodIterator = methods.listIterator();
        for (;;) {
            Method method = methodIterator.next();
            method.setAccessible(true);
            writeMethod(builder, object, method);
            if (!methodIterator.hasNext()) {
                break;
            }
            builder.append(",");
        }
    }

    private void writeMethod(StringBuilder builder, Object object, Method method) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class<?> type = method.getReturnType();
        Object writeObject = object == null ? null : method.invoke(object);
        if (ReflectionUtils.getAnnotation(type, UserType.class) != null) {
            writeClass(builder, type, writeObject);
            return;
        }

        writeObject(builder, writeObject, type);
    }

    private void writeClass(StringBuilder builder, Class<?> type, Object object) throws NoSuchMethodException {
        String result = TypeFactory.createOf(type).toString(object);
        if (hasBrackets(result)) {
            builder.append('"').append(result).append('"');
        } else {
            builder.append(result);
        }
    }

    private boolean hasBrackets(String result) {
        int length = result.length();
        return result.charAt(0) == '(' & result.charAt(length - 1) == ')';
    }

    private void writeObject(StringBuilder builder, Object object, Class<?> type) {
        TypeComponent<?> component = TypeFactory.getComponent(type);
        if (component != null) {
            builder.append(component.toString(object));
            return;
        }

        SQLObjectStringConverter.writeObject(builder, object, type);
    }

    @Override
    public Object toObject(String string) {
        Pattern pattern = Pattern.compile("^\\((.+)\\)$");
        Matcher matcher = pattern.matcher(string);
        Object object = null;
        String[] split;
        if (matcher.find()) {
            split = matcher.group(1).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        } else {
            split = new String[]{string};
        }

        AtomicInteger index = new AtomicInteger(0);
        try {
            object = setConstructor(split, index);
            setFields(object, split, index.get());
            return object;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return object;
    }

    private void setFields(Object object, String[] strings, int index) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        int i = index;
        for (Field field: fields) {
            field.setAccessible(true);
            Object value = getObject(strings[i], field.getType());
            setValue(object, field, value);
            field.setAccessible(false);
            i++;
        }
    }

    private void setValue(Object object, Field field, Object value) throws IllegalAccessException {
        if (value == null) {
            return;
        }
        TypeComponent<?> component = TypeFactory.getComponent(field.getType());
        if (component != null) {
            field.set(object, component.toObject(value.toString()));
            return;
        }

        field.set(object, value);
    }

    private Object setConstructor(String[] strings, AtomicInteger index) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        if (component.isEnum()) {
            return component.getEnumConstants()[0];
        }


        Constructor<?> constructor = getConstructor(component);
        if (constructor == null) {
            constructor = component.getDeclaredConstructor();
        }
        constructor.setAccessible(true);

        int parameterCount = constructor.getParameterCount();
        if (parameterCount == 0) {
            Object o = constructor.newInstance();
            constructor.setAccessible(false);
            return o;
        }


        Parameter[] parameters = constructor.getParameters();
        Object[] args = new Object[parameterCount];
        int i = 0;
        for (; i < args.length; i++) {
            args[i] = getObject(strings[i], parameters[i].getType());
        }

        index.set(i);
        Object o = constructor.newInstance(args);
        constructor.setAccessible(false);
        return o;
    }

    private Object getObject(String string, Class<?> typeParameter) throws NoSuchMethodException {
        if (string.length() == 0) {
            return null;
        }
        if (ReflectionUtils.getAnnotation(typeParameter, UserType.class) != null) {
            Pattern pattern = Pattern.compile("^\"(.+)\"$");
            Matcher matcher = pattern.matcher(string);
            String str = string;
            if (matcher.find()) {
                str = matcher.group(1);
            }

            return TypeFactory.createOf(typeParameter).toObject(str);
        }

        TypeComponent<?> component = TypeFactory.getComponent(typeParameter);
        if (component != null) {
            return component.toObject(string);
        }

        return readObject(string, typeParameter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RowUserType that = (RowUserType) o;
        return Objects.equals(component, that.component);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(component);
    }
}
