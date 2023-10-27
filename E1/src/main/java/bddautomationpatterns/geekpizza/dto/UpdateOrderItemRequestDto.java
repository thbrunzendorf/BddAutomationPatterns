package bddautomationpatterns.geekpizza.dto;

import bddautomationpatterns.geekpizza.model.OrderItemSize;

public class UpdateOrderItemRequestDto {
    private OrderItemSize size;
    private Boolean extraCheese;

    public UpdateOrderItemRequestDto() {
    }

    public UpdateOrderItemRequestDto(OrderItemSize size) {
        this.size = size;
    }

    public OrderItemSize getSize() {
        return size;
    }

    public void setSize(OrderItemSize size) {
        this.size = size;
    }

    public Boolean getExtraCheese() {
        return extraCheese;
    }

    public void setExtraCheese(Boolean extraCheese) {
        this.extraCheese = extraCheese;
    }
}
