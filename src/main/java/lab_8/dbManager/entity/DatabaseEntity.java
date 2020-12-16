package lab_8.dbManager.entity;

import annotations.ReflectionUtils;
import annotations.Relation;
import annotations.UserType;
import database.column.Column;
import database.column.IdColumn;
import database.column.RelationColumn;
import database.table.EntityTable;
import database.table.RelationTable;
import database.table.TableFactory;
import lab_8.dbManager.query.Query;
import lab_8.dbManager.query.QueryFactory;
import lab_8.dbManager.queryManager.QueryManager;
import lab_8.dbManager.type.TypeComponent;
import lab_8.dbManager.type.TypeFactory;
import loggers.MyFormatter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import static lab_8.dbManager.entity.SQLDateConverter.*;

public class DatabaseEntity implements Entity {

    public final static Logger logger = Logger.getLogger(DatabaseEntity.class.getName());

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new MyFormatter());
        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
    }

    private final EntityTable table;
    private final QueryManager manager;

    private Connection connection;

    public DatabaseEntity(EntityTable table) {
        this.table = table;
        this.manager = QueryFactory.createOf(table);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    @Override
    public <T> T select(Class<T> tClass, Object key) throws SQLException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException, InstantiationException {
        Query query = manager.selectById();
        PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setObject(1, key);

        logger.info(preparedStatement.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.close();
        T t = createObject(tClass);
        fillObject(t, resultSet);

        return t;
    }

    private <T> void fillObject(T object, ResultSet resultSet) throws SQLException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        if (!resultSet.next()) {
            return;
        }

        int i = 1;
        for (Column column: table.getAllColumns()) {
            Field field = column.getField();
            field.setAccessible(true);

            Object fieldObject = getFieldObject(field.getType(), resultSet, i);
            if (column instanceof RelationColumn) {
                fieldObject = getRelationObject(field.getType(), fieldObject);
            }

            field.set(object, fieldObject);
            field.setAccessible(false);
            i++;
        }

        selectExtendedTables(object);
    }

    private <T> void selectExtendedTables(T object) throws SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Object id = getFieldValue(this.table.getIdColumn().getField(), object);

        for (RelationTable relationTable: table.getRelationTables() ) {
            IdColumn firstColumn = relationTable.firstColumn();
            IdColumn secondColumn = relationTable.secondColumn();

            Query query = QueryFactory.createOf(relationTable).selectById();

            PreparedStatement statement = connection.prepareStatement(query.toString());
            statement.setObject(1, id);

            logger.info(statement.toString());
            ResultSet resultSet = statement.executeQuery();
            statement.close();

            List<Object> objects = new ArrayList<>();

            Field relationField = relationTable.getRelationField();
            Class<?> type = getTargetClass(relationField);
            Constructor<?> declaredConstructor = type.getDeclaredConstructor();

            Field idField = secondColumn.getField();
            idField.setAccessible(true);
            declaredConstructor.setAccessible(true);
            while (resultSet.next()) {
                Object fieldObject = declaredConstructor.newInstance();
                Object fieldId = resultSet.getObject(1);
                idField.set(fieldObject, fieldId);
                objects.add(fieldObject);
            }
            declaredConstructor.setAccessible(false);
            idField.setAccessible(false);

            setCollection(object, relationField, objects);
        }
    }

    private Class<?> getTargetClass(Field relationField) {
        Relation relation = ReflectionUtils.getAnnotation(relationField, Relation.class);
        return relation.target();
    }

    private <T> void setCollection(T object, Field field, List<Object> objects) throws IllegalAccessException {
        field.setAccessible(true);
        Class<?> type = field.getType();
        boolean isSupported = false;
        if (List.class.isAssignableFrom(type)) {
            field.set(object, objects);
            isSupported = true;
        }

        if (Set.class.isAssignableFrom(type)) {
            field.set(object, new HashSet<>(objects));
            isSupported = true;
        }

        field.setAccessible(false);
        if (!isSupported) {
            throw new UnsupportedOperationException(String.format("This collection %s isn't supported for reflection!", type));
        }
    }

    private <T> Object getFieldValue(Field field, T object) throws IllegalAccessException {
        field.setAccessible(true);
        Object id = field.get(object);
        field.setAccessible(false);
        return id;
    }

    private Object getRelationObject(Class<?> type, Object fieldObject) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        EntityTable table = TableFactory.createOf(type);
        Field field = table.getIdColumn().getField();

        Constructor<?> constructor = type.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object obj = constructor.newInstance();

        constructor.setAccessible(false);

        setObject(obj, field, fieldObject);

        return obj;
    }

    private Object getFieldObject(Class<?> type, ResultSet resultSet, int i) throws SQLException {
        boolean isDate = isDate(type);
        boolean isDateTime = isDateTime(type);
        boolean isTime = isTime(type);
        if (isDate || isDateTime || isTime) {
            if (isDate) {
                return getDate(type, resultSet.getDate(i));
            }

            if (isDateTime) {
                return getDateTime(type, resultSet.getTimestamp(i));
            }

            return getTime(type, resultSet.getTime(i));
        }

        return fromDatabaseObject(type, resultSet.getObject(i));
    }

    private Object fromDatabaseObject(Class<?> type, Object object) {
        TypeComponent<?> typeComponent = TypeFactory.getComponent(type);
        if (typeComponent != null) {
            return typeComponent.toObject(object.toString());
        }

        if (ReflectionUtils.getAnnotation(type, UserType.class) != null) {
            try {
                return TypeFactory.createOf(type).toObject(object.toString());
            } catch (NoSuchMethodException e) {
                logger.severe(e.getMessage());
            }
        }

        return object;
    }

    private <T> T createObject(Class<T> tClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<T> constructor = tClass.getDeclaredConstructor();
        constructor.setAccessible(true);

        T object = constructor.newInstance();
        constructor.setAccessible(false);

        return object;
    }

    private Object toDatabaseObject(Column column, Object object) throws IllegalAccessException, NoSuchMethodException {
        if (object == null) {
            return null;
        }

        Class<?> tClass = object.getClass();
        TypeComponent<?> component = TypeFactory.getComponent(tClass);
        if (component != null) {
            return component.toString(object);
        }

        if (ReflectionUtils.getAnnotation(tClass, UserType.class) != null) {
            return TypeFactory.createOf(tClass).toString(object);
        }

        if (column instanceof RelationColumn) {
            return getFieldValue(TableFactory.createOf(object.getClass()).getIdColumn().getField(), object);
        }

        return object;
    }

    @Override
    public void insert(Object entity) throws SQLException, IllegalAccessException, NoSuchMethodException {
        Query query = manager.insert();
        PreparedStatement preparedStatement = connection.prepareStatement(query.toString());

        int i = 1;
        for (Column column: table.getAllColumns()) {
            Field field = column.getField();
            field.setAccessible(true);
            preparedStatement.setObject(i, toDatabaseObject(column, field.get(entity)));
            field.setAccessible(false);
            i++;
        }

        logger.info(preparedStatement.toString());
        preparedStatement.execute();
        preparedStatement.close();

        insertExtendedTables(entity);
    }

    private synchronized void insertExtendedTables(Object entity) throws SQLException, IllegalAccessException {
        Object id = getFieldValue(this.table.getIdColumn().getField(), entity);

        for (RelationTable relationTable: table.getRelationTables()) {
            Field relationField = relationTable.getRelationField();
            Iterator<?> iterator =  getIterator(entity, relationField);
            Query query = QueryFactory.createOf(relationTable).insert();

            while (iterator.hasNext()) {
                Object objectField = iterator.next();
                PreparedStatement statement = connection.prepareStatement(query.toString());
                statement.setObject(1, id);
                statement.setObject(2, getFieldValue(relationTable.secondColumn().getField(), objectField));

                logger.info(statement.toString());
                statement.execute();
                statement.close();
            }
        }
    }

    private Iterator<?> getIterator(Object entity, Field relationField) throws IllegalAccessException {
        relationField.setAccessible(true);
        Iterator<?> iterator;
        if (Iterable.class.isAssignableFrom(relationField.getType())) {
            iterator = ((Iterable<?>) relationField.get(entity)).iterator();
        } else {
            throw new UnsupportedOperationException(String.format("This field %s isn't iteration type!", relationField));
        }

        relationField.setAccessible(false);
        return iterator;
    }

    @Override
    public <T> T update(T entity) throws SQLException, IllegalAccessException, NoSuchMethodException {
        Query query = manager.update();
        Object id = getFieldValue(this.table.getIdColumn().getField(), entity);
        PreparedStatement statement = connection.prepareStatement(query.toString());

        int i = 1;
        for (Column column: table.getAllColumns()) {
            if (column instanceof IdColumn) {
                continue;
            }

            Field field = column.getField();
            field.setAccessible(true);
            statement.setObject(i, toDatabaseObject(column, field.get(entity)));
            field.setAccessible(false);
            i++;
        }
        statement.setObject(i, id);
        logger.info(statement.toString());
        statement.executeUpdate();
        statement.close();

        updateExtendedTables(entity);
        return entity;
    }

    private <T> void updateExtendedTables(T entity) throws IllegalAccessException, SQLException {
        deleteExtendedTables(entity);
        insertExtendedTables(entity);
    }

    @Override
    public void refresh(Object entity) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException {
        Object id = getFieldValue(table.getIdColumn().getField(), entity);

        Object select = select(entity.getClass(), id);
        for (Column column: table.getAllColumns()) {
            Field field = column.getField();
            setObject(entity, field, field.get(select));
        }

        refreshExtendedTable(entity, select);
    }

    private void setObject(Object object, Field field, Object fieldObject) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(object, fieldObject);
        field.setAccessible(false);
    }

    private void refreshExtendedTable(Object entity, Object select) throws IllegalAccessException {
        for (RelationTable relationTable: this.table.getRelationTables()) {
            Field field = relationTable.secondColumn().getField();
            setObject(entity, field, field.get(select));
        }
    }

    @Override
    public void remove(Object entity) throws IllegalAccessException, SQLException {
        deleteExtendedTables(entity);
        Object id = getFieldValue(this.table.getIdColumn().getField(), entity);
        Query query = manager.delete();

        PreparedStatement statement = connection.prepareStatement(query.toString());
        statement.setObject(1, id);
        logger.info(statement.toString());
        statement.execute();
        statement.close();
    }

    private void deleteExtendedTables(Object entity) throws IllegalAccessException, SQLException {
        Object id = getFieldValue(this.table.getIdColumn().getField(), entity);
        for (RelationTable relationTable: this.table.getRelationTables()) {
            Query query = QueryFactory.createOf(relationTable).delete();
            PreparedStatement statement = connection.prepareStatement(query.toString());
            statement.setObject(1, id);
            logger.info(statement.toString());
            statement.execute();
            statement.close();
        }
    }
}
