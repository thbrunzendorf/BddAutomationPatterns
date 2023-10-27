package bddautomationpatterns.geekpizza.support;

import io.cucumber.java.*;
import bddautomationpatterns.geekpizza.repository.GeekPizzaRepository;

public class DatabaseHooks {

    @Before(order = 10)
    public void resetDatabaseToBaseline(){

        GeekPizzaRepository repository = new GeekPizzaRepository();
        repository.clearData();
        repository.saveChanges();

        DomainDefaults.addDefaultPizzas();
        DomainDefaults.addDefaultUsers();
    }


}
