package airlines.db;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Route {

    private final String ID;
    private Airport from;
    private Airport to;
    private double cost;
    private LocalTime duration;

    private List<Flight> flights;

    public Route(String id, Airport from, Airport to, double cost, LocalTime duration) {
        ID = id;
        this.from = from;
        this.to = to;
        this.cost = cost;
        this.duration = duration;
    }

    public String getID() {
        return ID;
    }

    public Airport getFrom() {
        return from;
    }

    public Airport getTo() {
        return to;
    }

    public double getCost() {
        return cost;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public List<Flight> getFlights() {
        return new ArrayList<>(flights);
    }

    public boolean addFlight(Flight flight) {
        return this.flights.add(flight);
    }

    public boolean addFlights(Collection<Flight> flights) {
        return this.flights.addAll(flights);
    }
}
