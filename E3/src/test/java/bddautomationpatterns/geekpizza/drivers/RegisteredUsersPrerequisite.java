package bddautomationpatterns.geekpizza.drivers;

import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import bddautomationpatterns.geekpizza.support.*;
import bddautomationpatterns.geekpizza.dto.*;

@Component
@ScenarioScope
public class RegisteredUsersPrerequisite extends ParametrizedPrerequisiteBase<String, TrackedPrerequisiteBase> {
    @Autowired
    private UserApiDriver userApiDriver;
    @Autowired
    private CurrentUserContext currentUserContext;

    public class RegisteredUserPrerequisite extends TrackedPrerequisiteBase {
        private final String userName;

        public RegisteredUserPrerequisite(String userName) {
            this.userName = userName;
        }

        @Override
        protected void fulfill() {
            System.out.printf("Registering user '%s' with password '%s'...%n", userName, DomainDefaults.password);
            userApiDriver.register(new RegisterRequestDto(userName, DomainDefaults.password, DomainDefaults.password));
            currentUserContext.setUserName(userName);
            currentUserContext.setPassword(DomainDefaults.password);
        }
    }

    @Override
    protected TrackedPrerequisiteBase createPrerequisite(String userName) {
        return new RegisteredUserPrerequisite(userName);
    }
}
