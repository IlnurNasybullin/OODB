package lab_8.generator;

import airlines.entities.Airport;
import airlines.entities.Route;

public class RouteRandomGenerator implements RandomInfinityGenerator<Route> {

    private final RandomInfinityGenerator<Airport> airportGenerator;
    private final RandomInfinityGenerator<Long> idGenerator;

    public RouteRandomGenerator() {
        this.airportGenerator = new AirportRandomGenerator();
        this.idGenerator = new IdRandomGenerator();
    }

    @Override
    public Route next() {
        Airport from = airportGenerator.next();
        Airport to = from;
        while (to.equals(from)) {
            to = airportGenerator.next();
        }

        Route route = new Route(from, to);
        route.setID(idGenerator.next());
        return route;
    }
}
