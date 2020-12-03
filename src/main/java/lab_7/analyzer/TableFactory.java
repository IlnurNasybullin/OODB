package lab_7.analyzer;

import lab_7.database.Table;

import java.util.*;

public class TableFactory {

    private static final Map<Class<?>, Table> tables;

    static {
        tables = new HashMap<>();
    }

    public static Set<Table> of(Collection<Class<?>> classes) {
        for (Class<?> tClass : classes) {
            try {
                tables.putIfAbsent(tClass, new Table(tClass));
            } catch (IllegalArgumentException ignored) {}
        }

        Set<Table> tables = new HashSet<>();
        for (Table table: TableFactory.tables.values()) {
            tables.add(table);
            tables.addAll(table.getExtendedTables());
        }

        return tables;
    }

    public static void put(Class<?> target, Table table) {
        tables.putIfAbsent(target, table);
    }
}
