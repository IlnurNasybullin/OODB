package airlines.db;

import java.time.LocalDate;
import java.util.Objects;

public class Airplane {
    private final String registrationNumber;
    private String model;

    private LocalDate manufactureData;
    private LocalDate operationData;

    private TechnicalPassport technicalPassport;

    public Airplane(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setData(LocalDate manufactureData, LocalDate operationData) {
        checkData(manufactureData, operationData);
        this.manufactureData = manufactureData;
        this.operationData = operationData;
    }

    private void checkData(LocalDate firstDate, LocalDate secondDate) {
        if (secondDate.isAfter(firstDate)) {
            return;
        }

        throw new IllegalArgumentException("Даты расположены не в хронологическом порядке!");
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setTechnicalPassport(TechnicalPassport technicalPassport) {
        this.technicalPassport = technicalPassport;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public LocalDate getManufactureData() {
        return manufactureData;
    }

    public LocalDate getOperationData() {
        return operationData;
    }

    public TechnicalPassport getTechnicalPassport() {
        return technicalPassport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airplane airplane = (Airplane) o;
        return registrationNumber.equals(airplane.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber);
    }
}
