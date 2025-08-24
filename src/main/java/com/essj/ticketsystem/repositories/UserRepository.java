package com.essj.ticketsystem.repositories;

import com.essj.ticketsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
