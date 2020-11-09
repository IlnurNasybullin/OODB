package airlines.userTypes;

import java.util.Objects;

public class FullName {

    private String familyName;
    private String name;
    private String fatherName;

    public FullName(String familyName, String name) {
        this.familyName = familyName;
        this.name = name;
    }

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
}
