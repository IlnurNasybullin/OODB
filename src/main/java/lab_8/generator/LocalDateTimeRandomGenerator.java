package lab_8.generator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class LocalDateTimeRandomGenerator implements RandomInfinityGenerator<LocalDateTime> {

    private LocalDateTime min;
    private LocalDateTime max;

    private long secondsMaxDelta;

    private RandomInfinityGenerator<Long> positiveLongGenerator;

    public LocalDateTimeRandomGenerator() {
        this.min = LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(0, 0, 0));
        this.max = LocalDateTime.now();
        this.secondsMaxDelta = getDelta(min, max);
        this.positiveLongGenerator = new IdRandomGenerator();
    }

    private long getDelta(LocalDateTime min, LocalDateTime max) {
        return ChronoUnit.SECONDS.between(min, max);
    }

    public LocalDateTime getMin() {
        return min;
    }

    public void setMin(LocalDateTime min) {
        checkRange(min, max);
        secondsMaxDelta = getDelta(min, max);
        this.min = min;
    }

    private void checkRange(LocalDateTime min, LocalDateTime max) {
        if (!min.isBefore(max)) {
            throw new IllegalArgumentException("Min localDateTime must be less than max localDateTime!");
        }
    }

    public LocalDateTime getMax() {
        return max;
    }

    public void setMax(LocalDateTime max) {
        checkRange(min, max);
        secondsMaxDelta = getDelta(min, max);
        this.max = max;
    }

    public RandomInfinityGenerator<Long> getPositiveLongGenerator() {
        return positiveLongGenerator;
    }

    public void setPositiveLongGenerator(RandomInfinityGenerator<Long> positiveLongGenerator) {
        this.positiveLongGenerator = positiveLongGenerator;
    }

    @Override
    public LocalDateTime next() {
        long seconds;
        Random random = new Random();
        do {
            seconds = Math.abs(random.nextInt());
        } while (seconds >= secondsMaxDelta);

        return min.plusSeconds(seconds);
    }
}
