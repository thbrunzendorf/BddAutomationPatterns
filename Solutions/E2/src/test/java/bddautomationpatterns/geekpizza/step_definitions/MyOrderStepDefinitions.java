package bddautomationpatterns.geekpizza.step_definitions;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import bddautomationpatterns.geekpizza.drivers.*;
import bddautomationpatterns.geekpizza.dto.*;
import bddautomationpatterns.geekpizza.model.*;
import bddautomationpatterns.geekpizza.support.*;

public class MyOrderStepDefinitions {

    private Order myOrderResponse;
    private DataTable orderedItems;

    @Autowired
    private OrderApiDriver orderApiDriver;

    @Autowired
    private CurrentOrderContext currentOrderContext;

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
    }

    @Given("the client has added a {orderItemSize} {word} pizza to the basket")
    public void theClientHasAddedAPizzaToTheBasket(OrderItemSize size, String pizzaName) {
        AddToOrderResponseDto response = orderApiDriver.addItemToOrder(new AddToOrderRequestDto(pizzaName, size));
        currentOrderContext.setCurrentItemId(response.getCreatedItemId());
    }

    @Given("the client has items in the basket")
    public void theClientHasItemsInTheBasket() {
        orderApiDriver.addItemToOrder(DomainDefaults.menuItemName, DomainDefaults.orderItemSize);
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
}
