package com.tidal.pawpal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tidal.pawpal.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
