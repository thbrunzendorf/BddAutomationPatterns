package bddautomationpatterns.geekpizza.drivers;

import java.util.Map;

import org.springframework.stereotype.Component;
import io.cucumber.spring.ScenarioScope;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import bddautomationpatterns.geekpizza.dto.*;
import bddautomationpatterns.geekpizza.support.*;
import bddautomationpatterns.geekpizza.model.*;

@Component
@ScenarioScope
public class OrderApiDriver {

    @Autowired
    protected WebApiContext webApiContext;

    private final ActionAttempt<AddToOrderRequestDto, AddToOrderResponseDto> addItemToOrderAction = WebApiActionAttempt.create(
            request -> webApiContext.executePost("/api/order/items", request, AddToOrderResponseDto.class), HttpStatus.OK);

    private final CommandActionAttempt<Map.Entry<Integer, UpdateOrderItemRequestDto>> updateOrderItemAction = WebApiActionAttempt.createCommand(
            request -> webApiContext.executePut("/api/order/items/" + request.getKey(), request.getValue()), HttpStatus.OK);

    private final CommandActionAttempt<OrderDetailsPageModelDto> updateOrderDetailsAction = WebApiActionAttempt.createCommand(
            request -> webApiContext.executePut("/api/order", request), HttpStatus.OK);

    private final CommandActionAttempt<Object> placeOrderAction = WebApiActionAttempt.createCommand(
            request -> webApiContext.executePost("/api/order", null), HttpStatus.OK);

    public ActionAttempt<AddToOrderRequestDto, AddToOrderResponseDto> addItemToOrder() {
        return addItemToOrderAction;
    }

    public CommandActionAttempt<Map.Entry<Integer, UpdateOrderItemRequestDto>> updateOrderItem() {
        return updateOrderItemAction;
    }

    public CommandActionAttempt<OrderDetailsPageModelDto> updateOrderDetails() {
        return updateOrderDetailsAction;
    }

    public CommandActionAttempt<Object> placeOrder() {
        return placeOrderAction;
    }

    public Order getMyOrder(){
        return webApiContext.executeGet("/api/order", Order.class);
    }
}
