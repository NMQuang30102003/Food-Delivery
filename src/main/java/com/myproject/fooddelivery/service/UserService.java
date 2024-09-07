package com.myproject.fooddelivery.service;

import DTO.UserDTO;
import com.myproject.fooddelivery.Entity.users;
import com.myproject.fooddelivery.Interface.UserInterface;
import com.myproject.fooddelivery.repository.UserRepository;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserInterface {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public List<users> getAllusers()
    {
        return userRepository.findAll();
    }

//    @Override
//    public boolean checkLogin(String username, String passwords) {
//        return userRepository.findUsersByUsernameAndPasswords(username,passwords).size() > 0;
//    }

    //checklogin với mật khẩu trong sql đã được mã hóa
    @Override
    public boolean checkLogin(String username, String passwords) {
        users user = userRepository.findByUsername(username);
        return passwordEncoder.matches(passwords,user.getPasswords());
    }

    @Override
    public boolean add_user(users user) {
        try{
            userRepository.save(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
