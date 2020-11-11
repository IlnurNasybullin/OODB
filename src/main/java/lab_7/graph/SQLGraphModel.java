package lab_7.graph;

import annotations.Entity;
import annotations.UserType;
import lab_7.sql.EntityClass;
import lab_7.sql.SQLType;
import lab_7.sql.UserTypeClass;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SQLGraphModel extends DirectedAcyclicGraph<SQLType, DefaultEdge> {

    private static class ClassFactory {
        private final static Map<Class<?>, SQLType> map;

        static {
            map = new HashMap<>();
        }

        public static SQLType put(Class<?> entityClass, SQLType type) {
            return map.put(entityClass, type);
        }

        public static SQLType getOrDefault(Class<?> entityClass, SQLType defaultType) {
            return map.getOrDefault(entityClass, defaultType);
        }

        public static SQLType get(Class<?> entityClass) {
            return getOrDefault(entityClass, null);
        }

        public static boolean containsKey(Class<?> entity) {
            return map.containsKey(entity);
        }
    }

    public SQLGraphModel(Collection<Class<?>> entities) {
        this(DefaultEdge.class);
        fillGraph(entities);
    }

    private void fillGraph(Collection<Class<?>> entities) {
        for (Class<?> entity: entities) {
            addClass(entity);
        }
    }

    private SQLType addClass(Class<?> tClass) {
        if (ClassFactory.containsKey(tClass)) {
            return ClassFactory.get(tClass);
        }
        SQLType type = null;

        if (isEntity(tClass)) {
            type = new EntityClass(tClass);
        }
        if (isUserType(tClass)) {
            type = new UserTypeClass(tClass);
        }

        if (type == null) {
            return null;
        }

        ClassFactory.put(tClass, type);
        addVertex(type);

        for (Class<?> fieldType : type.getFieldClasses()) {
            SQLType innerType = addClass(fieldType);
            if (innerType != null && type != innerType) {
                addEdge(type, innerType, new DefaultEdge());
            }
        }

        return type;
    }

    private boolean isUserType(Class<?> type) {
        return type.getDeclaredAnnotation(UserType.class) != null;
    }

    private boolean isEntity(Class<?> type) {
        return type.getDeclaredAnnotation(Entity.class) != null;
    }

    public SQLGraphModel(Class<? extends DefaultEdge> edgeClass) {
        super(edgeClass);
    }
}
