package com.myproject.fooddelivery.service;

import DTO.RestaurantDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myproject.fooddelivery.Entity.rating_restaurant;

import com.myproject.fooddelivery.Entity.restaurant;
import com.myproject.fooddelivery.Entity.roles;
import com.myproject.fooddelivery.Interface.RestaurantInterface;
import com.myproject.fooddelivery.repository.RatingResRepo;
import com.myproject.fooddelivery.repository.RestaurantRepo;
import com.myproject.fooddelivery.repository.RolesRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RestaurantService implements RestaurantInterface {
    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private RatingResRepo ratingResRepo;

    @Autowired
    private RedisTemplate redisTemplate;
    private Gson gson = new Gson();
    @Override
    public boolean insert_restaurant(restaurant restaurant) {
        try{
            restaurantRepo.save(restaurant);
            return true;
        }catch (Exception e)
        {
            System.out.println(e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    @Transactional
//    @Cacheable("restaurant_home")
    public List<RestaurantDTO> get_restaurant() {
        String data_redis = (String) redisTemplate.opsForValue().get("restaurant_home");
        // Tạo một danh sách để chứa các đối tượng DTO của nhà hàng
        List<RestaurantDTO> restaurantDTOS = new ArrayList<>();

        if(data_redis ==  null)
        {
            System.out.println("Chua co data");
            // Tạo một đối tượng PageRequest để phân trang kết quả
            PageRequest pageRequest = PageRequest.of(0, 6, Sort.by("id"));

            // Tìm tất cả các nhà hàng với phân trang
            Page<restaurant> listRestaurants = restaurantRepo.findAll(pageRequest);

            // Duyệt qua tất cả các đối tượng nhà hàng và chuyển đổi sang DTO
            for (restaurant i : listRestaurants) {
                RestaurantDTO restaurantDTO = new RestaurantDTO();
                restaurantDTO.setTitle(i.getTitle());
                restaurantDTO.setSubtitle(i.getSubtile()); // Chú ý: sửa tên phương thức nếu cần
                restaurantDTO.setImage(i.getImage());
                restaurantDTO.set_freeship(i.is_freeship()); // Chú ý: sửa tên phương thức nếu cần
                restaurantDTO.setRating(calculate_rate_point(i.getRatingRestaurants()));

                restaurantDTOS.add(restaurantDTO);
            }
            String data_gson = gson.toJson(restaurantDTOS);
            redisTemplate.opsForValue().set("restaurant_home",data_gson);
        }else{
            System.out.println("Co data");
            Type listType = new TypeToken<List<RestaurantDTO>>() {}.getType();
            restaurantDTOS = gson.fromJson(data_redis,listType);
        }

        return restaurantDTOS;
    }

    @Override
    public boolean insert_rating_restaurant(rating_restaurant ratingRestaurant) {
        try{
            ratingResRepo.save(ratingRestaurant);
            return true;
        }catch (Exception e)
        {
            System.out.println(e.getLocalizedMessage());
        }
        return false;
    }

    private double calculate_rate_point(List<rating_restaurant> list) {
        if (list == null || list.isEmpty()) {
            return 0.0;
        }
        System.out.println(list.size());
        double point = 0.0;
        for (rating_restaurant ratingRestaurant : list) {
            point += ratingRestaurant.getRate_point();
        }
        return point / list.size();
    }

}
