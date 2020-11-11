package lab_4;

import airlines.entities.Route;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static handlers.Config.*;

public class DBExample {

    private static final Set<Route> routes;
    private static Gson gson;

    public static final String ROUTES_PATH = "./src/main/resources/routes.json";

    static {
        String JSON = null;
        gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(ZoneId.class, (JsonDeserializer<Object>) (jsonElement, type, jsonDeserializationContext) -> ZoneId.of(jsonElement.getAsJsonObject().get("id").getAsString()))
                .create();
        try {
            JSON = Files.readString(Path.of(ROUTES_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        routes = gson.fromJson(JSON, new TypeToken<Set<Route>>(){}.getType());
    }

    public static void main(String[] args) {
        try (DatabaseHandler handler = DatabaseHandler.of(url, userName, password)) {
            insertTest(handler);
            selectTest(handler);
            hardSelect(handler);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void hardSelect(DatabaseHandler handler) throws SQLException {
        System.out.println("Start hard select");
        hardSelect(handler, json_column);
        hardSelect(handler, jsonb_column);
        System.out.println("Finish hard select");
    }

    private static void hardSelect(DatabaseHandler handler, String column) throws SQLException {
        String query = getHardSelectQuery(column);

        long start = System.nanoTime();
        ResultSet resultSet = handler.executeResult(query);
        List<DataSet> json = getDataSet(resultSet, "city", "len");
        long end = System.nanoTime();
        double seconds = getSeconds(start, end);
        System.out.println(String.format("%s - %.4f s", column, seconds));
        System.out.println(String.format("Size of list - %d", json.size()));
    }

    private static double getSeconds(long start, long end) {
        return ((double) (end - start)) / 1_000_000_000;
    }

    private static List<DataSet> getDataSet(ResultSet resultSet, String stringColumn, String doubleColumn) throws SQLException {
        List<DataSet> dataSets = new ArrayList<>(2324);
        while (resultSet.next()) {
            String city = resultSet.getString(stringColumn);
            double len = resultSet.getDouble(doubleColumn);
            dataSets.add(new DataSet(city, len));
        }

        return dataSets;
    }

    private static String getHardSelectQuery(String column) {
        String jsonFunction = (column.equals(json_column)) ? "json_array_elements" : "jsonb_array_elements";
        String query = "WITH json_table AS (SELECT elements -> 'from' -> 'position' ->> 'city' AS city, " +
                        "CAST(elements -> 'from' -> 'position' -> 'altitude' ->> 'maxLength' AS REAL) AS len " +
                        String.format("FROM %s CROSS JOIN %s(%s) AS elements) SELECT DISTINCT city, len FROM json_table WHERE len > 300 ORDER BY LEN", tableName, jsonFunction, column);

        return query;
    }

    private static void selectTest(DatabaseHandler handler) throws SQLException {
        System.out.println("Start select");
        select(handler, json_column);
        select(handler, jsonb_column);
        System.out.println("Finish select");
    }

    private static void select(DatabaseHandler handler, String column) throws SQLException {
        long start = System.nanoTime();
        ResultSet resultSet = handler.select(tableName, Set.of(column), false);
        List<String> json = getJsonList(column, resultSet);
        long end = System.nanoTime();
        double seconds = getSeconds(start, end);
        System.out.println(String.format("%s - %.4f s", column, seconds));
        System.out.println(String.format("Size of list - %d", json.size()));
    }

    private static List<String> getJsonList(String column, ResultSet resultSet) throws SQLException {
        List<String> json = new ArrayList<>(100);
        while (resultSet.next()) {
            json.add(resultSet.getString(column));
        }
        return json;
    }

    private static void insertTest(DatabaseHandler handler) {
        List<Route> routes = new ArrayList<>(DBExample.routes);
        Collections.shuffle(routes);
        List<Route>[] lists = new List[100];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = routes.subList(i * 100, (i + 1) * 100);
        }

        List<String> jsonList = Arrays.stream(lists).parallel().map(DBExample::toGson).collect(Collectors.toList());

        System.out.println("Start insert");
        insertTest(handler, json_column, jsonList);
        insertTest(handler, jsonb_column, jsonList);
        System.out.println("Finish insert");
    }

    private static void insertTest(DatabaseHandler handler, String column, List<String> json) {
        long start = System.nanoTime();
        try {
            insert(handler, column, json);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        long end = System.nanoTime();
        double seconds = getSeconds(start, end);
        System.out.println(String.format("%s - %.4f s", column, seconds));
    }

    private static void insert(DatabaseHandler handler, String column, List<String> json) throws SQLException {
        for (String js: json) {
            handler.insertData(tableName, Map.of(column, js));
        }
    }

    private static String toGson(List<Route> routes) {
        gson = gson.newBuilder().create();
        return gson.toJson(routes);
    }

    private static class DataSet {
        private String city;
        private double length;

        public DataSet(String city, double length) {
            this.city = city;
            this.length = length;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public double getLength() {
            return length;
        }

        public void setLength(double length) {
            this.length = length;
        }
    }
}
