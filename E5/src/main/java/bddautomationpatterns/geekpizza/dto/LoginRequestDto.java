package bddautomationpatterns.geekpizza.dto;

public class LoginRequestDto {
    private String name;
    private String password;

    public LoginRequestDto() {
    }

    public LoginRequestDto(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
