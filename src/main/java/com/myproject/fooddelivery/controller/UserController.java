package com.myproject.fooddelivery.controller;

import com.myproject.fooddelivery.Entity.users;
import com.myproject.fooddelivery.Entity.roles;
import com.myproject.fooddelivery.Interface.UserInterface;
import com.myproject.fooddelivery.payloads.ResponseData;
import com.myproject.fooddelivery.service.UserService;
import com.myproject.fooddelivery.utils.JwtUtilsHelper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserInterface userInterface;
    @Autowired
    JwtUtilsHelper jwtUtilsHelper;
    @GetMapping("/all")
    public ResponseEntity<?> getAllusers()
    {
        return new  ResponseEntity<>(userInterface.getAllusers(),HttpStatus.OK );
    }
    @PostMapping("/signin")
    public ResponseEntity<?> check_login(@RequestParam String username,@RequestParam String password)
    {
        System.out.println(username+password);
        ResponseData responseData = new ResponseData();
        boolean check_login = userInterface.checkLogin(username,password);
        if(check_login)
        {
            String token = jwtUtilsHelper.generateJwt(username);
            responseData.setData(token);
        }else{
            responseData.setSuccess(false);
            responseData.setData("");
        }
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }
    @PostMapping("/sign_up/{role_id}")
    public ResponseEntity<?> sign_up(@PathVariable("role_id") int role_id, @RequestBody users user)
    {
        roles role = new roles();
        role.setId(role_id);
        //
        user.setRoles(role);
        ResponseData responseData = new ResponseData();
        if(userInterface.add_user(user))
        {
            responseData.setData(true);
        }else {
            responseData.setData(false);
        }
        return new ResponseEntity<>(responseData,HttpStatus.OK);

    }

}
