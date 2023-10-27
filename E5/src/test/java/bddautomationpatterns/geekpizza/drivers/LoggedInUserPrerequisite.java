package bddautomationpatterns.geekpizza.drivers;

import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import bddautomationpatterns.geekpizza.support.*;
import bddautomationpatterns.geekpizza.dto.*;

@Component
@ScenarioScope
public class LoggedInUserPrerequisite extends TrackedPrerequisiteBase {

    @Autowired
    private AuthApiDriver authApiDriver;

    @Override
    protected void fulfill() {
        System.out.printf("Logging in with '%s' using password '%s'...%n", DomainDefaults.userName, DomainDefaults.password);
        authApiDriver.login().perform(new LoginRequestDto(DomainDefaults.userName, DomainDefaults.password));
    }
}
