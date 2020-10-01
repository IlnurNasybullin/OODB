package airlines.db;

import java.util.Objects;

public class Route {

    private final Airport from;
    private final Airport to;

    public static Route of(Airport from, Airport to) {
        checkAirports(from, to);
        return new Route(from, to);
    }

    private static void checkAirports(Airport from, Airport to) {
        if (Objects.isNull(from) || Objects.isNull(to) || Objects.equals(from, to)) {
            throw new IllegalArgumentException(String.format("Аэропорты указаны неверно! (%s, %s)", from, to));
        }
    }

    private Route(Airport from, Airport to) {
        this.from = from;
        this.to = to;
    }

    public Airport getFrom() {
        return from;
    }

    public Airport getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return from.equals(route.from) &&
                to.equals(route.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
