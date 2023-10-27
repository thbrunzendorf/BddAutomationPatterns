package bddautomationpatterns.geekpizza.support;

import bddautomationpatterns.geekpizza.model.MenuItem;
import bddautomationpatterns.geekpizza.model.OrderItemSize;
import bddautomationpatterns.geekpizza.model.User;
import bddautomationpatterns.geekpizza.repository.GeekPizzaRepository;

import java.util.Arrays;

public class DomainDefaults {

    public static final String userName = "Marvin";
    public static final String password = "1234";

    public static final String altUserName = "Ford";
    public static final String altPassword = "1423";

    public static String menuItemName = "Margherita";
    public static final String menuItemIngredients = "[default ingredients]";
    public static final int menuItemCalories = 1000;

    public static final OrderItemSize orderItemSize = OrderItemSize.Medium;

    public static void addDefaultUsers() {
        GeekPizzaRepository repository = new GeekPizzaRepository();
        repository.getUsers().addAll(
                Arrays.asList(
                        new User(userName, password),
                        new User(altUserName, altPassword)
                )
        );
        repository.saveChanges();
    }

    public static void addDefaultPizzas() {
        GeekPizzaRepository repository = new GeekPizzaRepository();
        repository.getMenuItems().addAll(
                Arrays.asList(
                        new MenuItem(menuItemName, "tomato slices, oregano, mozzarella", 1920),
                        new MenuItem("Fitness", "sweetcorn, broccoli, feta cheese, mozzarella", 1340),
                        new MenuItem("BBQ", "BBQ sauce, bacon, chicken breast strips, onions", 1580),
                        new MenuItem("Mexican", "taco sauce, bacon, beans, sweetcorn, mozzarella", 2340),
                        new MenuItem("Quattro formaggi", "blue cheese, parmesan, smoked mozzarella, mozzarella", 2150),
                        new MenuItem("The old one", "No one remembers...", 1010, true)
                ));
        repository.saveChanges();
    }

}
