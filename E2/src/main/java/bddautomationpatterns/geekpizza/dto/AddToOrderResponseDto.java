package bddautomationpatterns.geekpizza.dto;

import bddautomationpatterns.geekpizza.model.Order;

public class AddToOrderResponseDto {
    private Order order;
    private Integer createdItemId;

    public AddToOrderResponseDto() {
    }

    public AddToOrderResponseDto(Order order, Integer createdItemId) {
        this.order = order;
        this.createdItemId = createdItemId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getCreatedItemId() {
        return createdItemId;
    }

    public void setCreatedItemId(Integer createdItemId) {
        this.createdItemId = createdItemId;
    }
}
