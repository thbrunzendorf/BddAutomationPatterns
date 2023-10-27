package bddautomationpatterns.geekpizza.drivers;

import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import bddautomationpatterns.geekpizza.support.*;

@Component
@ScenarioScope
public class BasketPrerequisite extends TrackedPrerequisiteBase {
    @Autowired
    private OrderApiDriver orderApiDriver;

    @Override
    protected void fulfill() {
        //TODO: make sure there is something in the basket!
    }
}
