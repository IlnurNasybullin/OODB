package airlines.entities;

import annotations.*;
import graph.RelationType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Flight {

    @Column
    @ID
    @PrimaryKey
    private Long ID;

    @Column
    @Relation(type = RelationType.MANY_TO_ONE)
    private final Route route;
    @Column
    private LocalDateTime start;
    @Column
    private LocalDateTime end;
    @Column
    private LocalDateTime realStartTime;
    @Column
    private LocalDateTime realEndTime;
    @Column
    private Boolean successful;
    @Column
    @Unique
    @Relation(type = RelationType.ONE_TO_ONE)
    private FlightPassport flightPassport;
    @Column
    @Relation(targetClass = PlainTicket.class, type = RelationType.ONE_TO_MANY)
    private Set<PlainTicket> plainTickets;

    private Flight() {
        this(null);
    }

    public Flight(Route route) {
        this.route = route;
        this.plainTickets = new HashSet<>();
    }

    private static void checkData(LocalDateTime firstDateTime, LocalDateTime secondDateTime) {
        if (firstDateTime == null || secondDateTime == null || secondDateTime.isAfter(firstDateTime)) {
            return;
        }

        throw new IllegalArgumentException("Даты расположены не в хронологическом порядке!");
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Route getRoute() {
        return route;
    }

    public void setStart(LocalDateTime start) {
        checkData(start, end);
        this.start = start;
    }

    public void setEnd(LocalDateTime end) {
        checkData(start, end);
        this.end = end;
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

    public void setRealStartTime(LocalDateTime realStartTime) {
        checkData(realStartTime, realEndTime);
        this.realStartTime = realStartTime;
    }

    public LocalDateTime getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(LocalDateTime realEndTime) {
        checkData(realStartTime, realEndTime);
        this.realEndTime = realEndTime;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public FlightPassport getFlightPassport() {
        return flightPassport;
    }

    public void setFlightPassport(FlightPassport flightPassport) {
        this.flightPassport = flightPassport;
    }

    public boolean add(PlainTicket ticket) {
        return plainTickets.add(ticket);
    }

    public boolean addAll(Collection<? extends PlainTicket> tickets) {
        return plainTickets.addAll(tickets);
    }

    public boolean remove(PlainTicket ticket) {
        return plainTickets.remove(ticket);
    }

    public boolean removeAll(Collection<? extends PlainTicket> tickets) {
        return plainTickets.removeAll(tickets);
    }

    public void clearTickets() {
        plainTickets.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(flightPassport, flight.flightPassport);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(flightPassport);
    }

    @Override
    public String toString() {
        return String.format("Flight{route = %s}", route);
    }
}
