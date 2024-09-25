package com.example.cividleutils.obj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import com.example.cividleutils.lib.prepopo.PrepopoDefaultCallbacks;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class SGBuilding implements PrepopoDefaultCallbacks {

    private int level;

    private int desiredLevel;

//    private String status;

    private String type;

    @JsonProperty
    private Map<String, Double> resources;

//    private boolean completed;

//    private Map<String, Double> resources;

}
