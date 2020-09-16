package br.com.saudepublica.saudepublica.application.controller;

import br.com.saudepublica.saudepublica.application.util.Session;
import br.com.saudepublica.saudepublica.domain.dto.UserDto;
import br.com.saudepublica.saudepublica.domain.dto.enumerator.TypeUser;
import br.com.saudepublica.saudepublica.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping
    public ModelAndView getUsers() {
        ModelAndView modelAndView = new ModelAndView("user/users-list");
        modelAndView.addObject("users", userService.getAll());
        modelAndView.addObject("userLogged", Session.getUser());
        return modelAndView;
    }

    @RequestMapping("/{id}")
    public ModelAndView getUserById(@PathVariable("id") Long id, UserDto userDto, Boolean outRequest) throws Exception {
        ModelAndView modelAndView = new ModelAndView("user/users-form");
        userDto = outRequest != null && !outRequest ? userDto : userService.getById(id);
        userDto.setPassword(null);
        modelAndView.addObject("user", userDto);
        modelAndView.addObject("typeUser", TypeUser.values());
        modelAndView.addObject("userLogged", Session.getUser());
        return modelAndView;
    }

    @RequestMapping("/new")
    public ModelAndView newUser(UserDto userDto) {
        ModelAndView modelAndView = new ModelAndView("user/users-form");
        modelAndView.addObject("user", userDto != null ? userDto : new UserDto());
        modelAndView.addObject("typeUser", TypeUser.values());
        modelAndView.addObject("userLogged", Session.getUser());
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return new ModelAndView("redirect:/users");
    }

    @PostMapping
    public ModelAndView postUser(@Valid UserDto userDto, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError objectError : result.getAllErrors()) {
                errors.add("- " + objectError.getDefaultMessage());
            }
            userDto.setErrors(errors);
            return returnError(userDto);
        } else {
            userDto.setErrors(null);
        }

        UserDto userSaved = userService.save(userDto);
        if(userSaved.getErrors() != null && !userSaved.getErrors().isEmpty()){
            return returnError(userDto);
        }

        return new ModelAndView("redirect:/users");
    }

    private ModelAndView returnError(UserDto userDto) throws Exception {
        if (userDto.getId() == null) {
            return newUser(userDto);
        } else {
            return getUserById(userDto.getId(), userDto, false);
        }
    }
}
