package DTO;

import lombok.Data;

@Data
public class RestaurantDTO {
    private String title;
    private String subtitle;
    private String image;
    private double rating;
    private boolean is_freeship;
}
