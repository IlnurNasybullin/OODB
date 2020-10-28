package lab_5.dbHandler;

import airlines.db.Route;
import lab_5.db.RoutePGObject;

import java.sql.*;
import java.util.*;

import static lab_5.dbHandler.TableData.*;

public class RouteDAO {
    public static final String insertQuery = "INSERT INTO " + tableName + " (" + columnName + ") VALUES ";
    public static final String selectQuery = "SELECT * FROM " + tableName + ";";
    public static final String selectCountQuery = "SELECT count(*) FROM " + tableName;

    public static final String updateAltitudeByCityQuery =
            "UPDATE %s SET %s.%s.%s.%s.%s = ? WHERE (%s).%s.%s.%s = ? RETURNING %s;";

    public static final QueryBuilder insertQueryBuilder;
    public static final QueryBuilder selectQueryBuilder;
    public static final QueryBuilder updateAltitudeByCityQueryBuilder;

    static {
        insertQueryBuilder = new QueryBuilder()
                .setType(QueryType.WRITE)
                .addTable(tableName)
                .addColumn(String.format("%s.%s", tableName, columnName));

        selectQueryBuilder = new QueryBuilder()
                .setType(QueryType.READ)
                .addColumn(String.format("%s.%s", tableName, columnName));

        updateAltitudeByCityQueryBuilder = new QueryBuilder()
                .setType(QueryType.WRITE)
                .addColumn(String.format("%s.%s", tableName, columnName));
    }

    public int insertRoutes(Connection connection, Collection<Route> routes) throws SQLException {
        Query query = insertQueryBuilder.createQuery();
        DatabaseCasheManager.update(query);
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
        Query query = selectQueryBuilder.setQuery(selectQuery).createQuery();
        List<Route> routes = (List<Route>) DatabaseCasheManager.getOrDefault(query, null);
        if (routes != null) {
            return routes;
        }

        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(selectCountQuery);
        set.next();

        int size = set.getInt("count");

        routes = new ArrayList<>(size);
        set.close();
        set = statement.executeQuery(selectQuery);

        RoutePGObject pgObject = new RoutePGObject(ROUTE);

        while (set.next()) {
            String value = set.getString("route");
            pgObject.setValue(value);
            routes.add(pgObject.getObject());
        }

        DatabaseCasheManager.put(query, routes);
        return routes;
    }

    public int updateAltitudeByCity(Connection connection, double altitude, String city) throws SQLException {
        DatabaseCasheManager.update(updateAltitudeByCityQueryBuilder.createQuery());
        Set<Integer> id = getUpdateAltitudeByCityID
                (connection, altitude, city, tableName, columnName, from, geographicPosition, TableData.altitude, length, columnName, from,
                        geographicPosition, TableData.city, ID);
        id.addAll(getUpdateAltitudeByCityID
                (connection, altitude, city, tableName, columnName, to, geographicPosition, TableData.altitude, length, columnName, to,
                        geographicPosition, TableData.city, ID));

        return id.size();
    }

    private Set<Integer> getUpdateAltitudeByCityID(Connection connection, double altitude, String city, Object ... args) throws SQLException {
        String query = String.format(updateAltitudeByCityQuery, args);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, altitude);
        preparedStatement.setString(2, city);
        ResultSet resultSet = preparedStatement.executeQuery();

        Set<Integer> set = new HashSet<>();
        while (resultSet.next()) {
            set.add(resultSet.getInt(1));
        }

        return set;
    }
}
