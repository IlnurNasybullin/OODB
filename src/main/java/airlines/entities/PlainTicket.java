package airlines.entities;

import airlines.userTypes.TicketType;
import annotations.*;

import java.util.Objects;
@Entity
public class PlainTicket {
    @ID
    @Column
    private Long ID;
    @Column
    private final String ticketID;
    @Column
    private TicketType ticketType;
    @Column
    private Double cost;
    @Column
    private String place;
    @Column
    @Relation(type = RelationType.MANY_TO_ONE)
    private Passenger passenger;

    private PlainTicket() {
        this("");
    }

    public PlainTicket(String ticketID) {
        this.ticketID = ticketID;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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

    public Double getCost() {
        return cost;
    }

    public String getPlace() {
        return place;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public void setCost(Double cost) {
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
        return Objects.equals(ID, that.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlainTicket{");
        sb.append("\n\t").append("ID=").append(ID);
        sb.append("\n\tticketID='").append(ticketID).append('\'');
        sb.append("\n\tticketType=").append(ticketType);
        sb.append("\n\tcost=").append(cost);
        sb.append("\n\tplace='").append(place).append('\'');
        sb.append("\n\tpassenger=").append(passenger);
        sb.append("\n").append('}');
        return sb.toString();
    }
}
