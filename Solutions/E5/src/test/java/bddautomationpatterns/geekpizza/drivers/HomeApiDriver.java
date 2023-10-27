package bddautomationpatterns.geekpizza.drivers;

import org.springframework.stereotype.Component;
import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import bddautomationpatterns.geekpizza.dto.*;
import bddautomationpatterns.geekpizza.support.*;

@Component
@ScenarioScope
public class HomeApiDriver {

    @Autowired
    protected WebApiContext webApiContext;

    public HomePageModelDto getHomePageModel(){
        return webApiContext.executeGet("/api/home", HomePageModelDto.class);
    }
}
