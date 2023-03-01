package com.fbscolorado.documentworkflow.services;

import com.fbscolorado.documentworkflow.entities.User;
import com.fbscolorado.documentworkflow.entities.Workflow;
import com.fbscolorado.documentworkflow.repositories.UserRepository;
import com.fbscolorado.documentworkflow.repositories.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private WorkflowRepository workflowRepo;

    @Override
    public List<User> findAllUser() {
        return userRepo.findAll();
    }

    @Override
    public User findUserByUsername(String name) {
        return userRepo.findUserByEmail(name);
    }

    @Override
    public User findUserById(int id) {
        Optional<User> optionalUser = userRepo.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public User createUser(User user) {
        return userRepo.saveAndFlush(user);
    }

    @Override
    public User updateUser(String email, int id, User user) {
        Optional<User> optionalUser = userRepo.findById(id);
        User managedUser;
        if (optionalUser.isPresent()) {
            managedUser = optionalUser.get();
            managedUser.setUsername(user.getUsername());
            managedUser.setEmail(user.getEmail());
            managedUser.setPassword(user.getPassword());
            managedUser.setRole(user.getRole());
            userRepo.saveAndFlush(managedUser);
        }
        return user;
    }

    @Override
    public boolean deleteUser(int id) {
        Optional<User> optionalUser = userRepo.findById(id);
        User managedUser;
        if (optionalUser.isPresent()) {
            managedUser = optionalUser.get();
            managedUser.setEnabled(false);
            managedUser.setRole("inActive");
            userRepo.saveAndFlush(managedUser);
            return true;
        }
        return false;
    }

    @Override
    public List<Workflow> findWorkflowsByUserId(Integer id) {
        if (!userRepo.existsById(id)) {
            return null;
        }
        return workflowRepo.findWorkflowsByUsersId(id);
    }
}
