package jsonFileIO;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public interface JsonReader<T> extends Closeable {
    T readObject() throws IOException;
    T readObject(Predicate<T> filter) throws IOException;
    List<T> readAllObjects() throws IOException;
    List<T> readAllObjects(Predicate<T> filter) throws IOException;
}
