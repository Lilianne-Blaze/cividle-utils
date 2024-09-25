package sandbox.sandbox3;

import com.example.cividleutils.defs.TechDef;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import com.example.cividleutils.obj.SaveGame;

@Slf4j
public class TechDefWalker {

    private SaveGame saveGame;

    @Getter
    private List<TechDef> lockedTechs;

    @Getter
    private List<TechDef> unlockedTechs;

    private ConcurrentHashMap<TechDef, List<TechDef>> cacheLockedReqTechs = new ConcurrentHashMap<>();

    public TechDefWalker(SaveGame saveGame) {
	this.saveGame = saveGame;
	init();
    }

    private void init() {
	log.debug("init... 1");

	unlockedTechs = saveGame.getCurrent().getUnlockedTechs();
	lockedTechs = saveGame.getCurrent().getLockedTechs();

	log.debug("init... 2");

    }

    public List<TechDef> getLockedReqTechs(TechDef td) {
	return cacheLockedReqTechs.computeIfAbsent(td, this::cacheLockedReqTechs0);
    }

    private List<TechDef> cacheLockedReqTechs0(TechDef td) {
	List<TechDef> retVal = new ArrayList<>();
	for (TechDef td2 : td.getDirectRequiredTechs()) {
	    cacheLockedReqTechs0(td2, retVal);
	}
	return retVal;
    }

    private void cacheLockedReqTechs0(TechDef td, List<TechDef> list) {
	if (unlockedTechs.contains(td)) {
	    return;
	}
	for (TechDef td2 : td.getDirectRequiredTechs()) {
	    cacheLockedReqTechs0(td2, list);
	}
	if (!list.contains(td)) {
	    list.add(td);
	}
    }

    public long getTotalUnlockCost(TechDef td) {
	long retVal = td.unlockCost();
//	log.error("22 " + td.unlockCost());
	for (TechDef td2 : getLockedReqTechs(td)) {
	    retVal += td2.unlockCost();
//	    log.error("222 " + td2.unlockCost());
	}
	return retVal;
    }

    public List<TechDef> getNextToUnlockTechs() {
	List<TechDef> list = getLockedTechs();
	list.removeIf((t) -> {
	    return getLockedReqTechs(t).size() != 0;
	});
	return list;
    }

}
