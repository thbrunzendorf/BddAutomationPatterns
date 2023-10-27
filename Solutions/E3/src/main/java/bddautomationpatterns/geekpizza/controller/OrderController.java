package bddautomationpatterns.geekpizza.controller;

import bddautomationpatterns.geekpizza.dto.AddToOrderRequestDto;
import bddautomationpatterns.geekpizza.dto.AddToOrderResponseDto;
import bddautomationpatterns.geekpizza.dto.OrderDetailsPageModelDto;
import bddautomationpatterns.geekpizza.dto.UpdateOrderItemRequestDto;
import bddautomationpatterns.geekpizza.model.MenuItem;
import bddautomationpatterns.geekpizza.model.Order;
import bddautomationpatterns.geekpizza.model.OrderItem;
import bddautomationpatterns.geekpizza.repository.GeekPizzaRepository;
import bddautomationpatterns.geekpizza.service.AuthenticationService;
import bddautomationpatterns.geekpizza.service.PriceCalculatorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OrderController {

    private final PriceCalculatorService priceCalculatorService;

    public OrderController() {
        this(new PriceCalculatorService());
    }

    public OrderController(PriceCalculatorService priceCalculatorService) {
        this.priceCalculatorService = priceCalculatorService;
    }

    @GetMapping(value = "/api/order")
    public Order getMyOrder(String token, HttpServletRequest request){
        String userName = AuthenticationService.ensureAuthenticated(request, token);

        GeekPizzaRepository repository = new GeekPizzaRepository();
        return repository.getMyOrder(userName);
    }

    @PutMapping(value = "/api/order")
    public Order updateOrderDetails(@RequestBody OrderDetailsPageModelDto orderUpdates, String token, HttpServletRequest request) {
        String userName = AuthenticationService.ensureAuthenticated(request, token);

        GeekPizzaRepository repository = new GeekPizzaRepository();
        Order myOrder = repository.getMyOrder(userName);
        if (orderUpdates.getDeliveryStreetAddress() != null)
            myOrder.getDeliveryAddress().setStreetAddress(orderUpdates.getDeliveryStreetAddress());
        if (orderUpdates.getDeliveryCity() != null)
            myOrder.getDeliveryAddress().setCity(orderUpdates.getDeliveryCity());
        if (orderUpdates.getDeliveryZip() != null)
            myOrder.getDeliveryAddress().setZip(orderUpdates.getDeliveryZip());
        if (orderUpdates.getDeliveryDate().isPresent())
            myOrder.setDeliveryDate(orderUpdates.getDeliveryDate().get());
        if (orderUpdates.getDeliveryTime().isPresent())
            myOrder.setDeliveryTime(orderUpdates.getDeliveryTime().get());
        repository.saveChanges();

        return myOrder;
    }

    @PostMapping(value = "/api/order") // ideally PATCH, but RestTemplate does not support it, see https://stackoverflow.com/questions/41368762/resttemplate-and-patch-invalid
    public void placeOrder(String token, HttpServletRequest request) {
        String userName = AuthenticationService.ensureAuthenticated(request, token);

        // we do not place an order for real, but just clear the current order
        GeekPizzaRepository repository = new GeekPizzaRepository();
        Order myOrder = repository.getMyOrder(userName);
        if (myOrder.getOrderItems().size() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The basket is empty");

        repository.deleteMyOrder(userName);
        repository.saveChanges();
    }

    @PostMapping(value = "/api/order/items")
    public AddToOrderResponseDto addItemToOrder(@RequestBody AddToOrderRequestDto requestDto, String token, HttpServletRequest request) {
        String userName = AuthenticationService.ensureAuthenticated(request, token);

        GeekPizzaRepository repository = new GeekPizzaRepository();
        MenuItem menuItem = repository.findMenuItemByName(requestDto.getMenuItemName());
        if (menuItem == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "menu item not found");
        Order myOrder = repository.getMyOrder(userName);
        OrderItem orderItem = new OrderItem(menuItem.getName(), requestDto.getSize());
        myOrder.addOrderItem(orderItem);
        priceCalculatorService.updatePrice(myOrder);
        repository.saveChanges();
        return new AddToOrderResponseDto(myOrder, orderItem.getId());
    }

    @PutMapping(value = "/api/order/items/{id}")
    public Order updateOrderItem(@PathVariable int id, @RequestBody UpdateOrderItemRequestDto orderItemUpdates, String token, HttpServletRequest request) {
        String userName = AuthenticationService.ensureAuthenticated(request, token);

        GeekPizzaRepository repository = new GeekPizzaRepository();
        Order myOrder = repository.getMyOrder(userName);
        OrderItem orderItem = myOrder.getOrderItemById(id);
        if (orderItem == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order item not found");

        if (orderItemUpdates.getSize() != null)
            orderItem.setSize(orderItemUpdates.getSize());
        if (orderItemUpdates.getExtraCheese() != null)
            orderItem.setExtraCheese(orderItemUpdates.getExtraCheese());

        priceCalculatorService.updatePrice(myOrder);

        repository.saveChanges();

        return myOrder;
    }

    @DeleteMapping(value = "/api/order/items/{id}")
    public Order removeOrderItem(@PathVariable int id, String token, HttpServletRequest request) {
        String userName = AuthenticationService.ensureAuthenticated(request, token);

        GeekPizzaRepository repository = new GeekPizzaRepository();
        Order myOrder = repository.getMyOrder(userName);
        OrderItem orderItem = myOrder.getOrderItemById(id);
        if (orderItem == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order item not found");

        myOrder.removeOrderItem(orderItem);

        repository.saveChanges();

        return myOrder;
    }
}
