package com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.repository;

import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
}
