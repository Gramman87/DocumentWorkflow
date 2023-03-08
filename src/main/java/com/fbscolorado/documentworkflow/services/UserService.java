package com.fbscolorado.documentworkflow.services;

import com.fbscolorado.documentworkflow.entities.User;
import com.fbscolorado.documentworkflow.entities.Workflow;

import java.util.List;

public interface UserService {
    public List<User> findAllUser();
    public User findUserByUsername(String username);
    public User findUserById(int id);
    public User createUser(User user);
    public User updateUser(String username, int id, User user);
    public boolean deleteUser(int id);
    public List<Workflow> findWorkflowsByUserId(Integer id);
}
