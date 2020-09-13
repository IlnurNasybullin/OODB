package airlines.db;

import airlines.utilData.TicketType;

import java.util.Objects;

public class PlainTicket {

    private final String ticketID;
    private TicketType ticketType;
    private double cost;
    private String place;
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
