package airlines.utilData.metrics;

public class Length {

    private double length;
    private LengthType type;

    public Length(double length) {
        this(length, LengthType.METRE);
    }

    public Length(double length, LengthType type) {
        this.length = length;
        this.type = type;
    }

    public double getLength() {
        return length;
    }

    public LengthType getType() {
        return type;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setType(LengthType type) {
        this.type = type;
    }
}
