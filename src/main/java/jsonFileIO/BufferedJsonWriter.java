package jsonFileIO;

import java.io.File;
import java.io.IOException;
import java.nio.file.OpenOption;

public class BufferedJsonWriter<T> extends AbstractJsonWriter<T> {
    public BufferedJsonWriter(File file, OpenOption... openOptions) throws IOException {
        super(file, openOptions);
    }
}
