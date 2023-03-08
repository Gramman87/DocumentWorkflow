package com.fbscolorado.documentworkflow.services;

import com.fbscolorado.documentworkflow.entities.User;
import com.fbscolorado.documentworkflow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public User register(User user, String email) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRole("user");
        user.setEmail(email);
        userRepo.saveAndFlush(user);
        return user;
    }

    @Override
    public User findUserByName(String username) {
        return userRepo.findByUsername(username);
    }
}
