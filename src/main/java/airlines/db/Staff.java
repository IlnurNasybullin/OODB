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

    public Staff(String ID, FullName fullName, Address address, int flightHours, StaffPosition position) {
        this.ID = ID;
        this.fullName = fullName;
        this.address = address;
        this.flightHours = flightHours;
        this.position = position;
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
}
