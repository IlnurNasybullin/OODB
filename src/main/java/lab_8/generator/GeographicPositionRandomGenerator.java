package lab_8.generator;

import airlines.userTypes.geographic.GeographicCoordinates;
import airlines.userTypes.geographic.GeographicPosition;
import airlines.userTypes.metrics.Length;
import airlines.userTypes.metrics.LengthType;

import java.time.ZoneId;
import java.util.Random;

public class GeographicPositionRandomGenerator implements RandomInfinityGenerator<GeographicPosition> {

    private final RandomInfinityGenerator<GeographicCoordinates> geographicCoordinatesGenerator;
    private final RandomInfinityGenerator<ZoneId> zoneIdGenerator;
    private final RandomInfinityGenerator<String> nameStringRandomGenerator;
    private final RandomInfinityGenerator<Length> lengthGenerator;

    public GeographicPositionRandomGenerator() {
        this.geographicCoordinatesGenerator = new GeographicCoordinatesRandomGenerator();
        this.zoneIdGenerator = new ZoneIdRandomGenerator();
        this.nameStringRandomGenerator = new NameStringRandomGenerator();
        this.lengthGenerator = createLengthGenerator();
    }

    private RandomInfinityGenerator<Length> createLengthGenerator() {
        return new RandomInfinityGenerator<>() {
            private final double MAX_LENGTH = 5_000;

            @Override
            public Length next() {
                Random random = new Random();
                double length = random.nextDouble() * MAX_LENGTH;
                LengthType[] values = LengthType.values();
                LengthType type = values[random.nextInt(values.length)];

                Length l = new Length(length);
                l.setType(type);

                return l;
            }
        };
    }

    @Override
    public GeographicPosition next() {
        GeographicPosition geographicPosition = new GeographicPosition(geographicCoordinatesGenerator.next());
        geographicPosition.setTimeZone(zoneIdGenerator.next());
        geographicPosition.setAltitude(lengthGenerator.next());
        geographicPosition.setCity(nameStringRandomGenerator.next());
        geographicPosition.setCountry(nameStringRandomGenerator.next());

        return geographicPosition;
    }
}
