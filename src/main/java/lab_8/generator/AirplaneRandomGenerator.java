package lab_8.generator;

import airlines.entities.Airplane;
import airlines.entities.TechnicalPassport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AirplaneRandomGenerator implements RandomInfinityGenerator<Airplane> {

    private final RandomInfinityGenerator<Long> idGenerator;
    private final RandomInfinityGenerator<String> registrationNumberGenerator;
    private final RandomInfinityGenerator<String> nameStringRandomGenerator;
    private final RandomInfinityGenerator<LocalDateTime> localDateRandomGenerator;
    private final RandomInfinityGenerator<TechnicalPassport> technicalPassportRandomGenerator;

    public AirplaneRandomGenerator() {
        this.idGenerator = new IdRandomGenerator();
        this.registrationNumberGenerator = createRegistrationNumberGenerator();
        this.nameStringRandomGenerator = new NameStringRandomGenerator();
        this.localDateRandomGenerator = new LocalDateTimeRandomGenerator();
        this.technicalPassportRandomGenerator = new TechnicalPassportRandomGenerator();
    }

    private RandomInfinityGenerator<String> createRegistrationNumberGenerator() {
        StringRandomGenerator stringGenerator = new StringIdRandomGenerator(false);
        stringGenerator.setLength(15);
        return stringGenerator;
    }

    @Override
    public Airplane next() {
        Airplane airplane = new Airplane(registrationNumberGenerator.next());
        airplane.setName(nameStringRandomGenerator.next());
        airplane.setID(idGenerator.next());
        airplane.setAirlineName(nameStringRandomGenerator.next());
        List<LocalDate> dateTimeList = localDateRandomGenerator.next(4).stream().map(LocalDateTime::toLocalDate)
                .sorted().collect(Collectors.toUnmodifiableList());

        airplane.setRollingDate(dateTimeList.get(0));
        airplane.setMaidenFlightDate(dateTimeList.get(1));
        airplane.setRegistrationDate(dateTimeList.get(2));
        airplane.setNearestOperationDate(dateTimeList.get(3));

        airplane.setTechnicalPassport(technicalPassportRandomGenerator.next());

        return airplane;
    }
}
