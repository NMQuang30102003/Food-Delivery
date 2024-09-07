package com.myproject.fooddelivery.payloads;

import lombok.Data;

/*
{
    status:,
    data:{},
    description:
}
*/
@Data
public class ResponseData {
    private int status = 200;
    private boolean isSuccess = true;
    private String description;
    private Object data;
}
