package airlines.entities;

import airlines.userTypes.TicketType;
import annotations.*;

import java.util.Objects;
@Entity
public class PlainTicket {
    @PrimaryKey
    @AutoIncrement
    private Long ID;
    @NotNull
    @Unique
    private final String ticketID;
    @Column
    private TicketType ticketType;
    @Check(type = CheckType.MORE_AND_EQUAL, expression = @Expression(expression = "0.00"))
    @Numeric(type = Numeric.SQLType.NUMERIC, precision = 8, scale = 2)
    private Double cost;
    @Column
    private String place;
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
        return ticketID.equals(that.ticketID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ticketID);
    }

    @Override
    public String toString() {
        return String.format("PlainTicket{ticketID = %s}", ticketID);
    }
}
