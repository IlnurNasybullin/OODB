package lab_5.db;

import airlines.userTypes.metrics.Length;
import airlines.userTypes.metrics.LengthType;
import lab_5.AbstractPGObjectWrapper;
import org.postgresql.util.PGtokenizer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class LengthPGObject extends AbstractPGObjectWrapper<Length> {

    public LengthPGObject(String name) {
        super(name);
    }

    public LengthPGObject(Length object, String name) {
        super(object, name);
    }

    @Override
    public void setValue(String value) {
        PGtokenizer t = new PGtokenizer(tokenPrepare(value), ',');

        double length = Double.parseDouble(t.getToken(0));
        String lengthType = t.getToken(1);

        object = new Length(length, LengthType.valueOf(lengthType));
    }

    @Override
    public String getValue() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("####.##");
        format.setDecimalFormatSymbols(dfs);

        return String.format("(%s,'%s')", format.format(object.getLength()), object.getType());
    }
}
