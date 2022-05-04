package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class VendorList {

    @JsonProperty("name")
    private String name;
    @JsonProperty("vendor_url")
    private String vendorUrl;
}
