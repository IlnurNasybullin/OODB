import airlines.db.Airport;
import airlines.db.Flight;
import airlines.db.Route;
import jsonFileIO.BufferedJsonReader;
import jsonFileIO.BufferedJsonWriter;
import jsonFileIO.JsonReader;
import jsonFileIO.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws IOException {
        Flight flight = getFlight();
        File file = new File("./src/main/resources/flights.txt");
        try(JsonWriter<Flight> writer = new BufferedJsonWriter<>(file, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.writeObject(flight);
        }

        try(JsonReader<Flight> reader = new BufferedJsonReader<>(file, Flight.class)) {
            for (Flight f: reader.readAllObjects()) {
                System.out.println(f);
            }
        }
    }

    private static Flight getFlight() {
        LocalDateTime start = LocalDateTime.of(2020, 8, 14, 15, 30);
        LocalDateTime end = start.plusHours(7);
        return new Flight(getRoute(), start, end);
    }

    private static Route getRoute() {
        Airport from = new Airport("UL56");
        Airport to = new Airport("RU01");

        Route route = new Route(from, to);

        return route;
    }
}
