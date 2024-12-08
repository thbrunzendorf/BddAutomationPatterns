package bddautomationpatterns.geekpizza.model;

public class MenuItem {
    private static int idGenerator = 0;

    private Integer id;
    private String name;
    private String ingredients;
    private Integer calories;
    private boolean isInactive;

    public MenuItem() {
    }

    public MenuItem(String name, String ingredients, Integer calories)  {
        this(name, ingredients, calories, false);
    }

    public MenuItem(String name, String ingredients, Integer calories, boolean isInactive) {
        this.name = name;
        this.ingredients = ingredients;
        this.calories = calories;
        this.isInactive = isInactive;
        ensureId();
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public Integer getCalories() {
        return calories;
    }

    public boolean getIsInactive() { return isInactive; }

    public Integer getId() {
        return id;
    }

    public void ensureId(){
        if (id == null)
            id = ++idGenerator;
    }
}
