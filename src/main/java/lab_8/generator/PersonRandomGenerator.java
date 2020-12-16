package lab_8.generator;

import airlines.entities.Person;
import airlines.userTypes.Address;
import airlines.userTypes.FullName;

import java.util.Random;

public class PersonRandomGenerator implements RandomInfinityGenerator<Person> {

    private final RandomInfinityGenerator<Long> idGenerator;
    private final RandomInfinityGenerator<String> passportIdGenerator;
    private final RandomInfinityGenerator<FullName> fullNameGenerator;
    private final RandomInfinityGenerator<Address> addressGenerator;
    private final RandomInfinityGenerator<String> phoneNumberGenerator;

    public PersonRandomGenerator() {
        this.idGenerator = new IdRandomGenerator();
        this.passportIdGenerator = new PassportIdGenerator();
        this.fullNameGenerator = createFullNameGenerator();
        this.addressGenerator = createAddressGenerator();
        this.phoneNumberGenerator = new PhoneNumberGenerator();
    }

    private RandomInfinityGenerator<Address> createAddressGenerator() {
        return new RandomInfinityGenerator<>() {
            private final NameStringRandomGenerator nameGenerator = new NameStringRandomGenerator();

            private final int maxFlat = 15;
            private final int maxHouse = 150;

            @Override
            public Address next() {
                Address address = new Address();
                address.setCity(nameGenerator.next());
                address.setCountry(nameGenerator.next());
                Random random = new Random();

                address.setFlat(Integer.toString(random.nextInt(maxFlat) + 1));
                address.setHouse(Integer.toString(random.nextInt(maxHouse) + 1));
                address.setRegion(nameGenerator.next());
                address.setStreet(nameGenerator.next());
                address.setPostalCode(Integer.toString(random.nextInt(999_999 - 100_000) + 100_000));

                return address;
            }
        };
    }

    private RandomInfinityGenerator<FullName> createFullNameGenerator() {
        return new RandomInfinityGenerator<>() {
            private final NameStringRandomGenerator nameGenerator = new NameStringRandomGenerator();

            @Override
            public FullName next() {
                FullName fullName = new FullName();
                fullName.setLastName(nameGenerator.next());
                fullName.setName(nameGenerator.next());
                fullName.setFatherName(nameGenerator.next());
                return fullName;
            }
        };
    }

    @Override
    public Person next() {
        Person person = new Person(passportIdGenerator.next());
        person.setID(idGenerator.next());
        person.setAddress(addressGenerator.next());
        person.setFullName(fullNameGenerator.next());
        person.setPhoneNumber(phoneNumberGenerator.next());

        return person;
    }
}
