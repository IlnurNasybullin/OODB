package lab_8.generator;

import airlines.userTypes.geographic.GeographicCoordinates;
import airlines.userTypes.geographic.Latitude;
import airlines.userTypes.geographic.Longitude;

import java.util.Random;

public class GeographicCoordinatesRandomGenerator implements RandomInfinityGenerator<GeographicCoordinates> {

    private final RandomInfinityGenerator<Latitude> latitudeRandomGenerator;
    private final RandomInfinityGenerator<Longitude> longitudeRandomGenerator;

    public GeographicCoordinatesRandomGenerator() {
        this.latitudeRandomGenerator = () -> Latitude.of(new Random().nextDouble() * 180 - 90);
        this.longitudeRandomGenerator = () -> Longitude.of(new Random().nextDouble() * 360 - 180);
    }

    @Override
    public GeographicCoordinates next() {
        Latitude latitude = latitudeRandomGenerator.next();
        Longitude longitude = longitudeRandomGenerator.next();

        return new GeographicCoordinates(latitude, longitude);
    }
}
