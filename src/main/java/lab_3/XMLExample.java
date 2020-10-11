package lab_3;

import airlines.db.Flight;
import com.thoughtworks.xstream.XStream;
import lab_2.DataContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;

public class XMLExample {
    public static void main(String[] args) throws IOException {
        List<Flight> flightList = DataContainer.getFlights(10);
        File file = new File("./src/main/resources/flights.xml");

        XStream xStream = new XStream();
        xStream.alias("flights", Flight.class);
        String inputXML = xStream.toXML(flightList);

        Files.write(file.toPath(), inputXML.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        String outputXML = Files.readString(file.toPath());
        List<Flight> list = (List<Flight>) xStream.fromXML(outputXML);

        list.sort(Comparator.comparing(Flight::getStart));
        list.forEach(System.out::println);
    }
}
