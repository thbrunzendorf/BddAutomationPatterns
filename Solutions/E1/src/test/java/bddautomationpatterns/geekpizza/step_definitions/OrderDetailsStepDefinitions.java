package bddautomationpatterns.geekpizza.step_definitions;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import bddautomationpatterns.geekpizza.drivers.*;
import bddautomationpatterns.geekpizza.model.*;

public class OrderDetailsStepDefinitions {

    @Autowired
    private OrderApiDriver orderApiDriver;

    @When("the client specifies {date} at {time} as delivery time")
    public void theClientSpecifiesDateAtTimeAsDeliveryTime(LocalDate date, LocalTime time) {
        orderApiDriver.updateOrderDetails(date, time);
    }

    @Then("the order should indicate that the delivery date is {date}")
    public void theOrderShouldIndicateThatTheDeliveryDateIsDate(LocalDate expectedDate) {
        Order myOrderResponse = orderApiDriver.getMyOrder();
        assertEquals(expectedDate, myOrderResponse.getDeliveryDate());
    }

    @Then("the delivery time should be {time}")
    public void theDeliveryTimeShouldBe(LocalTime expectedTime) {
        Order myOrderResponse = orderApiDriver.getMyOrder();
        assertEquals(expectedTime, myOrderResponse.getDeliveryTime());
    }
}
