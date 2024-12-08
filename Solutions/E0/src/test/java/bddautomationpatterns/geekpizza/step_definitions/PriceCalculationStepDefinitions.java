package bddautomationpatterns.geekpizza.step_definitions;

import bddautomationpatterns.geekpizza.model.Order;
import bddautomationpatterns.geekpizza.model.OrderItem;
import bddautomationpatterns.geekpizza.model.OrderItemSize;
import bddautomationpatterns.geekpizza.service.PriceCalculatorService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceCalculationStepDefinitions {

    private Order order;

    private void createNewOrder() {
        order = new Order("some name", null, null, null);
    }

    @Given("a new order")
    public void aNewOrderIsCreated() {
        createNewOrder();
    }

    //@Given("it contains a {string} {string}")
    public void itContainsAnItem(String size, String name) {
        OrderItemSize itemSize = OrderItemSize.valueOf(OrderItemSize.class, size);
        order.addOrderItem(new OrderItem(name, itemSize));
    }

    @Given("it contains a {orderItemSize} {string}")
    public void itContainsAnItemWithParameterType(OrderItemSize itemSize, String name) {
        order.addOrderItem(new OrderItem(name, itemSize));
    }

    @When("the order price is calculated")
    public void theOrderPriceIsCalculated() {
        PriceCalculatorService priceCalculatorService = new PriceCalculatorService();
        priceCalculatorService.updatePrice(order);
    }

    @Then("the subtotal should be {double}")
    public void theSubtotalShouldBe(Double subtotal) {
        assertEquals(subtotal, order.getPrices().getSubTotal());
    }

    @Then("the delivery costs should be {double}")
    public void theDeliveryCostsShouldBe(Double deliveryCosts) {
        assertEquals(deliveryCosts, order.getPrices().getDeliveryCosts());
    }

    @Then("the total price should be {double}")
    public void theTotalPriceShouldBe(Double totalPrice) {
        assertEquals(totalPrice, order.getPrices().getTotal());
    }

    //@Given("an order with the following items")
    public void anOrderWithTheFollowingItems(List<Map<String,String>> dataTable) {
        createNewOrder();
        for (Map<String,String> entry: dataTable) {
            String name = entry.get("name");
            String size = entry.get("size");
            boolean extraCheese = Boolean.parseBoolean(entry.getOrDefault("extraCheese", Boolean.FALSE.toString()));
            OrderItemSize itemSize = OrderItemSize.valueOf(OrderItemSize.class, size);
            OrderItem item = new OrderItem(name, itemSize);
            item.setExtraCheese(extraCheese);
            order.addOrderItem(item);
        }
    }

    @Given("an order with the following items")
    public void anOrderWithTheFollowingItemsWithDataTableEntryTransformer(List<OrderItem> orderItems) {
        createNewOrder();
        for (OrderItem item: orderItems) {
            order.addOrderItem(item);
        }
    }
}
