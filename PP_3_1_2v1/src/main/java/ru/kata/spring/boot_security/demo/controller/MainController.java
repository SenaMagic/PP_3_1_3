package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class MainController {

    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/user")
    public String userPage() {
        return "user";
    }
    @GetMapping(value = "/admin")
    public String adminPage () {
        return "admin";
    }
    @GetMapping(value = "/admin/userlist")
    public String userList(ModelMap model) {
        model.addAttribute("result", userService.listUsers());
        return "userlist";
    }
    @GetMapping("/admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }
    @PostMapping("/admin/adduser")
    public String addUser(@ModelAttribute("user") @Valid User user) {
        if (user.getRoles() == null) {
            Set<Role> baseRole = new HashSet<>();
            baseRole.add(roleService.getRoleById((long) 1));
            user.setRoles(baseRole);
        }
        userService.addUser(user);
        return "adduser";
    }
    @GetMapping("/admin/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }
    @PatchMapping("/admin/{id}/edited")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.updateUser(id, user);
        return "edituser";
    }
    @DeleteMapping("/admin/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "deleteuser";
    }
}
