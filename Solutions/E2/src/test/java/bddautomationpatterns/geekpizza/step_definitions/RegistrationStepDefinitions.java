package bddautomationpatterns.geekpizza.step_definitions;

import java.util.List;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import bddautomationpatterns.geekpizza.dto.*;
import bddautomationpatterns.geekpizza.drivers.*;
import bddautomationpatterns.geekpizza.support.*;

public class RegistrationStepDefinitions {

    private boolean registerResult;

    @Autowired
    private UserApiDriver userApiDriver;
    @Autowired
    private CurrentUserContext currentUserContext;

    @Given("there is a user registered with user name {string} and password {string}")
    public void thereIsAUserRegisteredWithUserNameFordAndPassword(String userName, String password) {
        userApiDriver.register(new RegisterRequestDto(userName, password, password));
        currentUserContext.setUserName(userName);
        currentUserContext.setPassword(password);
    }

    @When("the client attempts to register with user name {string} and password {string}")
    public void theClientAttemptsToRegisterWithUserNameAndPassword(String userName, String password) {
        registerResult = userApiDriver.attemptRegister(userName, password, password);
    }

    @When("the client attempts to register with")
    public void theClientAttemptsToRegisterWith(List<RegisterRequestDto> registerRequests) {
        assertEquals(1, registerRequests.size(), "The data table of this step must contain a single data row");
        registerResult = userApiDriver.attemptRegister(registerRequests.get(0));
    }

    @Then("the registration should be successful")
    public void theRegistrationShouldBeSuccessful() {
        assertTrue(registerResult, String.format("The request should have been successful, but got '%s'", userApiDriver.responseMessage()));
    }

    @Then("the registration should fail with {string}")
    public void theRegistrationShouldFailWith(String expectedErrorMessage) {
        assertFalse(registerResult, "should have failed");
        assertTrue(userApiDriver.responseMessage().contains(expectedErrorMessage),
                String.format("The response '%s' did not contain the message '%s'",
                        userApiDriver.responseMessage(), expectedErrorMessage));
    }
}
