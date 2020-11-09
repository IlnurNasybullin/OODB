package lab_5.db;

import airlines.entities.Airport;
import lab_5.AbstractPGObjectWrapper;
import org.postgresql.util.PGtokenizer;

public class AirportPGObject extends AbstractPGObjectWrapper<Airport> {

    public static final String AIRPORT_ICAO = "airporticao";
    public static final String IATA = "iata";
    public static final String GEOGRAPHIC_POSITION = "geographicposition";

    public AirportPGObject(String name) {
        super(name);
    }

    public AirportPGObject(Airport object, String name) {
        super(object, name);
    }

    @Override
    public void setValue(String value) {
        PGtokenizer t = new PGtokenizer(tokenPrepare(value), ',');

        AirportICAOPGObject airportICAOPGObject = new AirportICAOPGObject(AIRPORT_ICAO);
        airportICAOPGObject.setValue(t.getToken(0));

        IATAPGObject iatapgObject = new IATAPGObject(IATA);
        iatapgObject.setValue(t.getToken(1));

        GeographicPositionPGObject geographicPositionPGObject = new GeographicPositionPGObject(GEOGRAPHIC_POSITION);
        geographicPositionPGObject.setValue(t.getToken(2));

        Airport airport = new Airport(airportICAOPGObject.getObject());
        airport.setPosition(geographicPositionPGObject.getObject());
        airport.setIataID(iatapgObject.getObject());

        object = airport;
    }

    @Override
    public String getValue() {
        AirportICAOPGObject airportICAOPGObject = new AirportICAOPGObject(object.getIcaoID(), AIRPORT_ICAO);
        IATAPGObject iatapgObject = new IATAPGObject(object.getIataID(), IATA);
        GeographicPositionPGObject geographicPositionPGObject = new GeographicPositionPGObject(object.getPosition(), GEOGRAPHIC_POSITION);

        return getOutputPreparedValue(airportICAOPGObject.getRowString(), iatapgObject.getRowString(), geographicPositionPGObject.getRowString());
    }
}
