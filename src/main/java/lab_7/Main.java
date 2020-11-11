package lab_7;

import analyse.ClassScanner;
import lab_7.graph.SQLGraphModel;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        List<Class<?>> classList = new ClassScanner().get(URI.create("airlines/entities"));
        SQLGraphModel graph = new SQLGraphModel(classList);
    }
}
