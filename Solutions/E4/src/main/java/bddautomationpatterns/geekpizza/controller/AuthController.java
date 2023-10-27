package bddautomationpatterns.geekpizza.controller;

import bddautomationpatterns.geekpizza.dto.LoginRequestDto;
import bddautomationpatterns.geekpizza.model.User;
import bddautomationpatterns.geekpizza.repository.GeekPizzaRepository;
import bddautomationpatterns.geekpizza.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping
    public String Login(@RequestBody LoginRequestDto request, HttpServletResponse response){

        GeekPizzaRepository repository = new GeekPizzaRepository();

        User user = repository.findUserByName(request.getName());
        if (user == null || !user.getPassword().equals(request.getPassword()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid user name or password");

        String token = AuthenticationService.setCurrentUser(user.getName());
        if (token == null)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid user name or password");

        AuthenticationService.addAuthCookie(token, response);
        return token;
    }

    @DeleteMapping
    public void Logout(String token, HttpServletRequest request, HttpServletResponse response){
        AuthenticationService.clearLoggedInUser(request, token);
        AuthenticationService.removeAuthCookie(response);
    }
}
