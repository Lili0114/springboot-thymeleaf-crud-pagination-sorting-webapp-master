package net.javaguides.springboot.controller;

import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Console;
import java.util.Objects;

@Controller
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String loginReg() {
        return "login_register";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }
    @PostMapping("/register/save")
    public String registerUser(@RequestParam(name = "username") String username, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        User user = new User(username, email, passwordEncoder.encode(password), "user");
        System.out.println(passwordEncoder.encode(password));
        System.out.println(password);
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/login/save")
    public String userLogin(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, Model model) {
        User user = userService.findUserByUsername(username);
        passwordEncoder.upgradeEncoding(password);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return "redirect:/index_user";
        } else {
            model.addAttribute("error", "Invalid username or password");
            //System.out.println("Név: " + user.getUsername() + ", jelszó: " + user.getPassword());
            //System.out.println(passwordEncoder.matches(password, user.getPassword()));
            return "redirect:/";
        }
    }

    /*@PostMapping("/admin-login")
    public String adminLogin(@RequestParam(name = "admin-username") String username, @RequestParam(name = "admin-password") String password, Model model) {
        User user = userService.loadUserByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword()) && user.getRole().equals("admin")) {
            return "redirect:/admin-home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "redirect:/admin-login";
        }
    }*/
}
