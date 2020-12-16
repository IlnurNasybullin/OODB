package analyse;

import database.column.Column;
import database.table.Table;
import database.table.TableFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseAnalyzer {

    public static final String TABLE_NAME = "table_name";
    public static final String COLUMN_ATTRIBUTE = "attname";

    public static final String tableQuery = "SELECT %s FROM information_schema.tables WHERE table_type = 'BASE TABLE' " +
            "AND table_schema NOT IN('pg_catalog', 'information_schema')";
    public static final String columnQuery = "SELECT a.%s FROM pg_catalog.pg_attribute a WHERE a.attrelid = " +
            "(SELECT c.oid FROM pg_catalog.pg_class c LEFT JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace " +
            "WHERE pg_catalog.pg_table_is_visible(c.oid) AND c.relname = ?) AND a.attnum > 0 AND NOT a.attisdropped";

    private final Connection connection;
    private final Set<Table> tables;
    private Map<String, Set<String>> tablesFromDatabase;

    public DatabaseAnalyzer(Collection<Class<?>> classes, Connection connection) {
        this.connection = connection;
        this.tables = createTables(classes);
    }

    public boolean containsEntities() throws SQLException {
        tablesFromDatabase = getDatabaseTableMap();
        Map<String, Set<String>> tablesFromEntities = getTablesFromEntities();

        return lessOrEqualDatabaseMaps(tablesFromEntities, tablesFromDatabase);
    }

    private boolean lessOrEqualDatabaseMaps(Map<String, Set<String>> entities, Map<String, Set<String>> database) {
        if (entities.size() > database.size() || !database.keySet().containsAll(entities.keySet())) {
            return false;
        }

        boolean lessOrEqual = true;

        for (Map.Entry<String, Set<String>> entry: entities.entrySet()) {
            Set<String> columns = entry.getValue();
            Set<String> databaseColumns = database.get(entry.getKey());

            lessOrEqual = columns.size() <= databaseColumns.size() && databaseColumns.containsAll(columns);
            if (!lessOrEqual) {
                break;
            }
        }

        return lessOrEqual;
    }

    public void analyse() {
        tablesFromDatabase = getTablesFromEntities();
        for (Table table : tables) {
            System.out.printf("Сущность %s%n", table);
            String tableName = table.getName();
            if (tablesFromDatabase.containsKey(tableName)) {
                System.out.println("------------------");
                Set<String> columns = tablesFromDatabase.get(tableName);
                for (Column column : table.getAllColumns()) {
                    System.out.printf("%s - %s в БД%n", column, columns.contains(column.getName()) ? "присутствует" : "отсутсвует");
                }
            }
            System.out.println();
        }
    }

    private Map<String, Set<String>> getTablesFromEntities() {
        return tables.stream().collect(Collectors.toMap(Table::getName, this::getColumns));
    }

    private Set<String> getColumns(Table table) {
        return table.getAllColumns().stream().map(Column::getName).collect(Collectors.toSet());
    }

    private Map<String, Set<String>> getDatabaseTableMap() throws SQLException {
        Map<String, Set<String>> tablesFromDatabase = new HashMap<>();

        String query = String.format(tableQuery, TABLE_NAME);
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet resultSet = st.executeQuery();
        while (resultSet.next()) {
            String tableName = resultSet.getString(TABLE_NAME);
            tablesFromDatabase.put(tableName, getColumns(tableName));
        }

        st.close();
        return tablesFromDatabase;
    }

    private Set<String> getColumns(String tableName) throws SQLException {
        Set<String> columns = new HashSet<>();
        String query = String.format(columnQuery, COLUMN_ATTRIBUTE);
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, tableName);
        ResultSet resultSet = st.executeQuery();

        while (resultSet.next()) {
            columns.add(resultSet.getString(COLUMN_ATTRIBUTE));
        }

        st.close();
        return columns;
    }

    private Set<Table> createTables(Collection<Class<?>> classes) {
        for (Class<?> cl: classes) {
            try {
                TableFactory.createOf(cl);
            } catch (IllegalArgumentException ignored) {}
        }

        return TableFactory.getTables();
    }
}
