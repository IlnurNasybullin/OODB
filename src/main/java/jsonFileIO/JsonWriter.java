package jsonFileIO;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.util.Collection;

public interface JsonWriter<T> extends Flushable, Closeable {
    void writeObject(T object) throws IOException;
    void writeAllObjects(Collection<T> objects) throws IOException;
}
