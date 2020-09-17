import airlines.db.Airport;
import airlines.db.Flight;
import airlines.db.Route;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Flight> flightList = getFlights(10);
        File file = new File("./src/main/resources/flights.txt");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String JSON = gson.toJson(flightList);

        Files.write(file.toPath(), JSON.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        String JSONfromFile = Files.readString(file.toPath());

        List<Flight> flights = gson.fromJson(JSONfromFile, new TypeToken<List<Flight>>(){}.getType());
        flights.sort(Comparator.comparing(Flight::getStart));
        flights.forEach(System.out::println);
    }

    private static List<Flight> getFlights(int count) {
        List<Flight> flights = new ArrayList<>(count);
        Route route = getRoute();

        for (int i = 0; i < count; i++) {
            LocalDateTime start = getRandomDate();
            LocalDateTime end = start.plusHours(7);

            flights.add(new Flight(route, start, end));
        }

        return flights;
    }

    private static LocalDateTime getRandomDate() {
        int day = (int) (Math.random() * 30 + 1);
        int hour = (int) (Math.random() * 5 + 8);
        return LocalDateTime.of(2020, 8, day, hour, 0, 0);
    }

    private static Route getRoute() {
        Airport from = new Airport("UL56");
        Airport to = new Airport("RU01");

        Route route = new Route(from, to);

        return route;
    }
}
