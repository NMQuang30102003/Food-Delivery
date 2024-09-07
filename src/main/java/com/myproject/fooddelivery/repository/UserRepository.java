package com.myproject.fooddelivery.repository;

import com.myproject.fooddelivery.Entity.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<users,Integer> {
    List<users> findUsersByUsernameAndPasswords(String username,String passwords);
    users findByUsername(String username);
}
