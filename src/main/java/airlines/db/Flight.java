package airlines.db;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Flight {

    private final String ID;
    private LocalDateTime start;
    private LocalDateTime end;
    private boolean successful;

    private List<Passenger> passengers;
    private Airplane airplane;

    public Flight(String id, LocalDateTime start, LocalDateTime end, boolean successful, Airplane airplane) {
        ID = id;
        this.start = start;
        this.end = end;
        this.successful = successful;
        this.airplane = airplane;
        this.passengers = new ArrayList<>();
    }

    public String getID() {
        return ID;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public List<Passenger> getPassengers() {
        return new ArrayList<>(passengers);
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public boolean addPassenger(Passenger passenger) {
        return this.passengers.add(passenger);
    }

    public boolean addPassengers(Collection<Passenger> passengers) {
        return this.passengers.addAll(passengers);
    }
}
