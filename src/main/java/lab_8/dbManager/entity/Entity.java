package lab_8.dbManager.entity;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface Entity {
    <T> T select(Class<T> tClass, Object key) throws SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException;
    void insert(Object entity) throws SQLException, IllegalAccessException, NoSuchMethodException;
    <T> T update(T entity) throws SQLException, IllegalAccessException, NoSuchMethodException;
    void refresh(Object entity) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException;
    void remove(Object entity) throws IllegalAccessException, SQLException;
}
