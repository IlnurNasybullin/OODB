package lab_8.generator;

import airlines.entities.Airplane;
import airlines.entities.FlightPassport;
import airlines.entities.Staff;
import airlines.userTypes.StaffPosition;

import java.util.List;
import java.util.Set;

public class FlightPassportRandomGenerator implements RandomInfinityGenerator<FlightPassport> {

    private final RandomInfinityGenerator<Long> idGenerator;
    private final RandomInfinityGenerator<String> passportIdGenerator;
    private final RandomInfinityGenerator<Airplane> airplaneGenerator;
    private final RandomInfinityGenerator<String> operatedCompanyGenerator;
    private final RandomInfinityGenerator<Staff> staffGenerator;

    public FlightPassportRandomGenerator() {
        this.idGenerator = new IdRandomGenerator();
        this.passportIdGenerator = createPassportIdGenerator();
        this.airplaneGenerator = new AirplaneRandomGenerator();
        this.operatedCompanyGenerator = new NameStringRandomGenerator();
        this.staffGenerator = new StaffRandomGenerator();
    }

    private RandomInfinityGenerator<String> createPassportIdGenerator() {
        StringRandomGenerator stringGenerator = new StringIdRandomGenerator(false);
        stringGenerator.setLength(15);
        return stringGenerator;
    }

    @Override
    public FlightPassport next() {
        FlightPassport flightPassport = new FlightPassport(passportIdGenerator.next());
        flightPassport.setID(idGenerator.next());
        flightPassport.setOperatedCompany(operatedCompanyGenerator.next());
        flightPassport.setAirplane(airplaneGenerator.next());

        Set<Staff> staffs = getStaffs();
        flightPassport.addStaffs(staffs);

        return flightPassport;
    }

    private Set<Staff> getStaffs() {
        List<Staff> staffs = staffGenerator.next(5);
        staffs.get(0).setPosition(StaffPosition.PILOT);
        staffs.get(1).setPosition(StaffPosition.ASSISTANT);

        return Set.copyOf(staffs);
    }
}
