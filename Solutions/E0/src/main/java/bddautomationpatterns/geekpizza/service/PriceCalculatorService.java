package bddautomationpatterns.geekpizza.service;

import bddautomationpatterns.geekpizza.model.Order;
import bddautomationpatterns.geekpizza.model.OrderItem;
import bddautomationpatterns.geekpizza.model.OrderPrice;

public class PriceCalculatorService {
    private static final double LARGE_PRICE = 25;
    private static final double MEDIUM_PRICE = 15;
    private static final double SMALL_PRICE = 10;
    private static final double EXTRA_CHEESE = 1;
    private static final double DELIVERY_COST = 5;
    private static final double DELIVERY_COST_THRESHOLD = 40;

    protected double getOrderItemPrice(OrderItem orderItem)
    {
        double itemPrice = 0;
        switch (orderItem.getSize())
        {
            case Large:
                itemPrice += LARGE_PRICE;
                break;
            case Small:
                itemPrice += SMALL_PRICE;
                break;
            default:
                itemPrice += MEDIUM_PRICE;
        }
        if (orderItem.isExtraCheese())
            itemPrice += EXTRA_CHEESE;

        return itemPrice;
    }

    protected double getSubtotal(Order order){
        double subtotal = 0;
        subtotal += order.getOrderItems().stream().mapToDouble(this::getOrderItemPrice).sum();
        return subtotal;
    }

    protected double getDeliveryCost(double subtotal){
        double deliveryCosts = 0;
        if (subtotal <= DELIVERY_COST_THRESHOLD && subtotal > 0)
            deliveryCosts = DELIVERY_COST;
        return deliveryCosts;
    }

    protected OrderPrice getOrderPrice(Order order)
    {
        double subtotal = getSubtotal(order);
        double deliveryCosts = getDeliveryCost(subtotal);
        return new OrderPrice(subtotal, deliveryCosts, subtotal + deliveryCosts);
    }

    public void updatePrice(Order order)
    {
        order.setPrices(getOrderPrice(order));
    }
}
