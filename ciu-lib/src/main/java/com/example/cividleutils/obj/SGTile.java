package com.example.cividleutils.obj;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
@ToString
public class SGTile {

    @JsonProperty
    private int tile;

    @JsonProperty
    private boolean explored;

    @JsonProperty
    private SGBuilding building;

    @JsonIgnore
    public Optional<SGBuilding> getBuildingOpt() {
	return Optional.ofNullable(building);
    }

    @JsonIgnore
    public Optional<String> getBuildingTypeOpt() {
	if (building != null) {
	    return Optional.ofNullable(building.getType());
	}
	return Optional.empty();
    }

    public double getResource(String name, double defVal) {
	if (building == null || building.getResources() == null) {
	    return defVal;
	}
	return getBuilding().getResources().getOrDefault(name, defVal);
    }

    @JsonIgnore
    public boolean isHq() {
	return "Headquarter".equals(getBuildingTypeOpt().orElse(null));
    }

}
