package com.casestudy.backend.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.backend.common.enums.UserType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByUserType(UserType userType);

    boolean existsByUserType(UserType userType);
}