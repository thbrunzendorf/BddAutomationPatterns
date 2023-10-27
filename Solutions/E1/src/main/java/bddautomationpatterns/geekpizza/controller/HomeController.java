package bddautomationpatterns.geekpizza.controller;

import bddautomationpatterns.geekpizza.dto.HomePageModelDto;
import bddautomationpatterns.geekpizza.service.AuthenticationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    public HomePageModelDto getHomePageModel() {
        return getHomePageModel(null, null);
    }

    @GetMapping
    public HomePageModelDto getHomePageModel(String token, HttpServletRequest request){

        HomePageModelDto model = new HomePageModelDto();
        model.setWelcomeMessage("Welcome to Geek Pizza!");
        model.setUserName(AuthenticationService.getCurrentUserName(request, token));
        return model;
    }
}
