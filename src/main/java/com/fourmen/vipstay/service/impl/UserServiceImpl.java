package com.fourmen.vipstay.service.impl;

import com.fourmen.vipstay.model.User;
import com.fourmen.vipstay.repository.UserRepository;
import com.fourmen.vipstay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void createHouse(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateHouse(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteHouse(Long id) {
        userRepository.deleteById(id);
    }
}
