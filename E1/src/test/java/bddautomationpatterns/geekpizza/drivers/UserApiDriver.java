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

    //TODO: add automation logic for the /api/user endpoint
}
