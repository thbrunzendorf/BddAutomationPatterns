package bddautomationpatterns.geekpizza.support;

import io.cucumber.java.*;
import org.springframework.beans.factory.annotation.Autowired;
import bddautomationpatterns.geekpizza.drivers.*;
import bddautomationpatterns.geekpizza.dto.*;

public class AuthHooks {

    @Autowired
    private AuthApiDriver authApiDriver;

    @Before(value = "@login", order = 20)
    public void theClientIsLoggedIn() {
        authApiDriver.login().perform(new LoginRequestDto(DomainDefaults.userName, DomainDefaults.password));
    }
}
