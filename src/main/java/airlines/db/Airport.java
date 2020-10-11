package airlines.db;

import airlines.utilData.AirportICAO;
import airlines.utilData.IATA;
import airlines.utilData.geographic.GeographicPosition;

import java.util.Objects;

public class Airport {

    private final AirportICAO icaoID;
    private IATA iataID;
    private GeographicPosition position;

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
        Airport airport = (Airport) o;
        return icaoID.equals(airport.icaoID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icaoID);
    }

    @Override
    public String toString() {
        return "Airport{" +
                "icaoID=" + icaoID +
                ", iataID=" + iataID +
                ", position=" + position +
                '}';
    }
}
