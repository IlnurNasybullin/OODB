package lab_5.db;

import airlines.userTypes.IATA;
import lab_5.AbstractPGObjectWrapper;
import org.postgresql.util.PGtokenizer;

public class IATAPGObject extends AbstractPGObjectWrapper<IATA> {
    public IATAPGObject(String name) {
        super(name);
    }

    public IATAPGObject(IATA object, String name) {
        super(object, name);
    }

    @Override
    public void setValue(String value) {
        PGtokenizer t = new PGtokenizer(tokenPrepare(value), ',');
        object = IATA.of(t.getToken(0));
    }

    @Override
    public String getValue() {
        return getOutputPreparedValue(object.getID());
    }
}
