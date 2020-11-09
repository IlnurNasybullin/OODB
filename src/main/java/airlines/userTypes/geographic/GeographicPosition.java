package airlines.userTypes.geographic;

import airlines.userTypes.metrics.Length;
import annotations.TypeComponent;
import annotations.UserType;

import java.sql.Types;
import java.time.ZoneId;
import java.util.Objects;

@UserType
public class GeographicPosition {

    private static final GeographicCoordinates DEFAULT_GEOGRAPHIC_POSITION;

    @TypeComponent
    private final GeographicCoordinates coordinates;
    @TypeComponent(SQLType = Types.VARCHAR)
    private ZoneId timeZone;
    @TypeComponent
    private String country;
    @TypeComponent
    private String city;
    @TypeComponent
    private Length altitude;

    static {
        Latitude latitude = Latitude.of(0);
        Longitude longitude = Longitude.of(0);
        DEFAULT_GEOGRAPHIC_POSITION = new GeographicCoordinates(latitude, longitude);
    }

    private GeographicPosition() {
        this(DEFAULT_GEOGRAPHIC_POSITION);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicPosition position = (GeographicPosition) o;
        return Objects.equals(coordinates, position.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coordinates);
    }

    @Override
    public String toString() {
        return String.format("Geographic position {coordinates = %s}", coordinates);
    }
}
