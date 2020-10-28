package airlines.db;

import airlines.utilData.Address;
import airlines.utilData.FullName;
import lab_6.annotations.Column;
import lab_6.annotations.Entity;
import lab_6.annotations.ManyToOne;

import java.util.Objects;
@Entity
public class Staff {
    @Column
    private final String passportID;
    @Column
    private FullName fullName;
    @Column
    private Address address;
    @Column
    private int flightHours;
    @Column
    @ManyToOne
    private StaffPosition position;

    public Staff(String passportID) {
        this.passportID = passportID;
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

    public int getFlightHours() {
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

    public void setFlightHours(int flightHours) {
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
        return Objects.hash(passportID);
    }
}
