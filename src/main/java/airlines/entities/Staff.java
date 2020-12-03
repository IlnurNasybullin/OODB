package airlines.entities;

import airlines.userTypes.StaffPosition;
import annotations.Column;
import annotations.Entity;
@Entity
public class Staff extends Person {
    @Column
    private Integer flightHours;
    @Column
    private StaffPosition position;

    protected Staff() {
        super("");
    }

    public Integer getFlightHours() {
        return flightHours;
    }

    public StaffPosition getPosition() {
        return position;
    }

    public void setFlightHours(Integer flightHours) {
        if (flightHours < 0) {
            throw new IllegalArgumentException("Flight hours mustn't be less 0!");
        }
        this.flightHours = flightHours;
    }

    public void setPosition(StaffPosition position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return String.format("Staff{passportID = %s}", passportID);
    }
}
