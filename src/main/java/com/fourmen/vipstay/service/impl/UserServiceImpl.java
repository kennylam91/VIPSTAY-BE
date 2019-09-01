package com.fourmen.vipstay.service.impl;

import com.fourmen.vipstay.model.User;
import com.fourmen.vipstay.repository.UserRepository;
import com.fourmen.vipstay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findUserById(id);
    }
}
