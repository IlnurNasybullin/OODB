package airlines.db;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Flight {

    private final Route route;

    private final LocalDateTime start;
    private final LocalDateTime end;

    private LocalDateTime realStartTime;
    private LocalDateTime realEndTime;

    private boolean successful;

    private FlightPassport flightPassport;

    private List<Passenger> passengers;

    public Flight(Route route, LocalDateTime start, LocalDateTime end) {
        this.route = route;
        this.start = start;
        this.end = end;
    }

    public Route getRoute() {
        return route;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public LocalDateTime getRealStartTime() {
        return realStartTime;
    }

    public LocalDateTime getRealEndTime() {
        return realEndTime;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public FlightPassport getFlightPassport() {
        return flightPassport;
    }

    public List<Passenger> getPassengers() {
        return new ArrayList<>(passengers);
    }

    public void setRealStartTime(LocalDateTime realStartTime) {
        this.realStartTime = realStartTime;
    }

    public void setRealEndTime(LocalDateTime realEndTime) {
        this.realEndTime = realEndTime;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public void setFlightPassport(FlightPassport flightPassport) {
        this.flightPassport = flightPassport;
    }
}
