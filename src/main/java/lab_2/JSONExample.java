package lab_2;

import airlines.entities.Flight;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;

public class JSONExample {
    public static void main(String[] args) throws IOException {
        List<Flight> flightList = DataContainer.getFlights(10);
        File file = new File("./src/main/resources/flights.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String JSON = gson.toJson(flightList);

        Files.write(file.toPath(), JSON.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        String JSONfromFile = Files.readString(file.toPath());

        List<Flight> flights = gson.fromJson(JSONfromFile, new TypeToken<List<Flight>>(){}.getType());
        flights.sort(Comparator.comparing(Flight::getStart));
        flights.forEach(System.out::println);
    }
}
