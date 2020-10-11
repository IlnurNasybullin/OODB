package lab_5.db;

import airlines.db.Airport;
import airlines.db.Route;
import lab_5.AbstractPGObjectWrapper;
import org.postgresql.util.PGtokenizer;

public class RoutePGObject extends AbstractPGObjectWrapper<Route> {

    public static final String AIRPORT = "airport";

    public RoutePGObject(String name) {
        super(name);
    }

    public RoutePGObject(Route object, String name) {
        super(object, name);
    }

    @Override
    public void setValue(String value) {
        PGtokenizer t = new PGtokenizer(tokenPrepare(value), ',');
        AirportPGObject pgObject = new AirportPGObject(AIRPORT);

        pgObject.setValue(t.getToken(0));
        Airport from = pgObject.getObject();

        pgObject.setValue(t.getToken(1));
        Airport to = pgObject.getObject();

        object = Route.of(from, to);
    }

    @Override
    public String getValue() {
        AirportPGObject airportPGObject = new AirportPGObject(AIRPORT);

        airportPGObject.setObject(object.getFrom());
        RowString from = airportPGObject.getRowString();

        airportPGObject.setObject(object.getTo());
        RowString to = airportPGObject.getRowString();

        return getOutputPreparedValue(from, to);
    }
}
