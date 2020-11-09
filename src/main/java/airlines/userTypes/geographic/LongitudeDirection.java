package airlines.userTypes.geographic;

public enum LongitudeDirection {
    EAST("в.д."), WEST("з.д.");

    LongitudeDirection(String direction) {
        this.direction = direction;
    }

    String direction;

    @Override
    public String toString() {
        return direction;
    }
}
