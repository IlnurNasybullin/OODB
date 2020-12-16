package lab_8.dbManager.query;

public class DeleteQuery extends AbstractQuery {

    private DeleteQuery(String query) {
        super(query);
    }

    public static class Builder extends ConditionBuilder {

        public Builder(String table) {
            super(table);
        }

        @Override
        public Query build() {
            StringBuilder builder = new StringBuilder();
            builder.append("DELETE FROM \"").append(table).append("\" ");
            conditions(builder);
            return new DeleteQuery(builder.toString());
        }
    }
}
