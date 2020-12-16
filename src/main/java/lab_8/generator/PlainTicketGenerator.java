package lab_8.generator;

import airlines.entities.Passenger;
import airlines.entities.Person;
import airlines.entities.PlainTicket;
import airlines.userTypes.TicketType;

import java.util.Random;

public class PlainTicketGenerator implements RandomInfinityGenerator<PlainTicket> {

    private final RandomInfinityGenerator<Long> idGenerator;
    private final RandomInfinityGenerator<String> ticketIdGenerator;
    private final RandomInfinityGenerator<Person> personGenerator;

    private final char[] places = {'A', 'B', 'C'};

    public PlainTicketGenerator() {
        this.idGenerator = new IdRandomGenerator();
        this.personGenerator = new PersonRandomGenerator();
        this.ticketIdGenerator = getTicketIdGenerator();
    }

    private StringIdRandomGenerator getTicketIdGenerator() {
        StringIdRandomGenerator ticketIdGenerator = new StringIdRandomGenerator(true);
        ticketIdGenerator.setLength(12);
        return ticketIdGenerator;
    }

    @Override
    public PlainTicket next() {
        PlainTicket ticket = new PlainTicket(ticketIdGenerator.next());
        TicketType[] types = TicketType.values();
        Random random = new Random();
        ticket.setTicketType(types[random.nextInt(types.length)]);
        ticket.setCost((random.nextInt(6) + 1) * 1_000d);
        ticket.setID(idGenerator.next());

        ticket.setPlace(getPlace(random));
        ticket.setPassenger(getPassenger());

        return ticket;
    }

    private Passenger getPassenger() {
        Person person = personGenerator.next();
        Passenger passenger = new Passenger(person.getPassportID());
        passenger.setAddress(person.getAddress());
        passenger.setID(person.getID());
        passenger.setFullName(person.getFullName());
        passenger.setPhoneNumber(person.getPhoneNumber());

        return passenger;
    }

    private String getPlace(Random random) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(random.nextInt(12 - 5) + 5);
        buffer.append(places[random.nextInt(places.length)]);

        return buffer.toString();
    }
}
