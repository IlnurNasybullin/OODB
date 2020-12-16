package lab_8.dbManager;

public interface EntityManager {

    <T> T select(Class<T> entityClass, Object key);
    void insert(Object entity);
    <T> T update(T entity);
    void refresh(Object entity);
    void remove(Object entity);

}
