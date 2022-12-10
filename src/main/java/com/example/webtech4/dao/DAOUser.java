package com.example.webtech4.dao;

import com.example.webtech4.pojo.User;

import java.util.List;
import java.util.Optional;

public interface DAOUser {
    Optional<User> findBoookingById(long id);
    List<User> findAllUsers();
    void saveUser(User user);
    void deleteUser(User user);
}
