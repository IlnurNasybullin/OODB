package airlines.db;

import airlines.utilData.TechnicalPassportID;
import lab_6.annotations.Column;
import lab_6.annotations.Entity;

import java.time.OffsetDateTime;
import java.util.Objects;
@Entity
public class TechnicalPassport {
    @Column
    private final TechnicalPassportID ID;
    @Column
    private OffsetDateTime lastCheckDate;
    @Column
    private boolean isReady;

    public TechnicalPassport(TechnicalPassportID ID) {
        this.ID = ID;
    }

    public TechnicalPassportID getID() {
        return ID;
    }

    public OffsetDateTime getLastCheckDate() {
        return lastCheckDate;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setLastCheckDate(OffsetDateTime lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
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
