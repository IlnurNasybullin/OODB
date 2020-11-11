package airlines.entities;

import airlines.userTypes.Address;
import airlines.userTypes.FullName;
import annotations.*;

import java.util.Objects;
@Entity
public class Passenger {

    @AutoIncrementable
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
    private String phoneNumber;

    private Passenger() {
        this("");
    }

    public Passenger(String passportID) {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return passportID.equals(passenger.passportID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(passportID);
    }

    @Override
    public String toString() {
        return String.format("Passenger{passportID = %s}", passportID);
    }
}
