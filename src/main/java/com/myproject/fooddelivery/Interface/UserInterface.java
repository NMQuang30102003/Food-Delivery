package com.myproject.fooddelivery.Interface;

import com.myproject.fooddelivery.Entity.users;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserInterface {
    List<users> getAllusers();
    boolean checkLogin(String username,String passwords);
    boolean add_user(users user);
}
