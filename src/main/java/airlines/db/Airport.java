package airlines.db;

import java.time.ZoneId;

public class Airport {

    private final String airportID;

    private String name;
    private String city;
    private ZoneId timeZone;

    public Airport(String airportID) {
        this.airportID = airportID;
    }

    public String getAirportID() {
        return airportID;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }
}
