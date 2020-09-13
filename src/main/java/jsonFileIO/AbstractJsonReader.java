package jsonFileIO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

abstract class AbstractJsonReader<T> implements JsonReader<T> {

    private BufferedReader reader;
    public static final char endJSONObject = '}';
    private Class<T> tClass;
    private Gson gson;

    private Function<String, Optional<T>> function = new Function<>() {
        private StringBuffer buffer = new StringBuffer();

        @Override
        public Optional<T> apply(String s) {
            T obj = null;
            buffer.append(s);
            if (s.charAt(0) == endJSONObject) {
                obj = gson.fromJson(buffer.toString(), tClass);
                buffer.delete(0, buffer.length());
            }

            return Optional.ofNullable(obj);
        }
    };

    protected AbstractJsonReader(File file, Class<T> tClass) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(file));
        this.tClass = tClass;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public T readObject() throws IOException {
        return readObject(t -> true);
    }

    @Override
    public T readObject(Predicate<T> filter) throws IOException {
        String str;
        Optional<T> optional;
        T obj;
        while ((str = reader.readLine()) != null) {
            optional = function.apply(str);
            if (optional.isPresent() && filter.test((obj = optional.get()))) {
                return obj;
            }
        }

        throw new EOFException("Достигнут конец файла!");
    }

    private boolean isJsonEnd(String str) {
        return str.charAt(0) == endJSONObject;
    }

    @Override
    public List<T> readAllObjects() {
        return readAllObjects(t -> true);
    }

    @Override
    public List<T> readAllObjects(Predicate<T> filter) {
        List<String> strings = reader.lines().collect(Collectors.toList());
        List<T> objects = new ArrayList<>();
        Optional<T> optional;
        T obj;

        for (String str: strings) {
            optional = function.apply(str);
            if (optional.isPresent() && filter.test(obj = optional.get())) {
                objects.add(obj);
            }
        }

        return objects;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
