package annotations;

public enum  CheckType {
    MORE(">"), LESS("<"), MORE_AND_EQUAL(">="), LESS_AND_EQUAL("<="), EQUAL("="), NOT_EQUAL("<>");

    CheckType(String expression) {
        this.expression = expression;
    }

    final String expression;

    @Override
    public String toString() {
        return expression;
    }
}
