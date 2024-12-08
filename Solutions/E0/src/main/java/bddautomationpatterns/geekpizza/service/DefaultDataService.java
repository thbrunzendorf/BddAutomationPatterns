package bddautomationpatterns.geekpizza.service;

import bddautomationpatterns.geekpizza.model.MenuItem;
import bddautomationpatterns.geekpizza.model.User;
import bddautomationpatterns.geekpizza.repository.Database;
import bddautomationpatterns.geekpizza.repository.GeekPizzaRepository;

import java.util.Arrays;

public class DefaultDataService {
    public static String getAdminUserName(){
        return "admin";
    }
    public static String getAdminPassword() { return "secret"; }

    public static String getDefaultUserName() { return "Marvin"; }
    public static String getDefaultPassword(){
        return "1234";
    }

    public static void seedWithDefaultData(GeekPizzaRepository repository) {
        addDefaultUsers(repository);
        addDefaultPizzas(repository);
    }

    private static void addDefaultUsers(GeekPizzaRepository repository) {
        repository.getUsers().addAll(
            Arrays.asList(
                new User(getDefaultUserName(), getDefaultPassword()),
                new User(getAdminUserName(), getAdminPassword())
            )
        );
    }

    private static void addDefaultPizzas(GeekPizzaRepository repository) {
        repository.getMenuItems().addAll(
            Arrays.asList(
                new MenuItem("Aslak Hellesøy's Cucumber", "Cucumber, Gherkin, Pickles", 1920),
                new MenuItem("Uncle Bob's FitNesse", "Chicken, Low cal cheese", 1340),
                new MenuItem("Chris Matts' GWT", "Garlic, Wasabi, Tomato", 1580),
                new MenuItem("Gojko Adzic's 50Q", "Quail, Quinoa, quark, quince & 46 others", 2340),
                new MenuItem("Dan North's b-hake", "Hake/cod/haddock, mushy peas, chips", 2150),
                new MenuItem("A very old pizza", "No one remembers...", 1010, true)
        ));
    }
}
