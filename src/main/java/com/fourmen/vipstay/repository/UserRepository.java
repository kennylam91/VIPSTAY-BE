package com.fourmen.vipstay.repository;

import com.fourmen.vipstay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findUserById(long id);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
