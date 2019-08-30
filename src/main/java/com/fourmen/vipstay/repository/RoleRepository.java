package com.fourmen.vipstay.repository;

import com.fourmen.vipstay.model.Role;
import com.fourmen.vipstay.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", maxAge = 3600)
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(RoleName roleName);
}
