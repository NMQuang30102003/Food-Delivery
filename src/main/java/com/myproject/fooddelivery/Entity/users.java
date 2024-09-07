package com.myproject.fooddelivery.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String passwords;
    private String fullname;
    @Column(name="create_date")
    private Date create_date;

    @ManyToOne()
    @JoinColumn(name="role_id")
    private roles roles;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    Set<rating_restaurant> ratingRestaurantSet = new HashSet<>();

}
