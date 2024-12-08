package bddautomationpatterns.geekpizza.support;

import bddautomationpatterns.geekpizza.model.OrderItem;
import bddautomationpatterns.geekpizza.model.OrderItemSize;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;

import java.util.Map;

public class TypeRegistryConfiguration {

    @ParameterType("\\w+")
    public OrderItemSize orderItemSize(String s) {
        return OrderItemSize.valueOf(OrderItemSize.class, s);
    }

    @DataTableType
    public OrderItem orderItemEntry(Map<String, String> entry) {
        String name = entry.getOrDefault("name", DomainDefaults.menuItemName);
        String size = entry.getOrDefault("size", DomainDefaults.orderItemSize.name());
        boolean extraCheese = Boolean.parseBoolean(entry.getOrDefault("extraCheese", Boolean.FALSE.toString()));
        OrderItemSize itemSize = OrderItemSize.valueOf(OrderItemSize.class, size);
        OrderItem item = new OrderItem(name, itemSize);
        item.setExtraCheese(extraCheese);
        return item;
    }
}
