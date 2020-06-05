package com.nevdev.witcher.repository;

import com.nevdev.witcher.core.Authority;
import com.nevdev.witcher.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByRoleName(Role roleName);
}
