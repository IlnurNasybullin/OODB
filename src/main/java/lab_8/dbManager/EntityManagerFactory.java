package lab_8.dbManager;

import analyse.DatabaseAnalyzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

public class EntityManagerFactory implements AutoCloseable {

    private final Connection connection;

    public EntityManagerFactory(DatabaseProperties properties, Collection<Class<?>> classes) throws SQLException {
        this.connection = DriverManager.getConnection(properties.getUrl(), properties.getUser(), properties.getPassword());
        checkDatabase(classes);
    }

    private void checkDatabase(Collection<Class<?>> classes) throws SQLException {
        if (!new DatabaseAnalyzer(classes, connection).containsEntities()) {
            throw new RelationMappingException("Entity classes aren't exist in database!");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public EntityManager getEntityManager() {
        return new SimpleEntityManager(connection);
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
