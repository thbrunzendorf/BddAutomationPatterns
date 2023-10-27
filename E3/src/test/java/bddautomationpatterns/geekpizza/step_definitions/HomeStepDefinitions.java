package bddautomationpatterns.geekpizza.step_definitions;

import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import bddautomationpatterns.geekpizza.drivers.*;
import bddautomationpatterns.geekpizza.dto.*;
import bddautomationpatterns.geekpizza.support.*;

public class HomeStepDefinitions {

    private HomePageModelDto homePageModelDto;

    @Autowired
    private HomeApiDriver homeApiDriver;
    @Autowired
    protected CurrentUserContext currentUserContext;

    @When("the client checks the home page")
    public void theClientChecksTheHomePage() {

        homePageModelDto = homeApiDriver.getHomePageModel();
    }

    @Then("the home page main message should be: {string}")
    public void theHomePageMainMessageShouldBe(String expectedMessage) {

        assertEquals(expectedMessage, homePageModelDto.getWelcomeMessage());
    }

    @Then("the user name of the client should be on the home page")
    public void theUserNameOfTheClientShouldBeOnTheHomePage() {
        assertNotNull(currentUserContext.getUserName(), "There should be a current user");
        assertEquals(currentUserContext.getUserName(), homePageModelDto.getUserName(), "The name of the current user should be on the home page");
    }
}
