package airlines.db;

import airlines.utilData.Address;
import airlines.utilData.FullName;

public class Passenger {

    private final String passportID;
    private FullName fullName;

    private Address address;
    private String phoneNumber;

    public Passenger(String passportID) {
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
}
