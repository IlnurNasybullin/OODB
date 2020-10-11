package lab_5;

import airlines.db.Route;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lab_5.dbHandler.DatabaseHandler;
import lab_5.dbHandler.RouteDAO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        Set<Route> routeSet = readFromFile();
        try(DatabaseHandler handler = new DatabaseHandler()) {
            RouteDAO dao = new RouteDAO();
            Connection connection = handler.getConnection();
            System.out.println(dao.insertRoutes(connection, routeSet));
            List<Route> routes = dao.selectRoutes(handler.getConnection());
            for (Route route : routes) {
                System.out.println(route);
            }
        }
    }

    private static Set<Route> readFromFile() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(ZoneId.class, (JsonDeserializer<Object>) (jsonElement, type, jsonDeserializationContext) -> {
            JsonObject object = jsonElement.getAsJsonObject();
            return ZoneId.of(object.get("id").getAsString());
        }).create();
        String JSON = Files.readString(Paths.get("./src/main/resources/routes.json"));
        return gson.fromJson(JSON, new TypeToken<Set<Route>>(){}.getType());
    }
}
