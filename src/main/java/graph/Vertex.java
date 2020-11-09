package graph;

import annotations.Column;

import java.lang.reflect.Field;
import java.util.*;

public class Vertex {

    private Class<?> vertexClass;
    private transient final Set<VertexAttribute> vertexAttributes;

    private Vertex() {
        this.vertexAttributes = new HashSet<>();
    }

    public Vertex(Class<?> vertexClass) {
        this();
        this.vertexClass = vertexClass;
        fillVertexAttributesSet();
    }

    private void fillVertexAttributesSet() {
        for (Field field: vertexClass.getDeclaredFields()) {
            if (containsAnnotation(field)) {
                vertexAttributes.add(new VertexAttribute(field.getType().getName(), field.getName()));
            }
        }
    }

    private boolean containsAnnotation(Field field) {
        return Objects.nonNull(field.getDeclaredAnnotation(Column.class));
    }

    public Class<?> getVertexClass() {
        return vertexClass;
    }

    public Set<VertexAttribute> getVertexAttributes() {
        return Collections.unmodifiableSet(vertexAttributes);
    }

    public void setVertexClass(Class<?> vertexClass) {
        this.vertexClass = vertexClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(vertexClass, vertex.vertexClass);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vertexClass);
    }

    @Override
    public String toString() {
        return String.format("{name: %s, attributes: %s}", vertexClass.getSimpleName(), vertexAttributes);
    }
}
