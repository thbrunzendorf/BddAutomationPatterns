package bddautomationpatterns.geekpizza.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import bddautomationpatterns.geekpizza.model.MenuItem;
import bddautomationpatterns.geekpizza.model.Order;
import bddautomationpatterns.geekpizza.model.User;
import bddautomationpatterns.geekpizza.service.DefaultDataService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Console;
import java.util.List;
import java.util.Optional;

public class GeekPizzaRepository {

    private DataPersist dataPersist = new FileDataPersist();
    private Database database;

    public GeekPizzaRepository() {
        loadData();
    }

    public List<MenuItem> getMenuItems() {
        return database.getMenuItems();
    }

    public MenuItem getMenuItemById(Integer id) {
        return database.getMenuItems().stream().filter(mi -> mi.getId().equals(id)).findFirst().orElse(null);
    }

    public MenuItem findMenuItemByName(String menuItemName) {
        return database.getMenuItems().stream().filter(mi -> mi.getName().equals(menuItemName)).findFirst().orElse(null);
    }

    public List<User> getUsers() {
        return database.getUsers();
    }

    public User findUserByName(String name) {
        return database.getUsers().stream().filter(u -> u.getName().equals(name)).findFirst().orElse(null);
    }

    public Order getMyOrder(String userName) {
        Order myOrder = database.getOrders().stream().filter(o -> o.getUserName().equals(userName)).findFirst().orElse(null);
        if (myOrder == null){
            myOrder = Order.CreateOrder(userName);
            database.getOrders().add(myOrder);
        }
        return myOrder;
    }

    public void deleteMyOrder(String userName) {
        Order myOrder = database.getOrders().stream().filter(o -> o.getUserName().equals(userName)).findFirst().orElse(null);
        if (myOrder != null){
            database.getOrders().remove(myOrder);
        }
    }

    private void loadData() {
        try {
            String json = dataPersist.load();
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            database = mapper.readValue(json, Database.class);
        }
        catch (Exception ex) {
            resetToDefault();
        }
    }

    public void resetToDefault(){

        try {
            clearData();
            DefaultDataService.seedWithDefaultData(this);
            saveChanges();
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

    public void clearData() {
        database = new Database();
    }

    public void saveChanges() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            String json = mapper.writeValueAsString(database);
            dataPersist.save(json);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }
}
