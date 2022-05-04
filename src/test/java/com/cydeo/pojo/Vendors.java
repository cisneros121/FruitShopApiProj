package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Vendors {
    private Meta meta;
    private List<VendorList> vendors;

}
