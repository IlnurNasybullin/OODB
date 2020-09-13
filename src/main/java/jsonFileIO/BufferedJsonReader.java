package jsonFileIO;

import java.io.File;
import java.io.FileNotFoundException;

public class BufferedJsonReader<T> extends AbstractJsonReader<T> {
    public BufferedJsonReader(File file, Class<T> tClass) throws FileNotFoundException {
        super(file, tClass);
    }
}
