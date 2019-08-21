package com.fourmen.vipstay.service;

import com.fourmen.vipstay.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User findByUsername(String username);

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);
}
