package com.example.cividleutils.obj;

import com.example.cividleutils.defs.CityDef;
import com.example.cividleutils.defs.CityDefs;
import com.example.cividleutils.lib.CivIdleUtils;
import com.example.cividleutils.defs.TechDef;
import com.example.cividleutils.defs.Techs;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import com.example.cividleutils.lib.prepopo.PrepopoDeserContext;
import com.example.cividleutils.lib.prepopo.PrepopoSerContext;
import com.example.cividleutils.lib.prepopo.PrepopoDefaultCallbacks;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Slf4j
public class SGCurrent implements PrepopoDefaultCallbacks {

    private String id;

    private String city;

    private long tick;

    private long seconds;

    private long transportId;

    private long lastPriceUpdated;

    @JsonIgnore
    private List<TechDef> unlockedTechs = new ArrayList<>();

    @JsonIgnore
    private List<String> unlockedUpgradeNames = new ArrayList<>();

    @JsonProperty(value = "isOffline")
    private boolean offline;

    @JsonBackReference
    private SaveGame parent;

    @JsonProperty
    private SGTiles tiles;

    @JsonProperty
    private SGValueTrackers valueTrackers;

    @JsonAnyGetter
    @JsonAnySetter
    private Map<Object, Object> others;

    private List<String> deserUnlockedUpgrades(Map<String, Boolean> map) {
	List<String> l = new ArrayList<>();
	map.forEach((t, u) -> {
	    if (u) {
		l.add(t);
	    }
	});
	return l;
    }

    @Override
    public void prepopoPreSerialize(PrepopoSerContext serContext) {
	log.trace("entering prepopoPreSerialize");

    }

    @Override
    public void prepopoPostSerialize(PrepopoSerContext serContext) {

    }

    @Override
    public void prepopoPostDeserialize(PrepopoDeserContext deserContext) {
	log.trace("entering prepopoPostSerialize");
	decodeUnlockedTech();
	decodeUnlockedUpgrades();

    }

    private void decodeUnlockedTech() {
	unlockedTechs = new ArrayList<>();
	Map<String, Boolean> map = (Map<String, Boolean>) others.get("unlockedTech");
	map.forEach((t, u) -> {
	    if (u) {
		TechDef td = Techs.BY_NAME.get(t);
		unlockedTechs.add(td);
	    }
	});
	map.clear();
    }

    private void decodeUnlockedUpgrades() {
	unlockedUpgradeNames = new ArrayList<>();
	Map<String, Boolean> map = (Map<String, Boolean>) others.get("unlockedUpgrades");
	map.forEach((t, u) -> {
	    if (u) {
		unlockedUpgradeNames.add(t);
	    }
	});
	map.clear();
    }

    @JsonIgnore
    public List<TechDef> getLockedTechs() {
	List<TechDef> retVal = new ArrayList<>();
	for (TechDef td : Techs.LIST) {
	    if (!unlockedTechs.contains(td)) {
		retVal.add(td);
	    }
	}

	return retVal;
    }

    public long getCurrentScience() {
	try {
	    return (long) getTiles().getHeadquarters().getResource("Science", 0);
	} catch (Exception e) {
	    return 0;
	}
    }

    public long getCurrentFestival() {
	try {
	    return (long) getTiles().getHeadquarters().getResource("Festival", 0);
	} catch (Exception e) {
	    return 0;
	}
    }

    public double getAccumulatedValue() {
	try {
	    SGValueTracker vt = getValueTrackers().get(0);
	    return vt.getAccumulated();
	} catch (Exception e) {
	    return 0;
	}
    }

    public double getCurrentEv() {
	try {
	    SGValueTracker vt = getValueTrackers().get(0);
	    double lfhValue = vt.getLastFullHourValue();
	    long lfhTick = vt.getLastFullHourTick();

	    log.trace("lfhTick=" + CivIdleUtils.ticksToTimeReadable(lfhTick));
	    log.trace("lfhValue=" + CivIdleUtils.formatNumber(lfhValue));

	    long remainingTicks = getTick() - lfhTick;
	    log.trace("remainingTicksReadable=" + CivIdleUtils.ticksToTimeReadable(remainingTicks));
	    double remainingValue = ((vt.getAccumulated()) / remainingTicks);
	    log.trace("remainingValueReadable=" + CivIdleUtils.formatNumber(remainingValue));

	    return remainingValue;
	} catch (Exception e) {
	    return 0;
	}
    }

    public CityDef getCityDef() {
	return CityDefs.CITIES_BY_NAME.get(city);
    }

}
