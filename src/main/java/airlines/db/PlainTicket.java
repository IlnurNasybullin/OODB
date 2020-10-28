package airlines.db;

import lab_6.annotations.Column;
import lab_6.annotations.Entity;
import lab_6.annotations.ManyToOne;

import java.util.Objects;
@Entity
public class PlainTicket {
    @Column
    private final String ticketID;
    @Column
    @ManyToOne
    private TicketType ticketType;
    @Column
    private double cost;
    @Column
    private String place;
    @Column
    @ManyToOne
    private Passenger passenger;

    public PlainTicket(String ticketID) {
        this.ticketID = ticketID;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public String getTicketID() {
        return ticketID;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public double getCost() {
        return cost;
    }

    public String getPlace() {
        return place;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlainTicket that = (PlainTicket) o;
        return ticketID.equals(that.ticketID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketID);
    }
}
