package bddautomationpatterns.geekpizza.model;

public class OrderPrice {
    private double subTotal;
    private double deliveryCosts;
    private double total;

    public OrderPrice() { }

    public OrderPrice(double subTotal, double deliveryCosts, double total) {
        this.subTotal = subTotal;
        this.deliveryCosts = deliveryCosts;
        this.total = total;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public double getDeliveryCosts() {
        return deliveryCosts;
    }

    public double getTotal() {
        return total;
    }
}
