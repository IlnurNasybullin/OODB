package airlines.entities;

import annotations.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Flight {
    @ID
    @Column
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
    @Relation(type = RelationType.ONE_TO_ONE)
    private FlightPassport flightPassport;
    @Relation(type = RelationType.MANY_TO_MANY, target = PlainTicket.class)
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

    public Set<PlainTicket> getPlainTickets() {
        return Set.copyOf(this.plainTickets);
    }

    public void clearTickets() {
        plainTickets.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(ID, flight.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Flight{");
        sb.append("\n\t").append("ID=").append(ID);
        sb.append("\n\troute=").append(route);
        sb.append("\n\tstart=").append(start);
        sb.append("\n\tend=").append(end);
        sb.append("\n\trealStartTime=").append(realStartTime);
        sb.append("\n\trealEndTime=").append(realEndTime);
        sb.append("\n\tsuccessful=").append(successful);
        sb.append("\n\tflightPassport=").append(flightPassport);
        sb.append("\n\tplainTickets=").append(plainTickets);
        sb.append("\n").append('}');
        return sb.toString();
    }
}
