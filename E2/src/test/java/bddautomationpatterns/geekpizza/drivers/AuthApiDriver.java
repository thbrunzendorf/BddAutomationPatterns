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
public class AuthApiDriver {

    @Autowired
    protected WebApiContext webApiContext;

    private StatusOnlyWebApiResponse webApiResponse;

    public String responseMessage() {
        return webApiResponse.responseMessage();
    }

    public boolean attemptLogin(String userName, String password){
        webApiResponse = webApiContext.executePost("/api/auth", new LoginRequestDto(userName, password));
        return webApiResponse.httpStatus() == HttpStatus.OK;
    }

    public void login(String userName, String password){
        boolean result = attemptLogin(userName, password);
        assertTrue(result, String.format("The request should have been successful, but got '%s'", responseMessage()));
    }
}
