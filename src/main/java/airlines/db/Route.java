package airlines.db;

import java.time.LocalTime;

public class Route {

    private final Airport from;
    private final Airport to;

    private double cost;

    private LocalTime duration;

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

    public double getCost() {
        return cost;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }
}
