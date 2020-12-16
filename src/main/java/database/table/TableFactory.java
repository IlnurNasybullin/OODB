package database.table;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TableFactory {

    private static final Map<Class<?>, EntityTable> tables;
    static {
        tables = new HashMap<>();
    }

    public static EntityTable createOf(Class<?> entity) {
        if (tables.containsKey(entity)) {
            return tables.get(entity);
        } else {
            EntityTable table = new SimpleEntityTable(entity);
            tables.put(entity, table);
            return table;
        }
    }

    public static Set<Table> getTables() {
        List<EntityTable> entityTables = new ArrayList<>(TableFactory.tables.values());
        List<RelationTable> relationTables = new ArrayList<>();
        for (EntityTable table: entityTables) {
            relationTables.addAll(table.getRelationTables());
        }

        return Stream.concat(entityTables.stream(), relationTables.stream())
                .collect(Collectors.toUnmodifiableSet());
    }
}
