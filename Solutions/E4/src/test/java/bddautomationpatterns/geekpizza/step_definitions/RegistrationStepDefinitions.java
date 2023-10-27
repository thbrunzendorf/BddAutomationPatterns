package bddautomationpatterns.geekpizza.step_definitions;

import java.util.List;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import bddautomationpatterns.geekpizza.dto.*;
import bddautomationpatterns.geekpizza.drivers.*;
import bddautomationpatterns.geekpizza.support.*;

public class RegistrationStepDefinitions {

    @Autowired
    private UserApiDriver userApiDriver;
    @Autowired
    private CurrentUserContext currentUserContext;
    @Autowired
    private RegisteredUsersPrerequisite registeredUsersPrerequisite;

    @Given("there is a user registered with user name {string} and password {string}")
    public void thereIsAUserRegisteredWithUserNameFordAndPassword(String userName, String password) {
        userApiDriver.register().perform(new RegisterRequestDto(userName, password, password));
        currentUserContext.setUserName(userName);
        currentUserContext.setPassword(password);
        registeredUsersPrerequisite.getPrerequisite(userName).signalFulfilled();
    }

    @When("the client attempts to register with user name {string} and password {string}")
    public void theClientAttemptsToRegisterWithUserNameAndPassword(String userName, String password) {
        userApiDriver.register().attempt(new RegisterRequestDto(userName, password, password));
    }

    @When("the client attempts to register with")
    public void theClientAttemptsToRegisterWith(List<RegisterRequestDto> registerRequests) {
        assertEquals(1, registerRequests.size(), "The data table of this step must contain a single data row");
        userApiDriver.register().attempt(registerRequests.get(0));
    }

    @Then("the registration should be successful")
    public void theRegistrationShouldBeSuccessful() {

        userApiDriver.register().assertSuccess();
    }

    @Then("the registration should fail with {string}")
    public void theRegistrationShouldFailWith(String expectedErrorMessage) {
        userApiDriver.register().assertFailWith(expectedErrorMessage);
    }
}
