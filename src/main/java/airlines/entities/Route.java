package airlines.entities;

import annotations.*;
import lab_6.graph.RelationType;

import java.util.Objects;
@Entity
public class Route {

    @AutoIncrementable
    @PrimaryKey
    private Long ID;

    @Relation(type = RelationType.MANY_TO_ONE)
    @Unique(constraintName = "route_unique")
    private final Airport from;
    @Relation(type = RelationType.MANY_TO_ONE)
    @Unique(constraintName = "route_unique")
    private final Airport to;

    private Route() {
        this(null, null);
    }

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

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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
        return Objects.equals(from, route.from) &&
                Objects.equals(to, route.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return String.format("Route{from = %s, to = %s}", from, to);
    }
}
