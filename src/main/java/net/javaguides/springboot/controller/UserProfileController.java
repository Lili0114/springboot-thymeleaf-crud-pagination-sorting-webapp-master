package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.model.UserBook;
import net.javaguides.springboot.service.UserBookService;
import net.javaguides.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserProfileController {

    private final UserService userService;
    private final UserBookService userBookService;

    @Autowired
    public UserProfileController(UserService userService, UserBookService userBookService) {
        this.userService = userService;
        this.userBookService = userBookService;
    }

    @GetMapping("/showProfile/{username}")
    public String showProfile(@PathVariable String username, Model model) {
        User user = userService.findUserByUsername(username);

        if (user != null && username != null){
            model.addAttribute("user", user);
        }
        else{
            model.addAttribute("user", "");
        }

        List<UserBook> userBooks = userBookService.getUserBooksByUserId(user.getId());
        model.addAttribute("userBooks", userBooks);
        return "profile";
    }

}

