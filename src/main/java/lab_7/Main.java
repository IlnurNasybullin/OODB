package lab_7;

import analyse.ClassScanner;
import databaseAnalyzer.analyzer.DatabaseAnalyzer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static final String URL = "jdbc:postgresql://127.0.0.1:5432/Airlines";
    public static final String USER = "postgres";
    public static final String PASSWORD = "password";

    public static void main(String[] args) throws IOException, URISyntaxException, SQLException {
        List<Class<?>> classes = new ClassScanner().get(URI.create("airlines/entities"));
        DatabaseAnalyzer analyzer = new DatabaseAnalyzer(classes, DriverManager.getConnection(URL, USER, PASSWORD));
        analyzer.analyze();
    }
}
