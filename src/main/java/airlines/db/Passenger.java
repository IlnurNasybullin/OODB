package airlines.db;

import airlines.utilData.Address;
import airlines.utilData.FullName;

public class Passenger {

    private final String passportID;
    private FullName fullName;

    private Address address;
    private String phoneNumber;

    public Passenger(String passportID, FullName fullName, Address address, String phoneNumber) {
        this.passportID = passportID;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
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
}
