package com.myproject.fooddelivery.repository;

import com.myproject.fooddelivery.Entity.roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<roles,Integer> {
}
