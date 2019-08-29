package com.fourmen.vipstay.service;

import com.fourmen.vipstay.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    void createHouse(User user);

    void updateHouse(User user);

    void deleteHouse(Long id);
}
