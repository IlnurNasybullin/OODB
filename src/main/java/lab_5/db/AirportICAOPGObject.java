package lab_5.db;

import airlines.utilData.AirportICAO;
import lab_5.AbstractPGObjectWrapper;
import org.postgresql.util.PGtokenizer;

public class AirportICAOPGObject extends AbstractPGObjectWrapper<AirportICAO> {

    public AirportICAOPGObject(String name) {
        super(name);
    }

    public AirportICAOPGObject(AirportICAO object, String name) {
        super(object, name);
    }

    @Override
    public void setValue(String value) {
        PGtokenizer t = new PGtokenizer(tokenPrepare(value), ',');
        object = AirportICAO.of(t.getToken(0));
    }

    @Override
    public String getValue() {
        return getOutputPreparedValue(object.getID());
    }
}
