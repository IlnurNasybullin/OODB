package lab_8.dbManager.type;

public interface TypeComponent<T> {
    String toString(Object object);
    T toObject(String string);
}
