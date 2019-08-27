package com.fourmen.vipstay.config;

import com.fourmen.vipstay.model.Role;
import com.fourmen.vipstay.model.RoleName;
import com.fourmen.vipstay.model.User;
import com.fourmen.vipstay.repository.RoleRepository;
import com.fourmen.vipstay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        // record into database all role name of RoleName enum
        for (RoleName roleName : RoleName.values()) {
            if (roleRepository.findByName(roleName) == null) {
                roleRepository.save(new Role(roleName));
            }
        }

        // set some default user
        String[] userDefaultStr = new String[]{"admin", "owner", "guest"};
        for (String userStr : userDefaultStr) {
            if (userRepository.findByUsername(userStr) == null) {
                User user = new User();
                String email = userStr + "@gmail.com";
                user.setEmail(email);
                user.setName(userStr);
                user.setUsername(userStr);
                user.setPassword(passwordEncoder.encode("123456"));
                Set<Role> roles = new HashSet<>();
                RoleName roleName = userStr.equals("admin") ? RoleName.ROLE_ADMIN :
                        userStr.equals("owner") ? RoleName.ROLE_OWNER : RoleName.ROLE_GUEST;
                roles.add(roleRepository.findByName(roleName));
                user.setRoles(roles);
                userRepository.save(user);
            }
        }
    }

}
