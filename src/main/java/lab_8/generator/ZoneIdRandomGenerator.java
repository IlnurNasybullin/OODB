package lab_8.generator;

import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ZoneIdRandomGenerator implements RandomInfinityGenerator<ZoneId> {

    private final List<String> zones;

    public ZoneIdRandomGenerator() {
        this.zones = ZoneId.SHORT_IDS.values().stream().filter(s -> !s.contains(":"))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ZoneId next() {
        return ZoneId.of(zones.get(new Random().nextInt(zones.size())));
    }
}
