package lab_8;

import airlines.entities.*;
import lab_8.dbManager.SimpleEntityManager;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class InsertTree {

    private final SimpleEntityManager manager;

    public InsertTree(SimpleEntityManager manager) {
        this.manager = manager;
    }

    public void insert(Collection<Flight> flights) {
        for (Flight flight: flights) {
            insert(flight);
        }
    }

    private void insert(Flight flight) {
        insert(flight.getFlightPassport());
        insert(flight.getRoute());
        insert(flight.getPlainTickets());

        manager.insert(flight);
    }

    private void insert(Set<PlainTicket> plainTickets) {
        for (PlainTicket plainTicket: plainTickets) {
            insert(plainTicket);
        }
    }

    private void insert(PlainTicket plainTicket) {
        insert(plainTicket.getPassenger());
        manager.insert(plainTicket);
    }

    private void insert(Passenger passenger) {
        manager.insert(passenger);
    }

    private void insert(Route route) {
        insert(route.getFrom());
        insert(route.getTo());
        manager.insert(route);
    }

    private void insert(Airport airport) {
        manager.insert(airport);
    }

    private void insert(FlightPassport flightPassport) {
        insert(flightPassport.getAirplane());
        insert(flightPassport.getStaffs());

        manager.insert(flightPassport);
    }

    private void insert(List<Staff> staffs) {
        for (Staff staff: staffs) {
            insert(staff);
        }
    }

    private void insert(Staff staff) {
        manager.insert(staff);
    }

    private void insert(Airplane airplane) {
        insert(airplane.getTechnicalPassport());
        manager.insert(airplane);
    }

    private void insert(TechnicalPassport technicalPassport) {
        manager.insert(technicalPassport);
    }

}
