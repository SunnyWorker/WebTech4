package com.example.webtech4.dao;

import com.example.webtech4.pojo.User;

import java.util.List;
import java.util.Optional;

public interface DAOUser {
    User findBoookingById(long id);
    User findBoookingByEmail(String email);
    List<User> findAllUsers();
    void saveUser(User user);
    void deleteUser(User user);
}
