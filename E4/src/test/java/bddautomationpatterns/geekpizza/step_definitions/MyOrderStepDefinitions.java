package bddautomationpatterns.geekpizza.step_definitions;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import static org.junit.jupiter.api.Assertions.*;
import bddautomationpatterns.geekpizza.drivers.*;
import bddautomationpatterns.geekpizza.dto.*;
import bddautomationpatterns.geekpizza.model.*;
import bddautomationpatterns.geekpizza.support.*;

public class MyOrderStepDefinitions {

    private Order myOrderResponse;
    private DataTable orderedItems;
    private boolean placeOrderResult;

    @Autowired
    private OrderApiDriver orderApiDriver;

    @Autowired
    private CurrentOrderContext currentOrderContext;

    @Autowired
    private LoggedInUserPrerequisite loggedInUserPrerequisite;

    @Autowired
    private BasketPrerequisite basketPrerequisite;

    @Given("the client has the following items in the basket")
    public void theClientHasTheFollowingItemsInTheBasket(DataTable orderItemsTable) {
        List<Map<String, String>> orderItems = orderItemsTable.asMaps();

        for (Map<String, String> orderItemRow: orderItems) {
            AddToOrderRequestDto addToOrderRequestDto = new AddToOrderRequestDto(
                    orderItemRow.get("name"),
                    orderItemRow.containsKey("size") ?
                            OrderItemSize.valueOf(OrderItemSize.class, orderItemRow.get("size")) :
                            DomainDefaults.orderItemSize
            );
            orderApiDriver.addItemToOrder(addToOrderRequestDto);
        }
        orderedItems = orderItemsTable;
        basketPrerequisite.signalFulfilled();
    }

    @Given("the client has the following items in the basket - ensure version")
    public void theClientHasTheFollowingItemsInTheBasketEnsureVersion(DataTable orderItemsTable) {

        loggedInUserPrerequisite.ensure();

        // from this point it is the same as the non-ensure version
        // the code is duplicated for the sake of the demonstration
        List<Map<String, String>> orderItems = orderItemsTable.asMaps();

        for (Map<String, String> orderItemRow: orderItems) {
            AddToOrderRequestDto addToOrderRequestDto = new AddToOrderRequestDto(
                    orderItemRow.get("name"),
                    orderItemRow.containsKey("size") ?
                            OrderItemSize.valueOf(OrderItemSize.class, orderItemRow.get("size")) :
                            DomainDefaults.orderItemSize
            );
            orderApiDriver.addItemToOrder(addToOrderRequestDto);
        }
        orderedItems = orderItemsTable;
        basketPrerequisite.signalFulfilled();
    }

    @Given("the client has added a {orderItemSize} {word} pizza to the basket")
    public void theClientHasAddedAPizzaToTheBasket(OrderItemSize size, String pizzaName) {
        AddToOrderResponseDto response = orderApiDriver.addItemToOrder(new AddToOrderRequestDto(pizzaName, size));
        currentOrderContext.setCurrentItemId(response.getCreatedItemId());
        basketPrerequisite.signalFulfilled();
    }

    @Given("the client has items in the basket")
    public void theClientHasItemsInTheBasket() {
        orderApiDriver.addItemToOrder(DomainDefaults.menuItemName, DomainDefaults.orderItemSize);
        basketPrerequisite.signalFulfilled();
    }

    @When("the client adds a {orderItemSize} {word} pizza to the basket")
    public void theClientAddsAPizzaToTheBasket(OrderItemSize size, String pizzaName) {
        orderApiDriver.addItemToOrder(new AddToOrderRequestDto(pizzaName, size));
        myOrderResponse = orderApiDriver.getMyOrder();
    }

    @When("the client checks the my order page")
    public void theClientChecksTheMyOrderPage() {
        myOrderResponse = orderApiDriver.getMyOrder();
    }

    @When("the client changes its size to {orderItemSize}")
    public void theClientChangesItsSizeTo(OrderItemSize size) {
        orderApiDriver.updateOrderItem(currentOrderContext.getCurrentItemId(), new UpdateOrderItemRequestDto(size));
        myOrderResponse = orderApiDriver.getMyOrder();
    }

    @Then("the following items should be listed on the my order page")
    public void theFollowingItemsShouldBeListedOnTheMyOrderPage(DataTable expectedOrderItemsTable) {

        DataTableComparer.assertMatchesToList(expectedOrderItemsTable, myOrderResponse.getOrderItems());
    }

    @Then("the ordered items should be listed on the my order page")
    public void theOrderedItemsShouldBeListedOnTheMyOrderPage() {
        theFollowingItemsShouldBeListedOnTheMyOrderPage(orderedItems);
    }

    @When("the client attempts to place the order")
    public void theClientAttemptsToPlaceTheOrder() {
        basketPrerequisite.ensure();
        placeOrderResult = orderApiDriver.attemptPlaceOrder();
    }

    @Then("the order placement should be successful")
    public void theOrderPlacementShouldBeSuccessful() {
        assertTrue(placeOrderResult, String.format("The request should have been successful, but got '%s'", orderApiDriver.responseMessage()));
    }
}
