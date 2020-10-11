package lab_5.db;

import airlines.utilData.geographic.GeographicCoordinates;
import airlines.utilData.geographic.GeographicPosition;
import airlines.utilData.metrics.Length;
import lab_5.AbstractPGObjectWrapper;
import org.postgresql.util.PGtokenizer;

import java.time.ZoneId;

public class GeographicPositionPGObject extends AbstractPGObjectWrapper<GeographicPosition> {

    public static final String GEOGRAPHIC_COORDINATES = "geographiccoordinates";
    public static final String LENGTH = "Length";

    public GeographicPositionPGObject(String name) {
        super(name);
    }

    public GeographicPositionPGObject(GeographicPosition object, String name) {
        super(object, name);
    }

    @Override
    public void setValue(String value) {
        PGtokenizer t = new PGtokenizer(tokenPrepare(value), ',');
        GeographicCoordinatesPGObject geographicCoordinates = new GeographicCoordinatesPGObject(GEOGRAPHIC_COORDINATES);
        geographicCoordinates.setValue(t.getToken(0));

        GeographicCoordinates coordinates = geographicCoordinates.getObject();
        ZoneId zoneId = ZoneId.of(t.getToken(1));
        String country = t.getToken(2);
        String city = t.getToken(3);

        LengthPGObject lengthPGObject = new LengthPGObject(LENGTH);
        lengthPGObject.setValue(t.getToken(4));
        Length length = lengthPGObject.getObject();

        object = new GeographicPosition(coordinates);
        object.setTimeZone(zoneId);
        object.setCountry(country);
        object.setCity(city);
        object.setAltitude(length);
    }

    @Override
    public String getValue() {
        GeographicCoordinatesPGObject pgObject = new GeographicCoordinatesPGObject(object.getCoordinates(), GEOGRAPHIC_COORDINATES);
        LengthPGObject lengthPGObject = new LengthPGObject(object.getAltitude(), LENGTH);

        String country = preparedStatement(object.getCountry());
        String city = preparedStatement(object.getCity());

        return getOutputPreparedValue(pgObject.getRowString(), object.getTimeZone().getId(), country, city, lengthPGObject.getRowString());
    }
}
