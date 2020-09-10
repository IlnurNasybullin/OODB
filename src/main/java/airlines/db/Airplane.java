package airlines.db;

import java.time.LocalDate;

public class Airplane {
    private final String flightNumber;
    private String model;

    private LocalDate manufactureDate;
    private LocalDate operationData;

    private TechnicalPassport technicalPassport;

    public Airplane(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public void setOperationData(LocalDate operationData) {
        this.operationData = operationData;
    }

    public void setTechnicalPassport(TechnicalPassport technicalPassport) {
        this.technicalPassport = technicalPassport;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getModel() {
        return model;
    }
}
