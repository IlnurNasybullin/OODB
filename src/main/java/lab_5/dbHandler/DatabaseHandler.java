package lab_5.dbHandler;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static handlers.Config.*;

public class DatabaseHandler implements Closeable {

    private final Connection connection;

    public DatabaseHandler() throws SQLException {
        connection = DriverManager.getConnection(url, userName, password);
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            IOException exception = new IOException(e.getMessage());
            exception.addSuppressed(e);
            exception.setStackTrace(e.getStackTrace());
            throw exception;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
