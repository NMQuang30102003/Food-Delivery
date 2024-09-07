package com.myproject.fooddelivery.Interface;

import DTO.RestaurantDTO;
import com.myproject.fooddelivery.Entity.restaurant;
import com.myproject.fooddelivery.Entity.rating_restaurant;
import java.util.List;

public interface RestaurantInterface {
    public boolean insert_restaurant(restaurant restaurant);
    public List<RestaurantDTO> get_restaurant();
    public boolean insert_rating_restaurant(rating_restaurant ratingRestaurant);
}
