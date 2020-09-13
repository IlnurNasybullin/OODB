package jsonFileIO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collection;

abstract class AbstractJsonWriter<T> implements JsonWriter<T> {

    private Gson gson;
    private BufferedWriter writer;

    protected AbstractJsonWriter(File file, OpenOption... openOptions) throws IOException {
        this.writer = Files.newBufferedWriter(file.toPath(), openOptions);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void writeObject(T object) throws IOException {
        writer.write(gson.toJson(object));
        writer.newLine();
    }

    @Override
    public void writeAllObjects(Collection<T> objects) throws IOException {
        for (T obj: objects) {
            writeObject(obj);
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }
}
