package com.fourmen.vipstay.repository;

import com.fourmen.vipstay.model.Role;
import com.fourmen.vipstay.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(RoleName roleName);
}
