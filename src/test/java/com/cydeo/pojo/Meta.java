package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {
    private int count;
    private int limit;
    private int page;

    @JsonProperty("next_url")
    private String nextUrl;
}
