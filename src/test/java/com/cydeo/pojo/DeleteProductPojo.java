package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteProductPojo {
    private String name;
    @JsonProperty("product_url")
    private String productUrl;

}
