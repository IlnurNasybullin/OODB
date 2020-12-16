package lab_8.dbManager;

import lab_8.dbManager.entity.DatabaseEntity;
import lab_8.dbManager.entity.EntityFactory;
import loggers.MyFormatter;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class SimpleEntityManager implements EntityManager {

    public final static Logger logger = Logger.getLogger(DatabaseEntity.class.getName());

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new MyFormatter());
        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
    }

    private Connection connection;

    SimpleEntityManager(Connection connection) {
        this.connection = connection;
    }

    private DatabaseEntity getEntity(Class<?> entityClass) {
        return EntityFactory.createOf(entityClass);
    }

    @Override
    public <T> T select(Class<T> entityClass, Object key) {
        if (key == null) {
            return null;
        }
        DatabaseEntity entity = EntityFactory.createOf(entityClass);
        entity.setConnection(connection);
        try {
            return entity.select(entityClass, key);
        } catch (SQLException | IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException throwables) {
            logger.severe(throwables.getMessage());
        }

        return null;
    }

    @Override
    public void insert(Object object) {
        if (object == null) {
            return;
        }

        DatabaseEntity entity = getEntity(object.getClass());
        entity.setConnection(connection);
        try {
            entity.insert(object);
        } catch (SQLException | IllegalAccessException | NoSuchMethodException throwables) {
            logger.severe(throwables.getMessage());
        }
    }

    @Override
    public <T> T update(T object) {
        if (object == null) {
            return null;
        }

        DatabaseEntity entity = getEntity(object.getClass());
        entity.setConnection(connection);

        try {
            return entity.update(object);
        } catch (SQLException | IllegalAccessException | NoSuchMethodException throwables) {
            logger.severe(throwables.getMessage());
        }

        return object;
    }

    @Override
    public void refresh(Object object) {
        if (object == null) {
            return;
        }

        DatabaseEntity entity = getEntity(object.getClass());
        entity.setConnection(connection);
        try {
            entity.refresh(object);
        } catch (IllegalAccessException | InvocationTargetException | SQLException | InstantiationException | NoSuchMethodException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void remove(Object object) {
        if (object == null) {
            return;
        }

        DatabaseEntity entity = getEntity(object.getClass());
        entity.setConnection(connection);
        try {
            entity.remove(object);
        } catch (IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
    }
}
