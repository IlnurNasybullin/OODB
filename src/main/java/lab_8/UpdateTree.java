package lab_8;

import airlines.entities.Flight;
import lab_8.dbManager.SimpleEntityManager;

import java.util.List;

public class UpdateTree {

    private final SimpleEntityManager manager;

    public UpdateTree(SimpleEntityManager manager) {
        this.manager = manager;
    }

    public void update(List<Flight> flights) {
        System.out.println(flights);
        int size = flights.size();

        int i = 1;
        for (Flight flight: flights) {
            Flight f = flights.get(i);
            flight.setID(f.getID());
            flight.setFlightPassport(f.getFlightPassport());
            i = (i + 1) % size;
        }

        for (Flight flight: flights) {
            this.manager.update(flight);
        }
    }
}
