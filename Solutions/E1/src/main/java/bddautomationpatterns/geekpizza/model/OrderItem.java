package bddautomationpatterns.geekpizza.model;

public class OrderItem {
    private Integer id;
    private String name;
    private OrderItemSize size;
    private boolean extraCheese = false;

    public OrderItem() { }

    public OrderItem(String name, OrderItemSize size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public OrderItemSize getSize() {
        return size;
    }

    public void setSize(OrderItemSize size) {
        this.size = size;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isExtraCheese() {
        return extraCheese;
    }

    public void setExtraCheese(boolean extraCheese) {
        this.extraCheese = extraCheese;
    }
}
