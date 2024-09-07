package com.myproject.fooddelivery.controller;

import DTO.RestaurantDTO;
import com.myproject.fooddelivery.Entity.restaurant;
import com.myproject.fooddelivery.Entity.rating_restaurant;
import com.myproject.fooddelivery.Interface.FileStorageInterface;
import com.myproject.fooddelivery.Interface.RestaurantInterface;
import com.myproject.fooddelivery.payloads.ResponseData;
import com.myproject.fooddelivery.repository.RatingResRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    @Autowired
    private FileStorageInterface fileStorageInterface;
    @Autowired
    private RestaurantInterface restaurantInterface;

    @Autowired
    private RatingResRepo ratingResRepo;
    @PostMapping("/upload")
    public ResponseEntity<ResponseData> create_restaurant(@RequestParam("file") MultipartFile file,@RequestParam String title ,@RequestParam String subtitle,
                                                          @RequestParam String description,@RequestParam boolean is_freeship,@RequestParam String address,@RequestParam String open_date)
    {
        ResponseData responseData = new ResponseData();
        String message = "";
        restaurant restaurant = new restaurant();
        restaurant.setTitle(title);
        restaurant.setSubtile(subtitle);
        restaurant.setDescription(description);
        restaurant.setImage(file.getOriginalFilename());
        restaurant.set_freeship(is_freeship);
        restaurant.setAddress(address);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        try{
            restaurant.setOpen_date(formatter.parse(open_date));
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        try{
            restaurantInterface.insert_restaurant(restaurant);
            fileStorageInterface.save(file);
            message = "Create Restaurant success !";
            responseData.setDescription(message);
        }catch (Exception e)
        {
            message = "Create Restaurant fail : "+e.getLocalizedMessage();
            responseData.setDescription(message);
            responseData.setSuccess(false);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ResponseData> get_restaurant()
    {
        ResponseData responseData = new ResponseData();
        String message="";
        try
        {
            List<RestaurantDTO> restaurantDTOS = restaurantInterface.get_restaurant();
            responseData.setData(restaurantDTOS);
            message = "Get Restaurant success !";
            responseData.setDescription(message);
        }catch(Exception e){
            message = "Get Restaurant fail : "+e.getLocalizedMessage();
            responseData.setDescription(message);
            responseData.setSuccess(false);
        }
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileStorageInterface.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/rating")
    public ResponseEntity<?> rating_restaurant(@RequestBody rating_restaurant ratingRestaurant)
    {
        ResponseData responseData = new ResponseData();
        try{
            restaurant restaurant = new restaurant();
            restaurant.setId(2);

            ratingRestaurant.setRestaurant(restaurant);
            ratingResRepo.save(ratingRestaurant);

        }catch (Exception e)
        {
            System.out.println(e.getLocalizedMessage());
        }
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }
    @CacheEvict(value="restaurant_home",allEntries = true)
    @GetMapping("/clear-cache")
    public String clear_cache()
    {
        return "Clear Cache success!";
    }
}
