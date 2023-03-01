package com.fbscolorado.documentworkflow.controllers;

import com.fbscolorado.documentworkflow.entities.User;
import com.fbscolorado.documentworkflow.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("users")
    public List<User> users() {
        return userService.findAllUser();
    }

    @GetMapping("users/email")
    public User getUserByEmail(Principal principal) {
        return userService.findUserByUsername(principal.getName());
    }

    @GetMapping("users/{id}")
    public User showUser(Principal principal, @RequestBody User user, @PathVariable Integer id,
                         HttpServletResponse res) {
        try {
            if (principal.getName().equals(user.getEmail())) {
                return userService.updateUser(principal.getName(), id, user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(400);
            user = null;
        }
        return null;
    }
}
