package airlines.utilData.geographic;

import airlines.utilData.metrics.Length;

import java.time.ZoneId;

public class GeographicPosition {

    private final GeographicCoordinates coordinates;
    private ZoneId timeZone;
    private String country;
    private String city;
    private Length altitude;

    public GeographicPosition(GeographicCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public GeographicCoordinates getCoordinates() {
        return coordinates;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Length getAltitude() {
        return altitude;
    }

    public void setAltitude(Length altitude) {
        this.altitude = altitude;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }
}
