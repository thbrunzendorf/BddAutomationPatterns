package bddautomationpatterns.geekpizza.step_definitions;

import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import bddautomationpatterns.geekpizza.drivers.*;
import bddautomationpatterns.geekpizza.support.*;
import bddautomationpatterns.geekpizza.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuStepDefinitions {

    private List<MenuItem> menuItems;

    @Autowired
    private MenuApiDriver menuApiDriver;

    @When("the client checks the menu page")
    public void theClientChecksTheMenuPage() {

        menuItems = menuApiDriver.getMenuItems();
    }

    @Then("there should be {int} pizzas listed")
    public void thereShouldBePizzasListed(int expectedCount) {
        assertEquals(expectedCount, menuItems.size());
    }

    @Then("the following pizzas should be listed in this order")
    public void theFollowingPizzasShouldBeListedInThisOrder(DataTable expectedMenuItemsTable) {

        DataTableComparer.assertMatchesToList(expectedMenuItemsTable, menuItems);
    }
}
