package lab_8.dbManager.query;

import java.util.LinkedHashMap;
import java.util.Map;

public interface Query {
    String getQuery();

    interface QueryBuilder {
        Query build();
    }

    abstract class AbstractQueryBuilder implements QueryBuilder {
        protected final String table;
        public AbstractQueryBuilder(String table) {
            this.table = table;
        }
    }

    abstract class ConditionBuilder extends AbstractQueryBuilder {

        protected Condition where;
        protected final LinkedHashMap<Condition, Condition.Union> conditions;

        public ConditionBuilder(String table) {
            super(table);
            this.conditions = new LinkedHashMap<>();
        }

        public QueryBuilder where(String column, Condition.Type type) {
            return where(column, null, type);
        }

        public QueryBuilder where(String column, String castType, Condition.Type type) {
            where = new Condition(column, castType, type);
            return this;
        }

        public QueryBuilder and(String column, Condition.Type type) {
            return and(column, null, type);
        }

        public QueryBuilder and(String column, String castType, Condition.Type type) {
            return conditions(column, castType, type, Condition.Union.AND);
        }

        public QueryBuilder or(String column, Condition.Type type) {
            return or(column, null, type);
        }

        public QueryBuilder or(String column, String castType, Condition.Type type) {
            return conditions(column, castType, type, Condition.Union.OR);
        }

        protected QueryBuilder conditions(String column, String cast, Condition.Type type, Condition.Union union) {
            this.conditions.put(new Condition(column, cast, type), union);
            return this;
        }

        protected void conditions(StringBuilder builder) {
            if (where == null && conditions.isEmpty()) {
                return;
            }

            builder.append("WHERE ").append(where.toString());

            for (Map.Entry<Condition, Condition.Union> entry : conditions.entrySet()) {
                builder.append(' ').append(entry.getValue()).append(' ').append(entry.getKey().toString());
            }
        }
    }
}
