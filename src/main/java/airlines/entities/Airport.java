package airlines.entities;

import airlines.userTypes.AirportICAO;
import airlines.userTypes.IATA;
import airlines.userTypes.geographic.GeographicPosition;
import annotations.Column;
import annotations.Entity;
import annotations.PrimaryKey;
import annotations.Unique;
import annotations.ID;

import java.util.Objects;

@Entity
public class Airport {

    @Column
    @ID
    @PrimaryKey
    private Long ID;

    @Column
    @Unique
    private final AirportICAO icaoID;
    @Column
    @Unique
    private IATA iataID;
    @Column
    @Unique
    private GeographicPosition position;

    private Airport() {
        this(AirportICAO.DEFAULT);
    }

    public Airport(AirportICAO icaoID) {
        this.icaoID = icaoID;
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
        Airport that = (Airport) o;
        return Objects.equals(icaoID, that.icaoID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(icaoID);
    }

    @Override
    public String toString() {
        return String.format("Airport{icaoID = %s}", icaoID);
    }
}
