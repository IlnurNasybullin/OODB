package lab_8;

import airlines.entities.Flight;
import lab_8.dbManager.type.TypeComponent;
import lab_8.dbManager.type.TypeFactory;
import lab_8.generator.FlightRandomGenerator;

import java.sql.SQLException;
import java.time.ZoneId;
import java.util.List;

public class Test {

    public static final String URL = "jdbc:postgresql://127.0.0.1:5432/Airlines";
    public static final String USER = "postgres";
    public static final String PASSWORD = "password";

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, SQLException {
        registerZoneID();
        List<Flight> flights = new FlightRandomGenerator().next(10);

//        InsertTree insertTest = new InsertTree(manager);
//        insertTest.insert(flights);

//        SelectTree selectTest = new SelectTree(manager);
//        selectTest.select(flights);

//        RefreshTree refreshTest = new RefreshTree(manager);
//        refreshTest.refresh(flights);

//        DeleteTree deleteTest = new DeleteTree(manager);
//        deleteTest.delete(flights);

//        UpdateTree updateTest = new UpdateTree(manager);
//        updateTest.update(flights);
    }

    private static void registerZoneID() {
        TypeComponent<ZoneId> typeComponent = new TypeComponent<ZoneId>() {
            @Override
            public String toString(Object object) {
                if (object == null) {
                    return "NULL";
                }
                return object.toString();
            }

            @Override
            public ZoneId toObject(String string) {
                try {
                    ZoneId of = ZoneId.of(string);
                    return of;
                } catch (RuntimeException ignored) {}
                return null;
            }
        };
        TypeFactory.register(ZoneId.class, typeComponent);
    }


}
