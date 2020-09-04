package airlines.utilData;

public class Address {
    private String country;
    private String region;
    private String city;
    private String street;
    private String house;
    private String flat;

    private String postalCode;

    public Address(String country, String region, String city, String street, String house, String flat, String postalCode) {
        this.country = country;
        this.region = region;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.postalCode = postalCode;
    }
}
