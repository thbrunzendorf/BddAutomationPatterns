package bddautomationpatterns.geekpizza.support;

import bddautomationpatterns.geekpizza.dto.RegisterRequestDto;
import bddautomationpatterns.geekpizza.model.OrderItem;
import bddautomationpatterns.geekpizza.model.OrderItemSize;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @ParameterType("\\d\\d?\\:\\d\\d")
    public LocalTime time(String s){
        return LocalTime.parse(s);
    }

    @ParameterType(".*")
    public LocalDate date(String s){
        return parseDate(s);
    }

    private LocalDate parseDate(String s){
        if (s.equalsIgnoreCase("today"))
            return LocalDate.now();
        if (s.equalsIgnoreCase("tomorrow"))
            return LocalDate.now().plusDays(1);

        Pattern daysLater = Pattern.compile("(?<days>\\d+) days later");
        Matcher matcher = daysLater.matcher(s);
        if (matcher.find())
            return LocalDate.now().plusDays(Integer.parseInt(matcher.group("days")));

        return LocalDate.parse(s);
    }

    @DataTableType
    public RegisterRequestDto registerRequestEntry(Map<String, String> entry) {
        return new RegisterRequestDto(
                entry.getOrDefault("user name", "Trillian"),
                entry.getOrDefault("password", "139139"),
                entry.getOrDefault("password re-enter", "139139"));
    }
}
