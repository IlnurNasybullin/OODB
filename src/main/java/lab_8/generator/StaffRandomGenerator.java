package lab_8.generator;

import airlines.entities.Person;
import airlines.entities.Staff;
import airlines.userTypes.StaffPosition;

import java.util.Random;

public class StaffRandomGenerator implements RandomInfinityGenerator<Staff> {

    private final RandomInfinityGenerator<Person> personGenerator;
    public static final int MAX_HOURS = 50_000;

    public StaffRandomGenerator() {
        this.personGenerator = new PersonRandomGenerator();
    }

    @Override
    public Staff next() {
        Person person = personGenerator.next();
        Staff staff = new Staff(person.getPassportID());
        StaffPosition[] positions = StaffPosition.values();

        Random random = new Random();
        staff.setPosition(positions[random.nextInt(positions.length)]);
        staff.setFlightHours(random.nextInt(MAX_HOURS));
        staff.setAddress(person.getAddress());
        staff.setFullName(person.getFullName());
        staff.setID(person.getID());
        staff.setPhoneNumber(person.getPhoneNumber());

        return staff;
    }
}
