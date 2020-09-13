package airlines.db;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

public class TechnicalPassport {

    private final String technicalPassportID;

    private ZonedDateTime lastCheckData;
    private boolean isReady;

    public TechnicalPassport(String technicalPassportID) {
        this.technicalPassportID = technicalPassportID;
    }

    public String getTechnicalPassportID() {
        return technicalPassportID;
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
        return technicalPassportID.equals(that.technicalPassportID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(technicalPassportID);
    }
}
