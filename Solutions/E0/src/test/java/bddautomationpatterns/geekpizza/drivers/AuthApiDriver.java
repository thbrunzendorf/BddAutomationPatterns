package bddautomationpatterns.geekpizza.drivers;

import org.springframework.stereotype.Component;
import io.cucumber.spring.ScenarioScope;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import bddautomationpatterns.geekpizza.dto.*;
import bddautomationpatterns.geekpizza.support.*;

@Component
@ScenarioScope
public class AuthApiDriver {

    @Autowired
    protected WebApiContext webApiContext;
    @Autowired
    protected CurrentUserContext currentUserContext;

    private final CommandActionAttempt<LoginRequestDto> loginAction = WebApiActionAttempt.createCommand(
            request -> {
                currentUserContext.setUserName(request.getName());
                currentUserContext.setPassword(request.getPassword());
                return webApiContext.executePost("/api/auth", request);
            }, HttpStatus.OK);

    public CommandActionAttempt<LoginRequestDto> login() {
        return loginAction;
    }
}
