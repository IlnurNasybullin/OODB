package airlines.db;

import airlines.utilData.Address;
import airlines.utilData.FullName;
import airlines.utilData.StaffPosition;

public class Staff {

    private final String ID;
    private FullName fullName;
    private Address address;
    private int flightHours;

    private StaffPosition position;

    public Staff(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
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
}
