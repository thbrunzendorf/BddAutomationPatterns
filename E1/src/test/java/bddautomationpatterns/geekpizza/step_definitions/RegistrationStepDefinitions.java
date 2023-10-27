package bddautomationpatterns.geekpizza.step_definitions;

import java.util.List;

import bddautomationpatterns.geekpizza.support.StatusOnlyWebApiResponse;
import bddautomationpatterns.geekpizza.support.WebApiContext;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import bddautomationpatterns.geekpizza.dto.*;
import bddautomationpatterns.geekpizza.drivers.*;
import org.springframework.http.HttpStatus;

public class RegistrationStepDefinitions {

    private StatusOnlyWebApiResponse registerResult;

    @Autowired
    private WebApiContext webApiContext;

    @Given("there is a user registered with user name {string} and password {string}")
    public void thereIsAUserRegisteredWithUserNameFordAndPassword(String userName, String password) {
        StatusOnlyWebApiResponse result = webApiContext.executePost("/api/user", new RegisterRequestDto(userName, password, password));
        assertEquals(HttpStatus.OK, result.httpStatus(), String.format("The request should have been successful, but got '%s'", result.responseMessage()));
    }

    @When("the client attempts to register with user name {string} and password {string}")
    public void theClientAttemptsToRegisterWithUserNameAndPassword(String userName, String password) {
        registerResult = webApiContext.executePost("/api/user", new RegisterRequestDto(userName, password, password));
    }

    @When("the client attempts to register with")
    public void theClientAttemptsToRegisterWith(List<RegisterRequestDto> registerRequests) {
        assertEquals(1, registerRequests.size(), "The data table of this step must contain a single data row");
        registerResult = webApiContext.executePost("/api/user", registerRequests.get(0));
    }

    @Then("the registration should be successful")
    public void theRegistrationShouldBeSuccessful() {
        assertEquals(HttpStatus.OK, registerResult.httpStatus(), String.format("The request should have been successful, but got '%s'", registerResult.responseMessage()));
    }

    @Then("the registration should fail with {string}")
    public void theRegistrationShouldFailWith(String expectedErrorMessage) {
        assertNotEquals(HttpStatus.OK, registerResult.httpStatus(), "should have failed");
        assertTrue(registerResult.responseMessage().contains(expectedErrorMessage),
                String.format("The response '%s' did not contain the message '%s'",
                        registerResult.responseMessage(), expectedErrorMessage));
    }
}
