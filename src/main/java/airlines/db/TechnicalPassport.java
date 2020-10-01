package airlines.db;

import airlines.utilData.TechnicalPassportID;

import java.time.ZonedDateTime;
import java.util.Objects;

public class TechnicalPassport {

    private final TechnicalPassportID ID;

    private ZonedDateTime lastCheckData;
    private boolean isReady;

    public TechnicalPassport(TechnicalPassportID ID) {
        this.ID = ID;
    }

    public TechnicalPassportID getID() {
        return ID;
    }

    public ZonedDateTime getLastCheckData() {
        return lastCheckData;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setLastCheckData(ZonedDateTime lastCheckData) {
        this.lastCheckData = lastCheckData;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechnicalPassport that = (TechnicalPassport) o;
        return ID.equals(that.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
