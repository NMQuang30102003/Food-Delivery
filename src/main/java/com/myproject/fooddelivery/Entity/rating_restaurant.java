package com.myproject.fooddelivery.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rating_restaurant")
public class rating_restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;
    private int rate_point;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "res_id")
    private restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private users user;

}
