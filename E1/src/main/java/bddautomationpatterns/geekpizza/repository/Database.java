package bddautomationpatterns.geekpizza.repository;

import bddautomationpatterns.geekpizza.model.MenuItem;
import bddautomationpatterns.geekpizza.model.Order;
import bddautomationpatterns.geekpizza.model.User;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<MenuItem> menuItems = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
}
