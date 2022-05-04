package com.cydeo.pojo;

import lombok.Data;

@Data
public class CreatedProduct {
    private String name;
    private int price;
    private String self_url;
    private String category_url;
    private String vendor_url;
}
