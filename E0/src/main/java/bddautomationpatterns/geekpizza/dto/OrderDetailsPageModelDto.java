package bddautomationpatterns.geekpizza.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class OrderDetailsPageModelDto {
    private String deliveryStreetAddress;
    private String deliveryCity;
    private String deliveryZip;
    private Optional<LocalDate> deliveryDate;
    private Optional<LocalTime> deliveryTime;

    public OrderDetailsPageModelDto() { }

    public OrderDetailsPageModelDto(LocalDate deliveryDate, LocalTime deliveryTime) {
        this.deliveryDate = Optional.ofNullable(deliveryDate);
        this.deliveryTime = Optional.ofNullable(deliveryTime);
    }

    public OrderDetailsPageModelDto(String deliveryStreetAddress, String deliveryCity, String deliveryZip, Optional<LocalDate> deliveryDate, Optional<LocalTime> deliveryTime) {
        this.deliveryStreetAddress = deliveryStreetAddress;
        this.deliveryCity = deliveryCity;
        this.deliveryZip = deliveryZip;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryStreetAddress() {
        return deliveryStreetAddress;
    }

    public void setDeliveryStreetAddress(String deliveryStreetAddress) {
        this.deliveryStreetAddress = deliveryStreetAddress;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryZip() {
        return deliveryZip;
    }

    public void setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
    }

    public Optional<LocalDate> getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = Optional.ofNullable(deliveryDate);
    }

    public Optional<LocalTime> getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalTime deliveryTime) {

        this.deliveryTime = Optional.ofNullable(deliveryTime);
    }
}
