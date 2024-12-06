package bddautomationpatterns.geekpizza.service;

import java.util.Arrays;

import bddautomationpatterns.geekpizza.model.MenuItem;
import bddautomationpatterns.geekpizza.model.User;
import bddautomationpatterns.geekpizza.repository.GeekPizzaRepository;

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
                new MenuItem("BBQ", "BBQ sauce, bacon, chicken breast strips, onions", 1920, false),
                new MenuItem("Fitness", "sweetcorn, broccoli, feta cheese, mozzarella", 1340),
                new MenuItem("Margherita", "tomato slices, oregano, mozzarella", 1580),
                new MenuItem("Mexican", "taco sauce, bacon, beans, sweetcorn, mozzarella", 2340),
                new MenuItem("Quattro formaggi", "blue cheese, parmesan, smoked mozzarella, mozzarella", 2150)
        ));
    }
}
