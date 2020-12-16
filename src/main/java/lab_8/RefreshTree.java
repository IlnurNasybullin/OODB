package lab_8;

import airlines.entities.Flight;
import lab_8.dbManager.SimpleEntityManager;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RefreshTree {

    private final SimpleEntityManager manager;

    public RefreshTree(SimpleEntityManager manager) {
        this.manager = manager;
    }

    public void refresh(Collection<Flight> flights) {
        System.out.println(flights);
        List<Flight> collect = flights.stream().map(flight -> {
            Flight f = new Flight(flight.getRoute());
            f.setID(flight.getID());
            return f;
        }).collect(Collectors.toUnmodifiableList());

        for (Flight flight: collect) {
            manager.refresh(flight);
            System.out.println(flight);
        }
    }
}
