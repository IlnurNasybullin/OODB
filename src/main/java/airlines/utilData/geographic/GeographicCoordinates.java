package airlines.utilData.geographic;

public class GeographicCoordinates {
    private final Latitude latitude;
    private final Longitude longitude;

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
    public String toString() {
        return String.format("%s %s", latitude, longitude);
    }
}
