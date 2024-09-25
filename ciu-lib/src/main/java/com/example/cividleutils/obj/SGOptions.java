package com.example.cividleutils.obj;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class SGOptions {

    private String userId;

    private String token;

    private String checksum;
    
    @JsonBackReference
    private SaveGame parent;

}
