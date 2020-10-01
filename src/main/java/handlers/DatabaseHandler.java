package handlers;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.Map;
import java.util.Set;

public class DatabaseHandler implements Closeable {

    private Connection connection;

    private DatabaseHandler(String url, String userName, String password) throws SQLException, ClassNotFoundException {
        connection = DriverManager.getConnection(url, userName, password);
    }

    public static DatabaseHandler of(String url, String userName, String password) throws SQLException, ClassNotFoundException {
        return new DatabaseHandler(url, userName, password);
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException throwables) {
            IOException exception = new IOException(throwables.getMessage());
            exception.setStackTrace(throwables.getStackTrace());
            exception.addSuppressed(throwables);
            throw exception;
        }
    }

    public int insertData(String tableName, Map<String, String> values) throws SQLException {
        StringBuffer columnNames = new StringBuffer();
        StringBuffer valuesString = new StringBuffer();
        for (Map.Entry<String, String> entry: values.entrySet()) {
            columnNames.append(String.format("%s, ", entry.getKey()));
            valuesString.append(String.format("'%s', ", entry.getValue()));
        }

        if (!values.isEmpty()) {
            int columnLength = columnNames.length();
            int valuesLength = valuesString.length();
            columnNames.delete(columnLength - 2, columnLength);
            valuesString.delete(valuesLength - 2, valuesLength);
        }

        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columnNames.toString(), valuesString.toString());
        return executeUpdate(query);
    }

    private int executeUpdate(String query) throws SQLException {
        System.out.println(query);
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeUpdate();
    }

    public ResultSet select(String tableName, Set<String> columns, boolean nullable) throws SQLException {
        String columnNames = columns.toString().replace("[", "").replace("]", "");
        StringBuffer nullableBuffer = getNotNullableColumnNames(columns, nullable);

        String query = String.format("SELECT %s FROM %s", columnNames, tableName) + ((!nullable) ? String.format(" WHERE %s", nullableBuffer) : "");
        return executeResult(query);
    }

    public ResultSet executeResult(String query) throws SQLException {
        System.out.println(query);
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }

    private StringBuffer getNotNullableColumnNames(Set<String> columns, boolean nullable) {
        StringBuffer nullableBuffer = new StringBuffer();
        if (!nullable) {
            for (String column: columns) {
                nullableBuffer.append(String.format("%s IS NOT NULL, ", column));
            }
            int length = nullableBuffer.length();
            nullableBuffer.delete(length - 2, length);
        }
        return nullableBuffer;
    }
}
