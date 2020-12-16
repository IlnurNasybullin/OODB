package lab_8.generator;

import airlines.entities.TechnicalPassport;
import airlines.userTypes.TechnicalPassportID;

import java.time.OffsetDateTime;
import java.util.Random;
import java.util.regex.Pattern;

public class TechnicalPassportRandomGenerator implements RandomInfinityGenerator<TechnicalPassport> {

    public static final double PROBABILITY = 0.9;
    private final RandomInfinityGenerator<Long> idGenerator;
    private final RandomInfinityGenerator<TechnicalPassportID> passportIdGenerator;
    private final RandomInfinityGenerator<OffsetDateTime> dateTimeGenerator;


    public TechnicalPassportRandomGenerator() {
        this.idGenerator = new IdRandomGenerator();
        this.passportIdGenerator = createPassportIdGenerator();
        this.dateTimeGenerator = new OffsetDateTimeRandomGenerator();
    }

    private RandomInfinityGenerator<TechnicalPassportID> createPassportIdGenerator() {
        StringRandomGenerator randomGenerator = new StringIdRandomGenerator(true);
        randomGenerator.setLength(8);
        randomGenerator.addPredicate(Pattern.compile(TechnicalPassportID.REGEX).asMatchPredicate());

        return () -> TechnicalPassportID.of(randomGenerator.next());
    }

    @Override
    public TechnicalPassport next() {
        TechnicalPassport technicalPassport = new TechnicalPassport(passportIdGenerator.next());
        technicalPassport.setID(idGenerator.next());
        technicalPassport.setReady(new Random().nextDouble() <= PROBABILITY);
        technicalPassport.setLastCheckDate(dateTimeGenerator.next());

        return technicalPassport;
    }
}
