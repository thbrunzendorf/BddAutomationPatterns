package bddautomationpatterns.geekpizza.controller;

import bddautomationpatterns.geekpizza.model.MenuItem;
import bddautomationpatterns.geekpizza.repository.GeekPizzaRepository;
import bddautomationpatterns.geekpizza.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // GET: api/admin/GetMenuItems -- returns all menu items
    @GetMapping("GetMenuItems")
    public List<MenuItem> getMenuItems(String token, HttpServletRequest request) {

        AuthenticationService.ensureAdminAuthenticated(request, token);

        GeekPizzaRepository repository = new GeekPizzaRepository();
        return repository.getMenuItems();
    }

    private boolean isValidMenuItem(MenuItem menuItem)
    {
        return !ObjectUtils.isEmpty(menuItem.getName()) &&
                !ObjectUtils.isEmpty(menuItem.getIngredients()) &&
                menuItem.getCalories() > 0;
    }

    // POST /api/admin/UpdateMenu -- replaces the menu
    @PostMapping("UpdateMenu")
    public void setMenu(@RequestBody MenuItem[] menuItems, String token, HttpServletRequest request){

        AuthenticationService.ensureAdminAuthenticated(request, token);

        if (menuItems == null || menuItems.length == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The menu cannot be empty!");

        for (MenuItem menuItem : menuItems) {
            if (!isValidMenuItem(menuItem))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid menu item");

            menuItem.ensureId();
        }

        GeekPizzaRepository repository = new GeekPizzaRepository();
        repository.getMenuItems().clear();
        repository.getMenuItems().addAll(Arrays.asList(menuItems));
        repository.saveChanges();
    }
}

