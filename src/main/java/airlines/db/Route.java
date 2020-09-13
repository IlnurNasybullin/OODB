package airlines.db;

import java.util.Objects;

public class Route {

    private final Airport from;
    private final Airport to;

    public Route(Airport from, Airport to) {
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
