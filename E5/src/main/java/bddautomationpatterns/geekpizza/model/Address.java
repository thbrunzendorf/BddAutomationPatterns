package bddautomationpatterns.geekpizza.model;

public class Address {
    private String streetAddress;
    private String city;
    private String zip;

    public Address() { }

    public Address(String streetAddress, String city, String zip) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.zip = zip;
    }

    public String getStreetAddress() { return streetAddress; }

    public String getCity() { return city; }

    public String getZip() { return zip; }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
