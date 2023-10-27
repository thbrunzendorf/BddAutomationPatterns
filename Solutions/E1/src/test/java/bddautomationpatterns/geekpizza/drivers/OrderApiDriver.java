package bddautomationpatterns.geekpizza.drivers;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Component;
import io.cucumber.spring.ScenarioScope;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import bddautomationpatterns.geekpizza.dto.*;
import bddautomationpatterns.geekpizza.support.*;
import bddautomationpatterns.geekpizza.model.*;

@Component
@ScenarioScope
public class OrderApiDriver {

    @Autowired
    protected WebApiContext webApiContext;

    private AddToOrderResponseDto addToOrderResponse;
    private String responseMessage;

    public String responseMessage() {
        return responseMessage;
    }

    public Order getMyOrder(){
        return webApiContext.executeGet("/api/order", Order.class);
    }

    public boolean attemptAddItemToOrder(AddToOrderRequestDto addToOrderRequestDto){
        WebApiResponse<AddToOrderResponseDto> response = webApiContext.executePost("/api/order/items", addToOrderRequestDto, AddToOrderResponseDto.class);
        responseMessage = response.responseMessage();
        addToOrderResponse = response.responseBody();
        return response.httpStatus() == HttpStatus.OK;
    }

    public AddToOrderResponseDto addItemToOrder(String name, OrderItemSize size){
        return addItemToOrder(new AddToOrderRequestDto(name, size));
    }

    public AddToOrderResponseDto addItemToOrder(AddToOrderRequestDto addToOrderRequestDto) {
        boolean result = attemptAddItemToOrder(addToOrderRequestDto);
        assertTrue(result, String.format("The request should have been successful, but got '%s'", responseMessage()));
        return addToOrderResponse;
    }

    public boolean attemptUpdateOrderItem(int itemId, UpdateOrderItemRequestDto updateOrderItemRequestDto){
        StatusOnlyWebApiResponse response = webApiContext.executePut("/api/order/items/" + itemId, updateOrderItemRequestDto);
        responseMessage = response.responseMessage();
        return response.httpStatus() == HttpStatus.OK;
    }

    public void updateOrderItem(int itemId, UpdateOrderItemRequestDto updateOrderItemRequestDto){
        boolean result = attemptUpdateOrderItem(itemId, updateOrderItemRequestDto);
        assertTrue(result, String.format("The request should have been successful, but got '%s'", responseMessage()));
    }

    public boolean attemptUpdateOrderDetails(OrderDetailsPageModelDto pageModelDto){
        StatusOnlyWebApiResponse response = webApiContext.executePut("/api/order", pageModelDto);
        responseMessage = response.responseMessage();
        return response.httpStatus() == HttpStatus.OK;
    }

    public void updateOrderDetails(LocalDate date, LocalTime time){
        OrderDetailsPageModelDto pageModelDto = new OrderDetailsPageModelDto();
        pageModelDto.setDeliveryDate(date);
        pageModelDto.setDeliveryTime(time);
        boolean result = attemptUpdateOrderDetails(pageModelDto);
        assertTrue(result, String.format("The request should have been successful, but got '%s'", responseMessage()));
    }
}
