package com.myproject.fooddelivery.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String role_name;
    private Date createDate;

    @OneToMany(mappedBy = "roles")
    @JsonIgnore
    Set<users> users = new HashSet<>();
}
