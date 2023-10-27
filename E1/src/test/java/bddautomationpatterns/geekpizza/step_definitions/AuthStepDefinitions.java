package bddautomationpatterns.geekpizza.step_definitions;

import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import bddautomationpatterns.geekpizza.support.*;
import bddautomationpatterns.geekpizza.drivers.*;

public class AuthStepDefinitions {

    private boolean loginResult;

    @Autowired
    private OrderApiDriver orderApiDriver;
    @Autowired
    private AuthApiDriver authApiDriver;

    @Given("the client is logged in")
    public void theClientIsLoggedIn() {
        authApiDriver.login(DomainDefaults.userName, DomainDefaults.password);
    }

    @Given("the client is logged in with user name {string} and password {string}")
    public void theClientIsLoggedInWithUserNameFordAndPassword(String userName, String password) {
        authApiDriver.login(userName, password);
    }

    @When("the client attempts to log in with user name {string} and password {string}")
    public void theClientAttemptsToLogInWithUserNameAndPassword(String userName, String password) {
        loginResult = authApiDriver.attemptLogin(userName, password);
    }

    @Then("the login attempt should be successful")
    public void theLoginAttemptShouldBeSuccessful() {
        assertTrue(loginResult, String.format("The request should have been successful, but got '%s'", authApiDriver.responseMessage()));
    }

    @Then("the client should be able to access member-only services")
    public void theClientShouldBeAbleToAccessMemberOnlyServices() {
        // we use the "my order" api as an example of a member-only service
        orderApiDriver.getMyOrder();
    }

    @Then("the login attempt should fail with {string}")
    public void theLoginAttemptShouldFailWith(String expectedErrorMessage) {
        assertFalse(loginResult, "should have failed");
        assertTrue(authApiDriver.responseMessage().contains(expectedErrorMessage),
                String.format("The response '%s' did not contain the message '%s'",
                        authApiDriver.responseMessage(), expectedErrorMessage));
    }
}
