package bddautomationpatterns.geekpizza.drivers;

import org.springframework.stereotype.Component;
import io.cucumber.spring.ScenarioScope;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import bddautomationpatterns.geekpizza.dto.*;
import bddautomationpatterns.geekpizza.support.*;

@Component
@ScenarioScope
public class UserApiDriver {

    @Autowired
    protected WebApiContext webApiContext;

    private final CommandActionAttempt<RegisterRequestDto> registerAction = WebApiActionAttempt.createCommand(
            request -> webApiContext.executePost("/api/user", request), HttpStatus.OK);

    public CommandActionAttempt<RegisterRequestDto> register() {
        return registerAction;
    }
}
