package lab_8.generator;

import airlines.entities.Flight;
import airlines.entities.FlightPassport;
import airlines.entities.PlainTicket;
import airlines.entities.Route;

import java.time.LocalDateTime;
import java.util.Random;

public class FlightRandomGenerator implements RandomInfinityGenerator<Flight> {

    private final RandomInfinityGenerator<Route> routeGenerator;

    private final RandomInfinityGenerator<Long> idGenerator;
    private final RandomInfinityGenerator<FlightPassport> flightPassportGenerator;
    private final RandomInfinityGenerator<LocalDateTime> localDateTimeGenerator;
    private final RandomInfinityGenerator<PlainTicket> plainTicketGenerator;

    private double successfulProbability;
    private int maxPlainTickets;

    public FlightRandomGenerator() {
        this.routeGenerator = new RouteRandomGenerator();
        this.idGenerator = new IdRandomGenerator();
        this.localDateTimeGenerator = new LocalDateTimeRandomGenerator();
        this.flightPassportGenerator = new FlightPassportRandomGenerator();
        this.plainTicketGenerator = new PlainTicketGenerator();

        this.successfulProbability = 0.95d;
        this.maxPlainTickets = 15;
    }

    public void setSuccessfulProbability(double successfulProbability) {
        if (successfulProbability > 1 || successfulProbability < 0) {
            throw new IllegalArgumentException("probability must be value from [0, 1]");
        }

        this.successfulProbability = successfulProbability;
    }

    public void setMaxPlainTickets(int maxPlainTickets) {
        if (maxPlainTickets < 0) {
            throw new IllegalArgumentException("tickets count can't be negative number!");
        }

        this.maxPlainTickets = maxPlainTickets;
    }

    @Override
    public Flight next() {
        Flight flight = new Flight(routeGenerator.next());
        Random random = new Random();

        flight.setID(idGenerator.next());

        flight.setFlightPassport(flightPassportGenerator.next());

        LocalDateTime start = localDateTimeGenerator.next();
        flight.setStart(start);
        flight.setEnd(getRandomShiftedDateTime(random, start));

        LocalDateTime realStart = start;
        while (!realStart.isAfter(start)) {
            realStart = localDateTimeGenerator.next();
        }

        flight.setRealStartTime(realStart);
        flight.setRealEndTime(getRandomShiftedDateTime(random, realStart));

        flight.setSuccessful(random.nextDouble() <= successfulProbability);

        if (maxPlainTickets > 0) {
            flight.addAll(plainTicketGenerator.next(random.nextInt(maxPlainTickets)));
        }

        return flight;
    }

    private LocalDateTime getRandomShiftedDateTime(Random random, LocalDateTime realStart) {
        return realStart.plusHours(random.nextInt(12))
                .plusMinutes(random.nextInt(60)).plusSeconds(60);
    }
}
