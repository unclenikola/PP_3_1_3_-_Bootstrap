//package ru.kata.spring.boot_security.demo.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//
//@Controller
//public class UserController {
//
//    private final UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/users")
//    public String listUsers(Model model) {
//        model.addAttribute("users", userService.getAllUsers());
//        return "users";
//    }
//
//    @PostMapping("/add")
//    public String addUser(@RequestParam("name") String name,
//                          @RequestParam("lastName") String lastName,
//                          @RequestParam("age") int age) {
//        User user = new User(name, lastName, age);
//        userService.addUser(user);
//        return "redirect:/users";
//    }
//
//    @GetMapping("/edit")
//    public String editUser(@RequestParam("id") Long id, Model model) {
//        model.addAttribute("user", userService.getUserById(id));
//        return "edit";
//    }
//
//    @PostMapping("/update")
//    public String updateUser(@RequestParam("id") Long id,
//                             @RequestParam("name") String name,
//                             @RequestParam("lastName") String lastName,
//                             @RequestParam("age") int age) {
//        User user = new User(name, lastName, age);
//        user.setId(id);
//        userService.updateUser(user);
//        return "redirect:/users";
//    }
//
//    @PostMapping("/delete")
//    public String deleteUser(@RequestParam("id") Long id) {
//        userService.deleteUser(id);
//        return "redirect:/users";
//    }
//}