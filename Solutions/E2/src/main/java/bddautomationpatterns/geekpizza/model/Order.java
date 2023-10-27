package bddautomationpatterns.geekpizza.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private String userName;
    private Address deliveryAddress;
    private LocalDate deliveryDate;
    private LocalTime deliveryTime;
    private List<OrderItem> orderItems = new ArrayList<>();
    private OrderPrice prices = new OrderPrice();

    public Order() { }

    public Order(String userName, Address deliveryAddress, LocalDate deliveryDate, LocalTime deliveryTime, List<OrderItem> orderItems, OrderPrice prices) {
        this.userName = userName;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.orderItems = orderItems;
        this.prices = prices;
    }

    public Order(String userName, Address deliveryAddress, LocalDate deliveryDate, LocalTime deliveryTime) {
        this(userName, deliveryAddress, deliveryDate, deliveryTime, new ArrayList<>(), new OrderPrice());
    }

    public String getUserName() {
        return userName;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public LocalTime getDeliveryTime() {
        return deliveryTime;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderPrice getPrices() {
        return prices;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setDeliveryTime(LocalTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setPrices(OrderPrice prices) {
        this.prices = prices;
    }

    public static Order CreateOrder(String userName)
    {
        return new Order(userName, getUserDefaultAddress(), LocalDate.now(), LocalTime.now().plusMinutes(40));
    }

    private static Address getUserDefaultAddress()
    {
        // For the sake of the workshop, we have a hard-coded default address here
        return new Address("2-4 Waterloo Pl", "Edinburgh","EH1 3EG");
    }

    public OrderItem getOrderItemById(int id) {
        return getOrderItems().stream().filter(oi -> oi.getId().equals(id)).findFirst().orElse(null);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
    }

    public void addOrderItem(OrderItem orderItem){
        int itemId = orderItems.size() == 0 ? 1 : orderItems.stream().map(oi -> oi.getId()) .max(Integer::compare).get() + 1;
        orderItem.setId(itemId);
        orderItems.add(orderItem);
    }
}
