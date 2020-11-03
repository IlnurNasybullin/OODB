package airlines.db;

import lab_6.annotations.Column;
import lab_6.annotations.Entity;
import lab_6.annotations.Relation;
import lab_6.graph.RelationType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
@Entity
public class FlightPassport {
    @Column
    private final String flightPassportID;
    @Column
    @Relation(type = RelationType.ONE_TO_ONE)
    private Airplane airplane;
    @Column
    private String operatedCompany;
    @Column
    @Relation(type = RelationType.ONE_TO_MANY, targetClass = Staff.class)
    private List<Staff> staffs;

    public FlightPassport(String flightPassportID, List<Staff> staffs) {
        this.flightPassportID = flightPassportID;
        this.staffs = staffs;
        this.staffs = new ArrayList<>();
    }

    public String getFlightPassportID() {
        return flightPassportID;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public String getOperatedCompany() {
        return operatedCompany;
    }

    public List<Staff> getStaffs() {
        return new ArrayList<>(staffs);
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public void setOperatedCompany(String operatedCompany) {
        this.operatedCompany = operatedCompany;
    }

    public boolean addStaff(Staff staff) {
        return this.staffs.add(staff);
    }

    public boolean addStaff(Collection<Staff> staffs) {
        return this.staffs.addAll(staffs);
    }

    public boolean removeStaff(Staff staff) {
        return this.staffs.remove(staff);
    }

    public boolean removeStaffs(Collection<Staff> staffs) {
        return this.staffs.removeAll(staffs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightPassport that = (FlightPassport) o;
        return flightPassportID.equals(that.flightPassportID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightPassportID);
    }
}
