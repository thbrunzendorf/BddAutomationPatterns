package bddautomationpatterns.geekpizza.step_definitions;

import bddautomationpatterns.geekpizza.model.Order;
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

    @Given("it contains a {string} {string}")
    public void itContainsAnItem(String size, String name) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
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

    @Given("an order with the following items")
    public void anOrderWithTheFollowingItems(List<Map<String,String>> dataTable) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
