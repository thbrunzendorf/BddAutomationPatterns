package bddautomationpatterns.geekpizza.dto;

import bddautomationpatterns.geekpizza.model.OrderItemSize;

public class AddToOrderRequestDto {

    private String menuItemName;
    private OrderItemSize size;

    public AddToOrderRequestDto() {
    }

    public AddToOrderRequestDto(String menuItemName, OrderItemSize size) {
        this.menuItemName = menuItemName;
        this.size = size;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public OrderItemSize getSize() {
        return size;
    }

    public void setSize(OrderItemSize size) {
        this.size = size;
    }
}
