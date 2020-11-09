package lab_5.db;

import airlines.userTypes.geographic.Longitude;
import lab_5.AbstractPGObjectWrapper;
import org.postgresql.util.PGtokenizer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class LongitudePGObject extends AbstractPGObjectWrapper<Longitude> {

    public LongitudePGObject(String name) {
        super(name);
    }

    public LongitudePGObject(Longitude object, String name) {
        super(object, name);
    }

    @Override
    public void setValue(String value) {
        PGtokenizer t = new PGtokenizer(tokenPrepare(value), ',');
        object = Longitude.of(Double.parseDouble(t.getToken(0)));
    }

    @Override
    public String getValue() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("##.####");
        format.setDecimalFormatSymbols(dfs);

        return String.format("ROW(%s)", format.format(object.getFullDegrees()));
    }
}
