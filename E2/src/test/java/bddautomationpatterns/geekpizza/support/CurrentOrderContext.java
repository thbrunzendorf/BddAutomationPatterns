package bddautomationpatterns.geekpizza.support;

import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class CurrentOrderContext {
    private Integer currentItemId;

    public Integer getCurrentItemId() {
        return currentItemId;
    }

    public void setCurrentItemId(Integer currentItemId) {
        this.currentItemId = currentItemId;
    }
}
