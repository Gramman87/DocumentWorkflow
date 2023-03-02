package com.fbscolorado.documentworkflow.controllers;

import com.fbscolorado.documentworkflow.entities.User;
import com.fbscolorado.documentworkflow.entities.Workflow;
import com.fbscolorado.documentworkflow.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("users/username")
    public User getUserByUsername(Principal principal) {
        return userService.findUserByUsername(principal.getName());
    }

    @GetMapping("users/{id}")
    public User showUser(Principal principal, @RequestBody User user, @PathVariable Integer id, HttpServletResponse res) {
        try {
            if (principal.getName().equals(user.getUsername())) {
                return userService.updateUser(principal.getName(), id, user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(400);
            user = null;
        }
        return user;
    }

    @PostMapping("users")
    public User createUser(@RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
        try {
            userService.createUser(user);
            res.setStatus(201);
            StringBuffer url = req.getRequestURL();
            url.append("/").append(user.getId());
            res.setHeader("Location", url.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("INVALID ENTRY FOR NEW User");
            user = null;
        }
        return user;
    }

    @PutMapping("users/{id}")
    public User updateUser(Principal principal, @PathVariable Integer id, @RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
        try {
            user = userService.updateUser(principal.getName(), id, user);
            if (user == null) {
                res.setStatus(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(400);
            user = null;
        }
        return user;
    }

    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable Integer id, HttpServletResponse res) {
        try {
            if (userService.deleteUser(id)) {
                res.setStatus(204);
            } else {
                res.setStatus(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(400);
        }
    }

    @GetMapping("users/{id}/workflows")
    public List<Workflow> userWorkflows(@PathVariable Integer id, HttpServletResponse res) {
        List<Workflow> workflows = userService.findWorkflowsByUserId(id);
        if (workflows == null) {
            res.setStatus(404);
        }
        return workflows;
    }
    
}
