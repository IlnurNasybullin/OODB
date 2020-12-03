package airlines.entities;

import airlines.userTypes.Address;
import airlines.userTypes.FullName;
import annotations.Column;
import annotations.ID;

import java.util.Objects;

public class Person {
    @ID
    @Column
    protected Long ID;
    @Column
    protected final String passportID;
    @Column
    protected FullName fullName;
    @Column
    protected Address address;
    @Column
    protected String phoneNumber;

    protected Person() {
        this("");
    }

    public Person(String passportID) {
        Objects.requireNonNull(passportID);
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

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return passportID.equals(person.passportID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(passportID);
    }

    @Override
    public String toString() {
        return passportID;
    }
}