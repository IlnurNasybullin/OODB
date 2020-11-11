package airlines.entities;

import annotations.*;
import lab_6.graph.RelationType;

import java.util.*;

@Entity
public class FlightPassport {

    @AutoIncrementable
    @PrimaryKey
    private Long ID;

    @Column
    @Unique
    private final String flightPassportID;
    @Relation(type = RelationType.MANY_TO_ONE)
    private Airplane airplane;
    @Column
    private String operatedCompany;
    @Relation(type = RelationType.ONE_TO_MANY, target = Staff.class)
    private Set<Staff> staffs;

    private FlightPassport() {
        this("");
    }

    public FlightPassport(String flightPassportID) {
        this.flightPassportID = flightPassportID;
        this.staffs = new HashSet<>();
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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

    public boolean addStaff(Collection<? extends Staff> staffs) {
        return this.staffs.addAll(staffs);
    }

    public boolean removeStaff(Staff staff) {
        return this.staffs.remove(staff);
    }

    public boolean removeStaffs(Collection<? extends Staff> staffs) {
        return this.staffs.removeAll(staffs);
    }

    public void clearStaffs() {
        this.staffs.clear();
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
        return Objects.hashCode(flightPassportID);
    }

    @Override
    public String toString() {
        return String.format("FlightPassport{flightPassportID = %s}", flightPassportID);
    }
}
