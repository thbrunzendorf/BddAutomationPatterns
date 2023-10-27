package bddautomationpatterns.geekpizza.drivers;

import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import bddautomationpatterns.geekpizza.support.*;
import bddautomationpatterns.geekpizza.dto.*;

@Component
@ScenarioScope
public class BasketPrerequisite extends TrackedPrerequisiteBase {
    @Autowired
    private OrderApiDriver orderApiDriver;

    @Override
    protected void fulfill() {
        orderApiDriver.addItemToOrder().perform(new AddToOrderRequestDto(DomainDefaults.menuItemName, DomainDefaults.orderItemSize));
    }
}
