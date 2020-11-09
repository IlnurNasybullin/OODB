package airlines.userTypes.geographic;

public enum LatitudeDirection {
    NORTH("с.ш."), SOUTH("ю.ш.");

    String direction;

    LatitudeDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return direction;
    }
}
