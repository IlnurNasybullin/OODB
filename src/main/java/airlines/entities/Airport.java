package airlines.entities;

import airlines.userTypes.AirportICAO;
import airlines.userTypes.IATA;
import airlines.userTypes.geographic.GeographicPosition;
import annotations.Column;
import annotations.Entity;
import annotations.ID;

import java.util.Objects;
@Entity
public class Airport {
    @ID
    @Column
    private Long ID;
    @Column
    private final AirportICAO icaoID;
    @Column
    private IATA iataID;
    @Column
    private GeographicPosition position;

    private Airport() {
        this(AirportICAO.DEFAULT);
    }

    public Airport(AirportICAO icaoID) {
        Objects.requireNonNull(icaoID);
        this.icaoID = icaoID;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public AirportICAO getIcaoID() {
        return icaoID;
    }

    public IATA getIataID() {
        return iataID;
    }

    public void setIataID(IATA iataID) {
        this.iataID = iataID;
    }

    public GeographicPosition getPosition() {
        return position;
    }

    public void setPosition(GeographicPosition position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Objects.equals(ID, airport.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Airport{");
        sb.append("\n\t").append("ID=").append(ID);
        sb.append("\n\ticaoID=").append(icaoID);
        sb.append("\n\tiataID=").append(iataID);
        sb.append("\n\tposition=").append(position);
        sb.append("\n").append('}');
        return sb.toString();
    }
}
