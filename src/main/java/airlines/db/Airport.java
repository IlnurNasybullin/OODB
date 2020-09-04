package airlines.db;

import java.time.ZoneId;

public class Airport {

    private final String ID;
    private String name;
    private String city;
    private ZoneId timeZone;

    public Airport(String ID, String name, String city, ZoneId timeZone) {
        this.ID = ID;
        this.name = name;
        this.city = city;
        this.timeZone = timeZone;
    }

    public String getID() {
        return ID;
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
}
