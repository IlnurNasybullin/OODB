package lab_8;

import airlines.entities.Flight;
import lab_8.dbManager.SimpleEntityManager;

import java.util.Collection;

public class SelectTree {
    private final SimpleEntityManager manager;

    public SelectTree(SimpleEntityManager manager) {
        this.manager = manager;
    }

    public void select(Collection<Flight> flights) {
        for (Flight flight: flights) {
            select(flight);
        }
    }

    private void select(Flight flight) {
        Flight select = this.manager.select(Flight.class, flight.getID());
        System.out.println(select);
    }
}
