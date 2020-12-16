package lab_8.dbManager.type;

import java.util.Locale;

public class SQLObjectStringConverter {
    public static void writeObject(StringBuilder builder, Object object, Class<?> type) {
        if (type.isPrimitive()) {
            if (isDouble(type)) {
                writeDoublePrimitive(builder, object);
            }

            if (isFloat(type)) {
                writeFloatPrimitive(builder, object);
            }

            if (isLong(type)) {
                writeLongPrimitive(builder, object);
            }

            if (isInteger(type) || isByte(type) || isShort(type)) {
                writeIntegerPrimitive(builder, object);
            }

            if (isCharacter(type)) {
                writeCharacterPrimitive(builder, (Character)object);
            }

            return;
        }

        writeString(builder, object);
    }

    public static void writeCharacterPrimitive(StringBuilder builder, Character object) {
        if (object == null) {
            builder.append('\u0000');
            return;
        }

        builder.append(object);
    }

    public static void writeIntegerPrimitive(StringBuilder builder, Object object) {
        if (object == null) {
            builder.append(0L);
            return;
        }

        builder.append(Integer.valueOf(object.toString()));
    }

    public static void writeLongPrimitive(StringBuilder builder, Object object) {
        if (object == null) {
            builder.append(0L);
            return;
        }

        builder.append(Long.valueOf(object.toString()));
    }

    public static void writeFloatPrimitive(StringBuilder builder, Object object) {
        if (object == null) {
            builder.append(0.0f);
            return;
        }

        builder.append(Float.valueOf(object.toString()));
    }

    public static void writeDoublePrimitive(StringBuilder builder, Object object) {
        if (object == null) {
            builder.append(0.0d);
            return;
        }

        builder.append(Double.valueOf(object.toString()));
    }

    public static void writeNull(StringBuilder builder) {
        builder.append("NULL");
    }

    public static void writeString(StringBuilder builder, Object string) {
        if (string == null) {
            writeNull(builder);
            return;
        }

        builder.append(string.toString());
    }

    private static boolean isCharacter(Class<?> typeParameter) {
        return typeParameter == char.class || typeParameter == Character.class;
    }

    private static boolean isBoolean(Class<?> typeParameter) {
        return typeParameter == boolean.class || typeParameter == Boolean.class;
    }

    private static boolean isByte(Class<?> typeParameter) {
        return typeParameter == byte.class || typeParameter == Byte.class;
    }

    private static boolean isShort(Class<?> typeParameter) {
        return typeParameter == short.class || typeParameter == Short.class;
    }

    private static boolean isInteger(Class<?> typeParameter) {
        return typeParameter == int.class || typeParameter == Integer.class;
    }

    private static boolean isLong(Class<?> typeParameter) {
        return typeParameter == long.class || typeParameter == Long.class;
    }

    private static boolean isFloat(Class<?> typeParameter) {
        return typeParameter == float.class || typeParameter == Float.class;
    }

    private static boolean isDouble(Class<?> typeParameter) {
        return typeParameter == double.class || typeParameter == Double.class;
    }

    public static Object readObject(String string, Class<?> type) {
        if (isDouble(type)) {
            return Double.valueOf(string);
        }

        if (isFloat(type)) {
            return Float.valueOf(string);
        }

        if (isLong(type)) {
            return Long.valueOf(string);
        }

        if (isInteger(type)) {
            return Integer.valueOf(string);
        }

        if (isShort(type)) {
            return Short.valueOf(string);
        }

        if (isByte(type)) {
            return Byte.valueOf(string);
        }

        if (isBoolean(type)) {
            return Boolean.valueOf(string);
        }

        if (isCharacter(type)) {
            return string.charAt(1);
        }

        if (type.isEnum()) {
            return Enum.valueOf((Class<? extends Enum>) type, string.toUpperCase(Locale.ROOT));
        }

        return string;
    }
}
