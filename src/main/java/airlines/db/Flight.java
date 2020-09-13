package airlines.db;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Flight {

    private final Route route;

    private final LocalDateTime start;
    private final LocalDateTime end;

    private LocalDateTime realStartTime;
    private LocalDateTime realEndTime;

    private boolean successful;

    private FlightPassport flightPassport;

    private List<PlainTicket> plainTickets;

    public Flight(Route route, LocalDateTime start, LocalDateTime end) {
        checkData(start, end);
        this.route = route;
        this.start = start;
        this.end = end;
    }

    private void checkData(LocalDateTime firstDateTime, LocalDateTime secondDateTime) {
        if (secondDateTime.isAfter(firstDateTime)) {
            return;
        }

        throw new IllegalArgumentException("Даты расположены не в хронологическом порядке!");
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

    public List<PlainTicket> getPlainTickets() {
        return new ArrayList<>(plainTickets);
    }

    public void setRealTimes(LocalDateTime realStartTime, LocalDateTime realEndTime) {
        checkData(realStartTime, realEndTime);
        this.realStartTime = realStartTime;
        this.realEndTime = realEndTime;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public void setFlightPassport(FlightPassport flightPassport) {
        this.flightPassport = flightPassport;
    }

    public boolean addPlainTicket(PlainTicket plainTicket) {
        return this.plainTickets.add(plainTicket);
    }

    public boolean addPlainTickets(Collection<PlainTicket> plainTickets) {
        return this.plainTickets.addAll(plainTickets);
    }

    public boolean removePlainTicket(PlainTicket plainTicket) {
        return this.plainTickets.remove(plainTicket);
    }

    public boolean removePlainTickets(Collection<PlainTicket> plainTickets) {
        return this.plainTickets.removeAll(plainTickets);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return route.equals(flight.route) &&
                start.equals(flight.start) &&
                end.equals(flight.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(route, start, end);
    }
}
