package bddautomationpatterns.geekpizza.dto;

public class RegisterRequestDto {
    private String userName;
    private String password;
    private String passwordReEnter;

    public RegisterRequestDto() {
    }

    public RegisterRequestDto(String userName, String password, String passwordReEnter) {
        this.userName = userName;
        this.password = password;
        this.passwordReEnter = passwordReEnter;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordReEnter() { return passwordReEnter; }
}
