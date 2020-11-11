package lab_6;

import airlines.entities.Airport;
import annotations.Column;
import annotations.ReflectionUtils;

import java.lang.reflect.Field;

public class Test {
    public static void main(String[] args) throws NoSuchFieldException {
        Field field = Airport.class.getDeclaredField("ID");
        System.out.println(ReflectionUtils.hasAnnotation(field, Column.class));
    }
}
