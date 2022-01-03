package com.lms.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.user.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
}
