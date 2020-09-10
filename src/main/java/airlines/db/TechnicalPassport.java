package airlines.db;

import java.time.ZonedDateTime;

public class TechnicalPassport {

    private final String technicalPassportID;
    private ZonedDateTime checkData;
    private boolean isReady;

    public TechnicalPassport(String technicalPassportID) {
        this.technicalPassportID = technicalPassportID;
    }

    public String getTechnicalPassportID() {
        return technicalPassportID;
    }

    public ZonedDateTime getCheckData() {
        return checkData;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setCheckData(ZonedDateTime checkData) {
        this.checkData = checkData;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}
