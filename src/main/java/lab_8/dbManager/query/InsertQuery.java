package lab_8.dbManager.query;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class InsertQuery extends AbstractQuery {

    private InsertQuery(String query) {
        super(query);
    }

    public static class Builder extends AbstractQueryBuilder {

        private final Map<String, String> columns;
        private Integer valuesCount;

        public Builder(String table) {
            super(table);
            this.columns = new LinkedHashMap<>();
        }

        public Builder column(String columnName) {
            return column(columnName, null);
        }

        public Builder column(String columnName, String userType) {
            this.columns.put(columnName, userType);
            return this;
        }

        public Builder valuesCount(int valuesCount) {
            this.valuesCount = valuesCount;
            return this;
        }

        public InsertQuery build() {
            StringBuilder builder = new StringBuilder("INSERT INTO \"").append(table).append('"');

            columns(builder);
            builder.append(" VALUES ");
            values(builder);

            builder.append(";");
            return new InsertQuery(builder.toString());
        }

        private void values(StringBuilder builder) {
            builder.append('(');
            Iterator<String> iterator = columns.values().iterator();
            if (!iterator.hasNext()) {
                if (valuesCount == null) {
                    throw new IllegalStateException(String.format("Unknowable values count!"));
                }
                iterator = new ConstantIterator<>(null, valuesCount);
            }
            for (;;) {
                builder.append('?');
                String cast = iterator.next();
                if (cast != null) {
                    builder.append("::").append(cast);
                }

                if (!iterator.hasNext()) {
                    break;
                }

                builder.append(", ");
            }
            builder.append(')');
        }

        private void columns(StringBuilder builder) {
            builder.append('(');
            Iterator<String> iterator = columns.keySet().iterator();
            for (;;) {
                String column = iterator.next();
                builder.append('\"').append(column).append('"');
                if (!iterator.hasNext()) {
                    break;
                }
                builder.append(", ");
            }
            builder.append(')');
        }

        private class ConstantIterator<T> implements Iterator<T> {

            private final T constant;
            private final int count;
            private int current;

            public ConstantIterator(T constant, int count) {
                this.constant = constant;
                this.count = count;
                this.current = 0;
            }

            @Override
            public boolean hasNext() {
                return current < count;
            }

            @Override
            public T next() {
                current++;
                return constant;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ConstantIterator<?> that = (ConstantIterator<?>) o;
                return count == that.count && current == that.current && Objects.equals(constant, that.constant);
            }

            @Override
            public int hashCode() {
                return Objects.hash(constant, count, current);
            }
        }
    }
}
