package bddautomationpatterns.geekpizza.controller;

import bddautomationpatterns.geekpizza.model.MenuItem;
import bddautomationpatterns.geekpizza.model.User;
import bddautomationpatterns.geekpizza.repository.GeekPizzaRepository;
import bddautomationpatterns.geekpizza.service.AuthenticationService;
import bddautomationpatterns.geekpizza.service.DefaultDataService;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @PostMapping("Reset")
    public void reset(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AuthenticationService.clearLoggedInUser(request, null);
        AuthenticationService.removeAuthCookie(response);

        GeekPizzaRepository repository = new GeekPizzaRepository();
        repository.clearData();
        repository.saveChanges();
    }

    @PostMapping("Seed")
    public void seed(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AuthenticationService.clearLoggedInUser(request, null);
        AuthenticationService.removeAuthCookie(response);

        GeekPizzaRepository repository = new GeekPizzaRepository();
        repository.clearData();
        DefaultDataService.seedWithDefaultData(repository);
        repository.saveChanges();
    }

    @PostMapping("DefaultLogin")
    public String defaultLogin(HttpServletResponse response){
        String token = AuthenticationService.setCurrentUser(DefaultDataService.getDefaultUserName());
        if (token == null)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unable to login with default user");

        AuthenticationService.addAuthCookie(token, response);
        return token;
    }

    @PostMapping("ClearMenu")
    public void clearMenu(){
        GeekPizzaRepository repository = new GeekPizzaRepository();
        repository.getMenuItems().clear();
        repository.saveChanges();
    }

    private boolean isValidMenuItem(MenuItem menuItem)
    {
        return !ObjectUtils.isEmpty(menuItem.getName()) &&
                !ObjectUtils.isEmpty(menuItem.getIngredients()) &&
                menuItem.getCalories() > 0;
    }

    @PostMapping("AddMenuItem")
    public MenuItem addMenuItem(@RequestBody MenuItem menuItem){
        if (!isValidMenuItem(menuItem))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid menu item");

        if (menuItem.getId() == 0)
            menuItem = new MenuItem(menuItem.getName(), menuItem.getIngredients(), menuItem.getCalories());

        GeekPizzaRepository repository = new GeekPizzaRepository();
        repository.getMenuItems().add(menuItem);
        repository.saveChanges();
        return menuItem;
    }

    @PostMapping("SetMenu")
    public void setMenu(@RequestBody MenuItem[] menuItems){
        for (MenuItem menuItem : menuItems) {
            if (!isValidMenuItem(menuItem))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid menu item");
        }

        GeekPizzaRepository repository = new GeekPizzaRepository();
        repository.getMenuItems().clear();
        repository.getMenuItems().addAll(Arrays.asList(menuItems));
        repository.saveChanges();
    }

    @PostMapping("AddUser")
    public User addUser(@RequestBody User user){
        if (ObjectUtils.isEmpty(user.getName()) || ObjectUtils.isEmpty(user.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name and Password must be provided");

        GeekPizzaRepository repository = new GeekPizzaRepository();
        User existingUser = repository.findUserByName(user.getName());
        if (existingUser != null)
            repository.getUsers().remove(existingUser);

        if (user.getId() == 0)
            user = new User(user.getName(), user.getPassword());

        repository.getUsers().add(user);
        repository.saveChanges();

        return user;
    }
}
