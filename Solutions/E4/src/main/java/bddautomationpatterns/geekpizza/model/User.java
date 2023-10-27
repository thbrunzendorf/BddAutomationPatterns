package bddautomationpatterns.geekpizza.model;

public class User {
    private static int idGenerator = 0;

    private Integer id;
    private String name;
    private String password;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.id = new Integer(++idGenerator);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }
}
