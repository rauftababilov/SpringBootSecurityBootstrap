package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private UserService userService;

    @GetMapping("/usersList")
    public String usersList(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("listUsers", userService.findAll());
        model.addAttribute("allRoles", userService.findAllRoles());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PostMapping("/createUser")
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/usersList";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam(value = "idEdit", required = false) Long id,
                           @RequestParam(value = "firstNameEdit", required = false) String firstName,
                           @RequestParam(value = "lastNameEdit", required = false) String lastName,
                           @RequestParam(value = "ageEdit", required = false) int age,
                           @RequestParam(value = "emailEdit", required = false) String email,
                           @RequestParam(value = "passwordEdit", required = false) String password,
                           @RequestParam(value = "editRole", required = false) String role,
                           Model model) {

        model.addAttribute("allRoles", userService.findAllRoles());
        User user = userService.findById(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setEmail(email);

        if (password == null){
            user.setPassword(user.getPassword());
        } else {
            user.setPassword(password);
        }

        Set<Role> roleSet = new HashSet<>();
        if (role.contains("USER")){
            roleSet.add(new Role("USER"));
            user.setRoles(roleSet);
        }
        if (role.contains("ADMIN")) {
            roleSet.add(new Role("ADMIN"));
            user.setRoles(roleSet);
        }
        userService.editUser(user);
        return "redirect:/admin/usersList";
    }

    @PostMapping("/delete")
    public String deleteUserById(@RequestParam("idDelete") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/usersList";
    }
}


