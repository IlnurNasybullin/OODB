package airlines.entities;

import annotations.Entity;

@Entity
public class Passenger extends Person {

    @Override
    public String toString() {
        return String.format("Passenger{passportID = %s}", passportID);
    }
}
