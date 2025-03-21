
package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class MainController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPage(@AuthenticationPrincipal User currentUser, Model model) {
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("currentUser", currentUser);
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            String rolesString = user.getRoles().stream()
                    .map(role -> role.getId().toString())
                    .collect(Collectors.joining(","));
            user.setRolesString(rolesString); // Добавляем поле rolesString в модель User
        }
        model.addAttribute("users", users);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin-page";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin-page"; // Возвращаем тот же шаблон, так как у вас один шаблон
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam("roles") List<Long> roleIds) {
        Set<Role> roles = roleIds.stream()
                .map(roleService::findById)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin-page"; // Возвращаем тот же шаблон, так как у вас один шаблон
    }

    @PostMapping("/{id}/edit")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("user") User user,
                             @RequestParam("roles") List<Long> roleIds) {
        Set<Role> roles = roleIds.stream()
                .map(roleService::findById)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        user.setId(id); // Убедитесь, что ID пользователя установлен
        userService.updateUser(user); // Используйте метод updateUser, который принимает User
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}

//package ru.kata.spring.boot_security.demo.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.service.RoleService;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/admin")
//public class MainController {
//
//    private final UserService userService;
//    private final RoleService roleService;
//
//    @Autowired
//    public MainController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    @GetMapping
//    public String adminPage(@AuthenticationPrincipal User currentUser, Model model) {
//        if (currentUser == null) {
//            return "redirect:/login";
//        }
//        model.addAttribute("currentUser", currentUser);
//        List<User> users = userService.getAllUsers();
//        for (User user : users) {
//            String rolesString = user.getRoles().stream()
//                    .map(role -> role.getId().toString())
//                    .collect(Collectors.joining(","));
//            user.setRolesString(rolesString); // Добавляем поле rolesString в модель User
//        }
//        model.addAttribute("users", users);
//        model.addAttribute("allRoles", roleService.getAllRoles());
//        return "admin-page";
//    }
//
//    @GetMapping("/new")
//    public String newUser(Model model) {
//        model.addAttribute("user", new User());
//        model.addAttribute("allRoles", roleService.getAllRoles());
//        return "admin-page"; // Возвращаем тот же шаблон, так как у вас один шаблон
//    }
//
//    @PostMapping("/new")
//    public String createUser(@ModelAttribute("user") User user,
//                             @RequestParam("roles") List<Long> roleIds) {
//        Set<Role> roles = roleIds.stream()
//                .map(roleService::findById)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toSet());
//        user.setRoles(roles);
//        userService.saveUser(user);
//        return "redirect:/admin-psge";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String editUser(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("user", userService.getUserById(id));
//        model.addAttribute("allRoles", roleService.getAllRoles());
//        return "admin-page"; // Возвращаем тот же шаблон, так как у вас один шаблон
//    }
//
//    @PostMapping("/{id}/edit")
//    public String updateUser(@PathVariable("id") Long id,
//                             @ModelAttribute("user") User user,
//                             @RequestParam("roles") List<Long> roleIds) {
//        Set<Role> roles = roleIds.stream()
//                .map(roleService::findById)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toSet());
//        user.setRoles(roles);
//        user.setId(id); // Убедитесь, что ID пользователя установлен
//        userService.updateUser(user); // Используйте метод updateUser, который принимает User
//        return "redirect:/admin-psge";
//    }
//
//    @PostMapping("/{id}/delete")
//    public String deleteUser(@PathVariable("id") Long id) {
//        userService.deleteUser(id);
//        return "redirect:/admin-psge";
//    }
//}

//package ru.kata.spring.boot_security.demo.controllers;

//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.service.RoleService;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/admin")
//public class MainController {
//
//    private final UserService userService;
//    private final RoleService roleService;
//
//    @Autowired
//    public MainController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    // Главная страница
////    @GetMapping
////    public String adminPage(Model model) {
////        model.addAttribute("users", userService.getAllUsers());
////        return "admin-page";
////    }
//    @GetMapping
//    public String adminPage(@AuthenticationPrincipal User currentUser, Model model) {
//        if (currentUser == null) {
//            return "redirect:/login";
//        }
//        model.addAttribute("currentUser", currentUser);
//        List<User> users = userService.getAllUsers();
//        for (User user : users) {
//            String rolesString = user.getRoles().stream()
//                    .map(role -> role.getId().toString())
//                    .collect(Collectors.joining(","));
//            user.setRolesString(rolesString); // Добавляем поле rolesString в модель User
//        }
//        model.addAttribute("users", users);
//        model.addAttribute("allRoles", roleService.getAllRoles());
//        return "admin-page";
//    }
//
//    // Форма создания нового пользователя
//    @GetMapping("/new")
//    public String newUser(Model model) {
//        model.addAttribute("user", new User());
//        model.addAttribute("allRoles", roleService.getAllRoles());
//        return "new-user"; // Имя шаблона для формы создания пользователя
//    }
//
//    // Создание нового пользователя
//    @PostMapping("/new")
//    public String createUser(@ModelAttribute("user") User user,
//                             @RequestParam("roles") List<Long> roleIds) {
//        Set<Role> roles = roleIds.stream()
//                .map(roleService::findById)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toSet());
//        user.setRoles(roles);
//        userService.saveUser(user);
//        return "redirect:/admin";
//    }
//
//    // Форма редактирования пользователя
//    @GetMapping("/{id}/edit")
//    public String editUser(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("user", userService.getUserById(id));
//        model.addAttribute("allRoles", roleService.getAllRoles());
//        return "erdit-use"; // Имя шаблона для формы редактирования пользователя
//    }
//
//    // Обновление пользователя
//    @PostMapping("/{id}/edit")
//    public String updateUser(@PathVariable("id") Long id,
//                             @ModelAttribute("user") User user,
//                             @RequestParam("roles") List<Long> roleIds) {
//        Set<Role> roles = roleIds.stream()
//                .map(roleService::findById)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toSet());
//        user.setRoles(roles);
//        userService.updateUser(id, user);
//        return "redirect:/admin";
//    }
//
//    // Удаление пользователя
//    @PostMapping("/{id}/delete")
//    public String deleteUser(@PathVariable("id") Long id) {
//        userService.deleteUser(id);
//        return "redirect:/admin";
//    }
//}

//package ru.kata.spring.boot_security.demo.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.service.RoleService;
//import ru.kata.spring.boot_security.demo.service.UserService;
//import java.util.stream.Collector;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.Set;
//
//@Controller
//@RequestMapping("/admin")
//public class MainController {
//
//    @GetMapping
//    public String adminPage(@AuthenticationPrincipal User currentUser, Model model) {
//        model.addAttribute("currentUser", currentUser);
//        model.addAttribute("users", List.of(new User("user1", "password")));
//        model.addAttribute("allRoles", List.of(new Role("ROLE_USER")));
//        return "admin-page";
//    }
//}