package airlines.db;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Airplane {
    private final String ID;
    private String model;

    private ZonedDateTime manufactureDate;
    private int operationYearPeriod;

    private boolean isReady;
    private List<Staff> staffs;

    public Airplane(String id, String model, ZonedDateTime manufactureDate, int operationYearPeriod, boolean isReady) {
        ID = id;
        this.model = model;
        this.manufactureDate = manufactureDate;
        this.operationYearPeriod = operationYearPeriod;
        this.isReady = isReady;
        this.staffs = new ArrayList<>();
    }

    public String getID() {
        return ID;
    }

    public String getModel() {
        return model;
    }

    public ZonedDateTime getManufactureDate() {
        return manufactureDate;
    }

    public int getOperationYearPeriod() {
        return operationYearPeriod;
    }

    public boolean isReady() {
        return isReady;
    }

    public List<Staff> getStaffs() {
        return new ArrayList<>(getStaffs());
    }

    public boolean addStaff(Staff staff) {
        return this.staffs.add(staff);
    }

    public boolean addStaffs(Collection<Staff> staffs) {
        return this.staffs.addAll(staffs);
    }
}
