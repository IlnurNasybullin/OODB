package airlines.entities;

import airlines.userTypes.TechnicalPassportID;
import annotations.Column;
import annotations.Entity;
import annotations.ID;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
public class TechnicalPassport {
    @ID
    @Column
    private Long ID;
    @Column
    private final TechnicalPassportID technicalPassportID;
    @Column
    private OffsetDateTime lastCheckDate;
    @Column
    private Boolean isReady;

    private TechnicalPassport() {
        this(TechnicalPassportID.DEFAULT);
    }

    public TechnicalPassport(TechnicalPassportID technicalPassportID) {
        this.technicalPassportID = technicalPassportID;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Boolean getReady() {
        return isReady;
    }

    public void setReady(Boolean ready) {
        isReady = ready;
    }

    public TechnicalPassportID getTechnicalPassportID() {
        return technicalPassportID;
    }

    public OffsetDateTime getLastCheckDate() {
        return lastCheckDate;
    }

    public void setLastCheckDate(OffsetDateTime lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechnicalPassport that = (TechnicalPassport) o;
        return Objects.equals(ID, that.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TechnicalPassport{");
        sb.append("\n\t").append("ID=").append(ID);
        sb.append("\n\ttechnicalPassportID=").append(technicalPassportID);
        sb.append("\n\tlastCheckDate=").append(lastCheckDate);
        sb.append("\n\tisReady=").append(isReady);
        sb.append("\n").append('}');
        return sb.toString();
    }
}
