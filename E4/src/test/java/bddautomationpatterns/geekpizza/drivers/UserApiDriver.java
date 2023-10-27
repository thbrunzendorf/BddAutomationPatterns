package bddautomationpatterns.geekpizza.drivers;

import org.springframework.stereotype.Component;
import io.cucumber.spring.ScenarioScope;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import bddautomationpatterns.geekpizza.dto.*;
import bddautomationpatterns.geekpizza.support.*;

@Component
@ScenarioScope
public class UserApiDriver {

    @Autowired
    protected WebApiContext webApiContext;

    private StatusOnlyWebApiResponse webApiResponse;

    public String responseMessage() {
        return webApiResponse.responseMessage();
    }

    public boolean attemptRegister(String userName, String password, String passwordReEnter){
        RegisterRequestDto registerRequest = new RegisterRequestDto(userName, password, passwordReEnter);
        return attemptRegister(registerRequest);
    }

    public boolean attemptRegister(RegisterRequestDto registerRequest) {
        webApiResponse = webApiContext.executePost("/api/user", registerRequest);
        return webApiResponse.httpStatus() == HttpStatus.OK;
    }

    public void register(RegisterRequestDto registerRequest) {
        boolean result = attemptRegister(registerRequest);
        assertTrue(result, String.format("The request should have been successful, but got '%s'", responseMessage()));
    }
}
