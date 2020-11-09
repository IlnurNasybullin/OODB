package airlines.entities;

import airlines.userTypes.Address;
import airlines.userTypes.FullName;
import airlines.userTypes.StaffPosition;
import annotations.*;
import graph.RelationType;

import java.util.Objects;
@Entity
public class Staff {

    @Column
    @ID
    @PrimaryKey
    private Long ID;

    @Column
    @Unique
    private final String passportID;
    @Column
    private FullName fullName;
    @Column
    private Address address;
    @Column
    private Integer flightHours;
    @Column
    private StaffPosition position;

    private Staff() {
        this("");
    }

    public Staff(String passportID) {
        this.passportID = passportID;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getPassportID() {
        return passportID;
    }

    public FullName getFullName() {
        return fullName;
    }

    public Address getAddress() {
        return address;
    }

    public Integer getFlightHours() {
        return flightHours;
    }

    public StaffPosition getPosition() {
        return position;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setFlightHours(Integer flightHours) {
        this.flightHours = flightHours;
    }

    public void setPosition(StaffPosition position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return passportID.equals(staff.passportID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(passportID);
    }

    @Override
    public String toString() {
        return String.format("Staff{passportID = %s}", passportID);
    }
}
