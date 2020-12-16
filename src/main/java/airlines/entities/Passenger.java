package airlines.entities;

import annotations.Entity;

@Entity
public class Passenger extends Person {

    private Passenger() {
        super();
    }

    public Passenger(String passportId) {
        super(passportId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Passenger{");
        sb.append("\n\t").append("ID=").append(ID);
        sb.append("\n\tpassportID='").append(passportID).append('\'');
        sb.append("\n\tfullName=").append(fullName);
        sb.append("\n\taddress=").append(address);
        sb.append("\n\tphoneNumber='").append(phoneNumber).append('\'');
        sb.append("\n").append('}');
        return sb.toString();
    }
}
