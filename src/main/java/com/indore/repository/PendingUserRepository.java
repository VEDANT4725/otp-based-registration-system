package com.indore.repository;

import com.indore.entity.PendingUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingUserRepository extends JpaRepository<PendingUser,Long> {
    PendingUser findByEmail(String email);
}
