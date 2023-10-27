package bddautomationpatterns.geekpizza.drivers;

import java.util.List;
import org.springframework.stereotype.Component;
import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import bddautomationpatterns.geekpizza.support.*;
import bddautomationpatterns.geekpizza.model.*;

@Component
@ScenarioScope
public class MenuApiDriver {

    @Autowired
    protected WebApiContext webApiContext;

    public List<MenuItem> getMenuItems() {
        return webApiContext.executeGetList("/api/menu", MenuItem.class);
    }
}
