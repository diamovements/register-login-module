package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.UserDTO;
import com.example.registrationlogindemo.model.User;
import com.example.registrationlogindemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegForm(Model model) {
        UserDTO u = new UserDTO();
        model.addAttribute("user", u);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDTO,
                               BindingResult bindingResult, Model model) {
        User exist = userService.findUserByEmail(userDTO.getEmail());
        if (exist != null && exist.getEmail() != null && !exist.getEmail().isEmpty()) {
            bindingResult.rejectValue("email", null, "There is already someone registered with this email");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "/register";
        }
        userService.saveUser(userDTO);
        return "redirect:/register?success";
    }
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDTO> us = userService.findAll();
        model.addAttribute("users", us);
        return "users";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
