package lab_8.generator;

import airlines.entities.Airport;
import airlines.userTypes.AirportICAO;
import airlines.userTypes.IATA;
import airlines.userTypes.geographic.GeographicPosition;

public class AirportRandomGenerator implements RandomInfinityGenerator<Airport> {

    private final RandomInfinityGenerator<AirportICAO> icaoGenerator;

    private final RandomInfinityGenerator<Long> idGenerator;
    private final RandomInfinityGenerator<IATA> iataGenerator;
    private final RandomInfinityGenerator<GeographicPosition> geographicPositionGenerator;

    public AirportRandomGenerator() {
        this.icaoGenerator = new IcaoRandomGenerator();
        this.idGenerator = new IdRandomGenerator();
        this.iataGenerator = new IataRandomGenerator();
        this.geographicPositionGenerator = new GeographicPositionRandomGenerator();
    }

    @Override
    public Airport next() {
        Airport airport = new Airport(icaoGenerator.next());

        airport.setID(idGenerator.next());
        airport.setIataID(iataGenerator.next());
        airport.setPosition(geographicPositionGenerator.next());

        return airport;
    }
}
