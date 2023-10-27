package bddautomationpatterns.geekpizza.step_definitions;

import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import bddautomationpatterns.geekpizza.support.*;
import bddautomationpatterns.geekpizza.drivers.*;
import bddautomationpatterns.geekpizza.dto.*;

public class AuthStepDefinitions {

    @Autowired
    private OrderApiDriver orderApiDriver;
    @Autowired
    private AuthApiDriver authApiDriver;
    @Autowired
    private CurrentUserContext currentUserContext;
    @Autowired
    private LoggedInUserPrerequisite loggedInUserPrerequisite;
    @Autowired
    private RegisteredUsersPrerequisite registeredUsersPrerequisite;

    @Given("the client is logged in")
    public void theClientIsLoggedIn() {
        authApiDriver.login().perform(new LoginRequestDto(DomainDefaults.userName, DomainDefaults.password));
        loggedInUserPrerequisite.signalFulfilled();
    }

    @Given("the client {string} is logged in")
    public void theClientIsLoggedIn(String userName) {
        registeredUsersPrerequisite.ensure(userName);
        authApiDriver.login().perform(new LoginRequestDto(userName, currentUserContext.getPassword()));
        loggedInUserPrerequisite.signalFulfilled();
    }

    @Given("the client is logged in with user name {string} and password {string}")
    @When("the client logs in with user name {string} and password {string}")
    public void theClientIsLoggedInWithUserNameFordAndPassword(String userName, String password) {
        authApiDriver.login().perform(new LoginRequestDto(userName, password));
        loggedInUserPrerequisite.signalFulfilled();
    }

    @When("the client attempts to log in with user name {string} and password {string}")
    public void theClientAttemptsToLogInWithUserNameAndPassword(String userName, String password) {
        authApiDriver.login().attempt(new LoginRequestDto(userName, password));
    }

    @When("the client logs in with the registered user credentials")
    public void theClientLogsInWithTheRegisteredUserCredentials() {
        authApiDriver.login().perform(new LoginRequestDto(currentUserContext.getUserName(), currentUserContext.getPassword()));
    }

    @Then("the login attempt should be successful")
    public void theLoginAttemptShouldBeSuccessful() {
        authApiDriver.login().assertSuccess();
    }

    @Then("the client should be able to access member-only services")
    public void theClientShouldBeAbleToAccessMemberOnlyServices() {
        // we use the "my order" api as an example of a member-only service
        orderApiDriver.getMyOrder();
    }

    @Then("the login attempt should fail with {string}")
    public void theLoginAttemptShouldFailWith(String expectedErrorMessage) {
        authApiDriver.login().assertFailWith(expectedErrorMessage);
    }
}
