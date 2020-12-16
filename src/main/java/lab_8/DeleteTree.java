package lab_8;

import airlines.entities.*;
import lab_8.dbManager.SimpleEntityManager;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class DeleteTree {

    private final SimpleEntityManager manager;

    public DeleteTree(SimpleEntityManager manager) {
        this.manager = manager;
    }

    public void delete(Collection<Flight> flights) {
        for (Flight flight: flights) {
            delete(flight);
        }
    }

    private void delete(Flight flight) {
        manager.remove(flight);
        delete(flight.getPlainTickets());
        delete(flight.getRoute());
        delete(flight.getFlightPassport());
    }

    private void delete(FlightPassport flightPassport) {
        this.manager.remove(flightPassport);
        delete(flightPassport.getStaffs());
        delete(flightPassport.getAirplane());
    }

    private void delete(Airplane airplane) {
        this.manager.remove(airplane);
        delete(airplane.getTechnicalPassport());
    }

    private void delete(TechnicalPassport technicalPassport) {
        this.manager.remove(technicalPassport);
    }

    private void delete(List<Staff> staffs) {
        for (Staff staff: staffs) {
            delete(staff);
        }
    }

    private void delete(Staff staff) {
        this.manager.remove(staff);
    }

    private void delete(Route route) {
        this.manager.remove(route);
        delete(route.getTo());
        delete(route.getFrom());
    }

    private void delete(Airport airport) {
        this.manager.remove(airport);
    }

    private void delete(Set<PlainTicket> plainTickets) {
        for (PlainTicket plainTicket: plainTickets) {
            delete(plainTicket);
        }
    }

    private void delete(PlainTicket plainTicket) {
        this.manager.remove(plainTicket);
        delete(plainTicket.getPassenger());
    }

    private void delete(Passenger passenger) {
        this.manager.remove(passenger);
    }
}
