package com.example.shoppingcomparison.controller;

import com.example.shoppingcomparison.auth.ApplicationUserService;
import com.example.shoppingcomparison.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserValidator userValidator;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public UserController(UserValidator userValidator, ApplicationUserService applicationUserService) {
        this.userValidator = userValidator;
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String signIn(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        applicationUserService.validatePassword(username, password);
        return "index";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm,
                               BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        applicationUserService.save(userForm);
        return "redirect:/";
    }

    //todo: implement this
    @GetMapping("/addToFavorites")
    public String addToFavorites() {
        return "index";
    }
}