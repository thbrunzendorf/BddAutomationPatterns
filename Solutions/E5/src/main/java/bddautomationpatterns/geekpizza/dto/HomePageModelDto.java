package bddautomationpatterns.geekpizza.dto;

public class HomePageModelDto {
    private String welcomeMessage;
    private String userName;

    public void setWelcomeMessage(String welcomeMessage) { this.welcomeMessage = welcomeMessage; }
    public void setUserName( String userName) { this.userName = userName; }

    public String getWelcomeMessage() { return welcomeMessage; }
    public String getUserName() { return userName; }
}
