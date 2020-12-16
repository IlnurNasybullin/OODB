package airlines.userTypes;

import annotations.TypeComponent;
import annotations.UserType;

import java.util.Objects;

@UserType
public class FullName {
    @TypeComponent
    private String lastName;
    @TypeComponent
    private String name;
    @TypeComponent
    private String fatherName;

    public FullName() { }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public String getFatherName() {
        return fatherName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return Objects.equals(lastName, fullName.lastName) &&
                Objects.equals(name, fullName.name) &&
                Objects.equals(fatherName, fullName.fatherName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, name, fatherName);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", lastName, name, fatherName);
    }
}
