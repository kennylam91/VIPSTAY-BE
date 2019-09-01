package com.fourmen.vipstay.service;

import com.fourmen.vipstay.model.User;

public interface UserService {
    User findByUserName(String username);
    User findUserById(long id);
}
