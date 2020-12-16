package lab_8.dbManager.query;

import database.table.EntityTable;
import database.table.RelationTable;
import lab_8.dbManager.queryManager.EntityTableQueryManager;
import lab_8.dbManager.queryManager.QueryManager;
import lab_8.dbManager.queryManager.RelationTableQueryManager;

import java.util.HashMap;
import java.util.Map;

public class QueryFactory {
    private static final Map<EntityTable, QueryManager> entityTableQueries;
    private static final Map<RelationTable, QueryManager> relationTableQueries;

    static {
        entityTableQueries = new HashMap<>();
        relationTableQueries = new HashMap<>();
    }

    public static QueryManager createOf(EntityTable table) {
        if (entityTableQueries.containsKey(table)) {
            return entityTableQueries.get(table);
        } else {
            QueryManager manager = new EntityTableQueryManager(table);
            entityTableQueries.put(table, manager);
            return manager;
        }
    }

    public static QueryManager createOf(RelationTable table) {
        if (relationTableQueries.containsKey(table)) {
            return relationTableQueries.get(table);
        } else {
            QueryManager manager = new RelationTableQueryManager(table);
            relationTableQueries.put(table, manager);
            return manager;
        }
    }
}
