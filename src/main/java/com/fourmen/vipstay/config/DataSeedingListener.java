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
        for (RoleName roleName:RoleName.values()){
            if (roleRepository.findByName(roleName)==null){
                roleRepository.save(new Role(roleName));
            }
        }

        // set default Admin account
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setName("admin");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN));
            admin.setRoles(roles);
            userRepository.save(admin);
        }

        // Owner account
        if (userRepository.findByUsername("owner") == null) {
            User owner = new User();
            owner.setEmail("owner@gmail.com");
            owner.setName("owner");
            owner.setUsername("owner");
            owner.setPassword(passwordEncoder.encode("123456"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(RoleName.ROLE_OWNER));
            owner.setRoles(roles);
            userRepository.save(owner);
        }

        // Guest account
        if (userRepository.findByUsername("guest") == null) {
            User guest = new User();
            guest.setEmail("guest@gmail.com");
            guest.setName("guest");
            guest.setUsername("guest");
            guest.setPassword(passwordEncoder.encode("123456"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(RoleName.ROLE_GUEST));
            guest.setRoles(roles);
            userRepository.save(guest);
        }
    }

}
