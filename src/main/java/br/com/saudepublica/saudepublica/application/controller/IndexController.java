package br.com.saudepublica.saudepublica.application.controller;

import br.com.saudepublica.saudepublica.application.util.Session;
import br.com.saudepublica.saudepublica.domain.dto.LoginDto;
import br.com.saudepublica.saudepublica.domain.service.UserService;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class IndexController {

    private UserService userService;

    @RequestMapping("/")
    @CrossOrigin
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("user", Session.getUser());
        return modelAndView;
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        return "/login";
    }

}
