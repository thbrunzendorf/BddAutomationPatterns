package bddautomationpatterns.geekpizza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PagesController {
    @GetMapping({"/Login", "/Menu", "/MyOrder", "/OrderDetails", "/Admin", "/Register"})
    public ModelAndView GetPage() {

        return new ModelAndView();
    }
}
