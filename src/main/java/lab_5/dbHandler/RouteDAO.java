package lab_5.dbHandler;

import airlines.db.Route;
import lab_5.db.RoutePGObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RouteDAO {
    public static final String columnName = "route";
    public static final String tableName = columnName + "s";
    public static final String insertQuery = "INSERT INTO " + tableName + " (" + columnName + ") VALUES ";
    public static final String selectQuery = "SELECT * FROM " + tableName + ";";
    public static final String selectCountQuery = "SELECT count(*) FROM " + tableName;
    public static final String ROUTE = "route";

    public int insertRoutes(Connection connection, Collection<Route> routes) throws SQLException {
        String insertQuery = prepareInsertCollectionQuery(routes);
        Statement statement = connection.createStatement();

        return statement.executeUpdate(insertQuery);
    }

    private String prepareInsertCollectionQuery(Collection<Route> routes) {
        StringBuffer buffer = new StringBuffer(insertQuery);

        RoutePGObject pGobject = new RoutePGObject(ROUTE);
        for (Route route: routes) {
            pGobject.setObject(route);
            buffer.append(String.format("(%s), ", pGobject.getValue()));
        }

        int length = buffer.length();
        int pos = length - 2;

        buffer.delete(pos, length);
        buffer.append(';');
        return buffer.toString();
    }

    public List<Route> selectRoutes(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(selectCountQuery);
        set.next();

        int size = set.getInt("count");

        List<Route> routes = new ArrayList<>(size);
        set.close();
        set = statement.executeQuery(selectQuery);

        RoutePGObject pgObject = new RoutePGObject(ROUTE);

        while (set.next()) {
            String value = set.getString("route");
            pgObject.setValue(value);
            routes.add(pgObject.getObject());
        }

        return routes;
    }
}
