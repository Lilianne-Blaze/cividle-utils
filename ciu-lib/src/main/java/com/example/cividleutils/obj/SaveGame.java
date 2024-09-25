package com.example.cividleutils.obj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import com.example.cividleutils.lib.prepopo.PrepopoDefaultCallbacks;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class SaveGame implements PrepopoDefaultCallbacks {
    
    @JsonManagedReference
    private SGCurrent current;
    
    @JsonManagedReference
    private SGOptions options;

}
