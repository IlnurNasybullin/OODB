package airlines.db;

import lab_6.annotations.Column;
import lab_6.annotations.Entity;
import lab_6.annotations.OneToOne;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.BiPredicate;

@Entity
public class Airplane {
    @Column
    private final String registrationNumber;
    @Column
    private String name;
    @Column
    private String airlineName;
    @Column
    private LocalDate rollingDate;
    @Column
    private LocalDate maidenFlightDate;
    @Column
    private LocalDate registrationDate;
    @Column
    private LocalDate nearestOperationDate;
    @Column
    @OneToOne
    private TechnicalPassport technicalPassport;

    public Airplane(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public LocalDate getRollingDate() {
        return rollingDate;
    }

    public void setRollingDate(LocalDate rollingDate) {
        checkRollingDate(rollingDate);
        this.rollingDate = rollingDate;
    }

    private void checkRollingDate(LocalDate rollingDate) {
        checkDate(rollingDate, LocalDate::isBefore, maidenFlightDate, registrationDate, nearestOperationDate);
    }

    private void checkDate(LocalDate checkDate, BiPredicate<LocalDate, LocalDate> predicate, LocalDate ... dates) {
        for (LocalDate date: dates) {
            if (Objects.nonNull(date) && predicate.negate().test(checkDate, date)) {
                throw new IllegalArgumentException("Дата установлена неверно!");
            }
        }
    }

    public LocalDate getMaidenFlightDate() {
        return maidenFlightDate;
    }

    public void setMaidenFlightDate(LocalDate maidenFlightDate) {
        checkMaidenFlightDate(maidenFlightDate);
        this.maidenFlightDate = maidenFlightDate;
    }

    private void checkMaidenFlightDate(LocalDate maidenFlightDate) {
        checkDate(maidenFlightDate, LocalDate::isAfter, rollingDate);
        checkDate(maidenFlightDate, LocalDate::isBefore, registrationDate, nearestOperationDate);
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        checkRegistrationDate(registrationDate);
        this.registrationDate = registrationDate;
    }

    private void checkRegistrationDate(LocalDate registrationDate) {
        checkDate(registrationDate, LocalDate::isAfter, rollingDate, maidenFlightDate);
        checkDate(registrationDate, LocalDate::isBefore, nearestOperationDate);
    }

    public LocalDate getNearestOperationDate() {
        return nearestOperationDate;
    }

    public void setNearestOperationDate(LocalDate nearestOperationDate) {
        checkNearestOperationDate(nearestOperationDate);
        this.nearestOperationDate = nearestOperationDate;
    }

    private void checkNearestOperationDate(LocalDate nearestOperationDate) {
        checkDate(nearestOperationDate, LocalDate::isAfter, rollingDate, maidenFlightDate, registrationDate);
    }

    public TechnicalPassport getTechnicalPassport() {
        return technicalPassport;
    }

    public void setTechnicalPassport(TechnicalPassport technicalPassport) {
        this.technicalPassport = technicalPassport;
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
