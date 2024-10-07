package com.myproject.fooddelivery.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Data
@Entity
@Table(name="restaurant")
public class restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title ;
    private String subtile;
    private String description ;
    private String image ;
    private boolean is_freeship ;
    private String address ;
    private Date open_date;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<rating_restaurant> ratingRestaurants = new ArrayList<>();
}
