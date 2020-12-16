package lab_8.generator;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class OffsetDateTimeRandomGenerator implements RandomInfinityGenerator<OffsetDateTime> {

    private final RandomInfinityGenerator<LocalDateTime> dateTimeGenerator;
    private final RandomInfinityGenerator<ZoneId> zoneIdGenerator;

    public OffsetDateTimeRandomGenerator() {
        this.dateTimeGenerator = new LocalDateTimeRandomGenerator();
        this.zoneIdGenerator = new ZoneIdRandomGenerator();
    }

    @Override
    public OffsetDateTime next() {
        return OffsetDateTime.of(dateTimeGenerator.next(),
                zoneIdGenerator.next().getRules().getOffset(Instant.now()));
    }
}
