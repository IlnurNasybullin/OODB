package airlines.userTypes.metrics;

import annotations.TypeComponent;
import annotations.UserType;

import java.util.Objects;

@UserType
public class Length {

    @TypeComponent
    private double length;
    @TypeComponent
    private LengthType type;

    private Length() {this(0d);}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Length that = (Length) o;
        return Double.compare(that.length, length) == 0 &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, type);
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", length, type);
    }
}
