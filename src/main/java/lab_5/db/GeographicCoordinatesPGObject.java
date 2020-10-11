package lab_5.db;

import airlines.utilData.geographic.GeographicCoordinates;
import lab_5.AbstractPGObjectWrapper;
import org.postgresql.util.PGtokenizer;

public class GeographicCoordinatesPGObject extends AbstractPGObjectWrapper<GeographicCoordinates> {

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    public GeographicCoordinatesPGObject(String name) {
        super(name);
    }

    public GeographicCoordinatesPGObject(GeographicCoordinates object, String name) {
        super(object, name);
    }

    @Override
    public void setValue(String value) {
        PGtokenizer t = new PGtokenizer(tokenPrepare(value), ',');
        LatitudePGObject latitude = new LatitudePGObject(LATITUDE);
        LongitudePGObject longitude = new LongitudePGObject(LONGITUDE);

        latitude.setValue(t.getToken(0));
        longitude.setValue(t.getToken(1));

        object = new GeographicCoordinates(latitude.getObject(), longitude.getObject());
    }

    @Override
    public String getValue() {
        LatitudePGObject latitude = new LatitudePGObject(object.getLatitude(), LATITUDE);
        LongitudePGObject longitude = new LongitudePGObject(object.getLongitude(), LONGITUDE);

        return getOutputPreparedValue(latitude.getRowString(), longitude.getRowString());
    }
}
