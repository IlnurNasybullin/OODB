package lab_8.dbManager.type;

import java.util.HashMap;
import java.util.Map;

public class TypeFactory {

    private static final Map<Class<?>, RowUserType> components;
    private static final Map<Class<?>, TypeComponent<?>> registeredClasses;

    static {
        components = new HashMap<>();
        registeredClasses = new HashMap<>();
    }

    public static RowUserType createOf(Class<?> tClass) throws NoSuchMethodException {
        if (components.containsKey(tClass)) {
            return components.get(tClass);
        } else {
            RowUserType component = new RowUserType(tClass);
            components.put(tClass, component);
            return component;
        }
    }

    public static void register(Class<?> registerClass, TypeComponent<?> component) {
        registeredClasses.put(registerClass, component);
    }

    public static TypeComponent<?> getComponent(Class<?> tClass) {
        return registeredClasses.getOrDefault(tClass, null);
    }
}
