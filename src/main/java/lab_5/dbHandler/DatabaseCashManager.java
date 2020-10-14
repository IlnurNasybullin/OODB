package lab_5.dbHandler;

import java.util.HashMap;
import java.util.Map;

public class DatabaseCashManager {

    private final static Map<Query, Object> queryCash;

    static {
        queryCash = new HashMap<>();
    }

    public static Object put(Query query, Object result) {
        update(query);
        return queryCash.put(query, result);
    }

    public static void update(Query query) {
        queryCash.keySet().removeIf(q -> q.isExcludingQuery(query));
    }

    public static void clear() {
        queryCash.clear();
    }

    public static Object getOrDefault(Query query, Object def) {
        return queryCash.getOrDefault(query, def);
    }

}
