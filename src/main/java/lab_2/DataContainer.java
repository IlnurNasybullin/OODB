package lab_2;

import airlines.entities.Airport;
import airlines.entities.Flight;
import airlines.entities.Route;
import airlines.userTypes.AirportICAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataContainer {
    public static List<Flight> getFlights(int count) {
        List<Flight> flights = new ArrayList<>(count);
        Route route = getRoute();

        for (int i = 0; i < count; i++) {
            LocalDateTime start = getRandomDate();
            LocalDateTime end = start.plusHours(7);

            Flight flight = new Flight(route);
            flight.setStart(start);
            flight.setEnd(end);
            flights.add(flight);
        }

        return flights;
    }

    private static LocalDateTime getRandomDate() {
        int day = (int) (Math.random() * 30 + 1);
        int hour = (int) (Math.random() * 5 + 8);
        return LocalDateTime.of(2020, 8, day, hour, 0, 0);
    }

    private static Route getRoute() {
        Airport from = new Airport(AirportICAO.of("ULAS"));
        Airport to = new Airport(AirportICAO.of("RUUD"));

        Route route = Route.of(from, to);

        return route;
    }
}
