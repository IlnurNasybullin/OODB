package lab_5.dbHandler;

import java.util.HashMap;
import java.util.Map;

public class DatabaseCasheManager {

    private final static Map<Query, Object> queryCashe;

    static {
        queryCashe = new HashMap<>();
    }

    public static Object put(Query query, Object result) {
        update(query);
        return queryCashe.put(query, result);
    }

    public static void update(Query query) {
        queryCashe.keySet().removeIf(q -> q.isExcludingQuery(query));
    }

    public static void clear() {
        queryCashe.clear();
    }

    public static Object getOrDefault(Query query, Object def) {
        return queryCashe.getOrDefault(query, def);
    }

}
