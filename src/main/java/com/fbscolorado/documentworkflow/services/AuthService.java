package com.fbscolorado.documentworkflow.services;

import com.fbscolorado.documentworkflow.entities.User;

public interface AuthService {
    User register(User user, String email);

    User findUserByName(String username);
}
