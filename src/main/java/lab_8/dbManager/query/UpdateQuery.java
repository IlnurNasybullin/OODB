package lab_8.dbManager.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UpdateQuery extends AbstractQuery {

    private UpdateQuery(String query) {
        super(query);
    }

    public static class Builder extends ConditionBuilder {

        private static final String DEFAULT = "DEFAULT";

        private final List<Assignation> setColumns;

        public Builder(String table) {
            super(table);
            this.setColumns = new ArrayList<>();
        }

        public Builder set(String column) {
            return set(column, null);
        }

        public Builder set(String column, String castType) {
            this.setColumns.add(new Assignation(column, castType));
            return this;
        }

        public Builder set(String column, String castType, String expression) {
            this.setColumns.add(new Assignation(column, castType, expression));
            return this;
        }

        public Builder setDefault(String column) {
            return setDefault(column, null);
        }

        public Builder setDefault(String column, String castType) {
            return set(column, castType, DEFAULT);
        }

        @Override
        public Query build() {
            if (setColumns.isEmpty()) {
                throw new IllegalArgumentException("no set columns!");
            }

            StringBuilder builder = new StringBuilder();
            builder.append("UPDATE ").append('"').append(table).append("\" ");
            set(builder);
            builder.append(' ');
            conditions(builder);

            return new UpdateQuery(builder.toString());
        }

        private void set(StringBuilder builder) {
            builder.append("SET ");
            Iterator<Assignation> iterator = setColumns.listIterator();
            for (;;) {
                Assignation assignation = iterator.next();
                builder.append(assignation.toString());
                if (!iterator.hasNext()) {
                    break;
                }

                builder.append(", ");
            }
        }
    }
}
