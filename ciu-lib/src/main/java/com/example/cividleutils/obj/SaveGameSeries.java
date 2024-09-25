package com.example.cividleutils.obj;

import com.example.cividleutils.lib.CivIdleUtils;
import com.example.cividleutils.lib.CivIdleProfile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SaveGameSeries {

    private Deque<SaveGame> saveGames = new ConcurrentLinkedDeque<>();

    public void reset() {
	saveGames.clear();
    }

    public void readProfile(CivIdleProfile profile) throws IOException {
	log.debug("Loading current save...");
	SaveGame sgc = SaveGames.readSaveGame(profile.getCurrentSaveDirPath().resolve("CivIdle"));
	saveGames.addFirst(sgc);
	String d1 = CivIdleUtils.ticksToTimeReadable(sgc.getCurrent().getTick());
	log.debug("Loaded current save id={} ticks={}", sgc.getCurrent().getId(), d1);

	List<SaveGame> sgbl = new ArrayList<>();
	for (int i = 1; i <= 10; i++) {
	    try {
		String filename = "CivIdle_" + i;
		SaveGame sgb = SaveGames.readSaveGame(profile.getPreviousSaveDirPath().resolve(filename));

		String d2 = CivIdleUtils.ticksToTimeReadable(sgb.getCurrent().getTick());
		log.info("Loaded prev save id={} ticks={}", sgb.getCurrent().getId(), d2);

		sgbl.add(sgb);

	    } catch (Exception e) {
	    }
	}
	sgbl.sort((o1, o2) -> {
	    int x = Long.compare(o2.getCurrent().getTick(), o1.getCurrent().getTick());
	    return x;
	});
	for (SaveGame sgb : sgbl) {
	    if (sgb.getCurrent().getTick() != sgc.getCurrent().getTick()
		    && sgb.getCurrent().getId().equals(sgc.getCurrent().getId())) {

		saveGames.addLast(sgb);
	    }

	}

    }

    public void addLatest(SaveGame sg) {
	log.error("444 ");
	log.error("444 4 " + sg.getCurrent().getTick());
	saveGames.addFirst(sg);
    }

    public boolean hasTwoOrMore() {
	return saveGames.size() >= 2;
    }

    public Deque getDeque() {
	return saveGames;
    }

    public SaveGame getSaveGame(int index) {
	SaveGame[] sga = (SaveGame[]) saveGames.toArray(new SaveGame[0]);
	return sga[index];
    }

    public Optional<Double> getAverageSciencePerSec() {
	try {
	    SaveGame[] sga = (SaveGame[]) saveGames.toArray(new SaveGame[0]);
	    SaveGame cur = sga[0];
	    SaveGame prev = sga[1];

	    long timeDelta = cur.getCurrent().getTick() - prev.getCurrent().getTick();
	    double scienceDelta = cur.getCurrent().getCurrentScience() - prev.getCurrent().getCurrentScience();
	    double sciPerSec = scienceDelta / timeDelta;
	    return Optional.of(sciPerSec);
	} catch (Exception e) {
	}
	return Optional.empty();
    }

    public Optional<Double> getCurrentEv() {
	try {
	    SaveGame[] sga = (SaveGame[]) saveGames.toArray(new SaveGame[0]);
	    SaveGame cur = sga[0];
	    SaveGame prev = sga[1];

	    long timeDelta = cur.getCurrent().getTick() - prev.getCurrent().getTick();

	    double curAccu = cur.getCurrent().getAccumulatedValue();
	    double prevAccu = prev.getCurrent().getAccumulatedValue();
	    double accuDelta = curAccu - prevAccu;
	    double accuPerSec = accuDelta / timeDelta;

	    return Optional.of(accuPerSec);
	} catch (Exception e) {
	}
	return Optional.empty();
    }

    public Optional<DoubleAtTick> getCurrentEvBetween(int newerIndex, int olderIndex) {
	try {
	    SaveGame[] sga = (SaveGame[]) saveGames.toArray(new SaveGame[0]);
	    SaveGame newerSg = sga[newerIndex];
	    SaveGame olderSg = sga[olderIndex];

	    double newerAccu = newerSg.getCurrent().getAccumulatedValue();
	    double olderAccu = olderSg.getCurrent().getAccumulatedValue();
	    double accuDelta = newerAccu - olderAccu;
	    long newerTick = newerSg.getCurrent().getTick();
	    long olderTick = olderSg.getCurrent().getTick();
	    log.debug("newerTick=" + newerTick);
	    log.debug("olderTick=" + olderTick);
	    log.debug("newerAccu=" + newerAccu);
	    log.debug("olderAccu=" + olderAccu);

	    long timeDelta = newerTick - olderTick;
	    long midTick = newerTick - (timeDelta / 2);
	    double midAccu = accuDelta / timeDelta;
	    log.debug("timeDelta=" + timeDelta);
	    log.debug("midTick=" + midTick);
	    log.debug("midAccu=" + midAccu);

	    return Optional.of(new DoubleAtTick(midAccu, midTick));
	} catch (Exception e) {
	}
	return Optional.empty();
    }

    public Optional<Double> getEvPerSec() {
//	try {
//	    SaveGame[] sga = (SaveGame[]) saveGames.toArray(new SaveGame[0]);
//	    SaveGame cur = sga[0];
//	    SaveGame prev = sga[1];
//	    SaveGame prev2 = sga[2];
//
//	    long timeDelta = cur.getCurrent().getTick() - prev.getCurrent().getTick();
//
//	    double curAccu = cur.getCurrent().getAccumulatedValue();
//	    double prevAccu = prev.getCurrent().getAccumulatedValue();
//	    double prevAccu2 = prev2.getCurrent().getAccumulatedValue();
//	    
//	    double increase = curAccu - prevAccu;
//	    double accuPerSec = increase / timeDelta;
//
//	    return Optional.of(accuPerSec);
//	} catch (Exception e) {
//	}
	return Optional.empty();
    }

}
