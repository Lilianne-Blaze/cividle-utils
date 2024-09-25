package com.example.cividleutils.defs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import net.lbcode.commons.base.suppliers.CHMLazyInitializer;

@Getter
@Accessors(fluent = true)
@ToString(callSuper = true)
public class TechDef extends UnlockableDef {

    protected int column;

    protected String techAgeName;

    @ToString.Exclude
    protected List<TechDef> requireTechs = new ArrayList<>();

    @ToString.Exclude
    protected TechAgeDef techAge;

    @ToString.Include
    protected long unlockCost;

    protected Supplier<List<TechDef>> allRequiredTechsHolder = new CHMLazyInitializer<>(this::initAllRequiredTechs);

    public TechDef(String name, int column) {
	this.name = name;
	this.column = column;
	this.techAge = TechAges.BY_COLUMN.get(column);
	this.techAgeName = techAge.name();

	init();
    }

    private void init() {
	unlockCost = (long) (Math.pow(5, techAge.index()) * Math.pow(1.5, column()) * 5000);
    }

    public TechDef addRequireTechs(TechDef... rts) {
	requireTechs().addAll(Arrays.asList(rts));
	return this;
    }

    @ToString.Include
    public List<String> requireTechNames() {
	List<String> l = new ArrayList<>();
	for (TechDef td : requireTechs) {
	    l.add(td.name());
	}
	return l;
    }

//    @ToString.Include
//    public long getUnlockCost() {
//	return (long) (Math.pow(5, techAge.index()) * Math.pow(1.5, column()) * 5000);
//    }

    private List<TechDef> initAllRequiredTechs() {
	return getAllRequiredTechsExcept(null);
    }

    public List<TechDef> getDirectRequiredTechs() {
	return requireTechs;
    }

    public List<TechDef> getAllRequiredTechs() {
	return allRequiredTechsHolder.get();
    }

    public List<TechDef> getAllRequiredTechsExcept(Collection<TechDef> exceptTechs) {
	List<TechDef> retVal = new ArrayList<>();
	for (TechDef tdr : getDirectRequiredTechs()) {
	    getAllRequiredTechs0(tdr, retVal, exceptTechs);
	}

	if (true) {
	    retVal.sort((o1, o2) -> {
		return Integer.compare(o1.column, o2.column);
	    });
	}
	return retVal;
    }

    private void getAllRequiredTechs0(TechDef td, List<TechDef> list, Collection<TechDef> exceptList) {
	if (exceptList != null && exceptList.contains(this)) {
	    return;
	}
	for (TechDef tdr : td.getDirectRequiredTechs()) {
	    getAllRequiredTechs0(tdr, list, exceptList);
	}
	if (!list.contains(td)) {
	    list.add(td);
	}
    }

}
