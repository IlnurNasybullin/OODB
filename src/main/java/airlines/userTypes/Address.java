package airlines.userTypes;

import annotations.TypeComponent;
import annotations.UserType;

import java.util.Objects;
@UserType
public class Address {
    @TypeComponent
    private String country;
    @TypeComponent
    private String region;
    @TypeComponent
    private String city;
    @TypeComponent
    private String street;
    @TypeComponent
    private String house;
    @TypeComponent
    private String flat;
    @TypeComponent
    private String postalCode;

    public Address() { }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getFlat() {
        return flat;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(country, address.country) &&
                Objects.equals(region, address.region) &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(house, address.house) &&
                Objects.equals(flat, address.flat) &&
                Objects.equals(postalCode, address.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, region, city, street, house, flat, postalCode);
    }

    @Override
    public String toString() {
        return String.format("Address{country = %s, region = %s, city = %s, street = %s, house = %s, flat = %s, postalCode = %s}",
                             country, region, city, street, house, flat, postalCode);
    }
}
