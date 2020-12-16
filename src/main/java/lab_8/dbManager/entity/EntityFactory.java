package lab_8.dbManager.entity;

import database.table.TableFactory;

import java.util.HashMap;
import java.util.Map;

public class EntityFactory {

    private static final Map<Class<?>, DatabaseEntity> entities;

    static {
        entities = new HashMap<>();
    }

    public static DatabaseEntity createOf(Class<?> entityClass) {
        if (entities.containsKey(entityClass)) {
            return entities.get(entityClass);
        } else {
            DatabaseEntity entity = new DatabaseEntity(TableFactory.createOf(entityClass));
            entities.put(entityClass, entity);
            return entity;
        }
    }
}
