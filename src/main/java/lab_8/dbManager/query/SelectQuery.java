package lab_8.dbManager.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SelectQuery extends AbstractQuery {

    private SelectQuery(String query) {
        super(query);
    }

    public static class Builder extends ConditionBuilder {

        private static final char ALL = '*';
        private final List<String> columns;

        public Builder(String table) {
            super(table);
            this.columns = new ArrayList<>();
        }

        public Builder select(String column) {
            columns.add(column);
            return this;
        }

        public SelectQuery build() {
            if (where == null && !conditions.isEmpty()) {
                throw new IllegalStateException("where is null, but has conditions!");
            }

            StringBuilder builder = new StringBuilder();
            select(builder);
            builder.append(' ');
            conditions(builder);
            builder.append(';');
            return new SelectQuery(builder.toString());
        }

        private void select(StringBuilder builder) {
            builder.append("SELECT ");
            if (columns.isEmpty()) {
                builder.append(ALL).append(' ');
            } else {
                Iterator<String> iterator = columns.listIterator();
                for (;;) {
                    String column = iterator.next();
                    builder.append('"').append(column).append('"');
                    if (!iterator.hasNext()) {
                        builder.append(' ');
                        break;
                    }

                    builder.append(", ");
                }
            }

            builder.append("FROM \"").append(table).append('\"');
        }
    }
}
