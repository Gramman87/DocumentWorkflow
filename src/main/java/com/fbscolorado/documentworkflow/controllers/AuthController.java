package com.fbscolorado.documentworkflow.controllers;

import com.fbscolorado.documentworkflow.entities.User;
import com.fbscolorado.documentworkflow.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin({"*", "http://localhost:4300"})
public class AuthController {
    @Autowired
    private AuthService authSvc;

    @PostMapping("/register")
    public User register(@RequestBody User user, HttpServletResponse res) {
        if (user == null) {
            res.setStatus(400);
        }
        assert user != null;
        user = authSvc.register(user, user.getEmail());
        return user;
    }

    @GetMapping("/login")
    public User authenticate(Principal principal) {
        return authSvc.findUserByName(principal.getName());
    }
}
