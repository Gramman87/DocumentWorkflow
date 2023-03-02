package com.fbscolorado.documentworkflow.repositories;

import com.fbscolorado.documentworkflow.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findUserByUsername(String email);

}
