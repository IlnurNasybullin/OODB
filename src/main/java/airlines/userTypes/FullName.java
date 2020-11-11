package airlines.userTypes;

import annotations.CompositeType;
import annotations.TypeComponent;

import java.util.Objects;
@CompositeType
public class FullName {
    @TypeComponent
    private String familyName;
    @TypeComponent
    private String name;
    @TypeComponent
    private String fatherName;

    public FullName() { }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFamilyName() {
        return familyName;
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
        return Objects.equals(familyName, fullName.familyName) &&
                Objects.equals(name, fullName.name) &&
                Objects.equals(fatherName, fullName.fatherName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(familyName, name, fatherName);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", familyName, name, fatherName);
    }
}
