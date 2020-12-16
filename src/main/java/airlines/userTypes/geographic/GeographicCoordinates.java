package airlines.userTypes.geographic;

import annotations.TypeComponent;
import annotations.UserType;

import java.util.Objects;

@UserType
public class GeographicCoordinates {
    @TypeComponent
    private final Latitude latitude;
    @TypeComponent
    private final Longitude longitude;

    public static final GeographicCoordinates DEFAULT = new GeographicCoordinates();

    private GeographicCoordinates() {
        this(Latitude.DEFAULT, Longitude.DEFAULT);
    }

    public GeographicCoordinates(Latitude latitude, Longitude longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Latitude getLatitude() {
        return latitude;
    }

    public Longitude getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicCoordinates that = (GeographicCoordinates) o;
        return Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return String.format("%s %s", latitude, longitude);
    }
}
