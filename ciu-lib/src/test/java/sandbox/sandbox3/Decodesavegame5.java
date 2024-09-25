package sandbox.sandbox3;

import com.example.cividleutils.defs.TechDef;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import net.lbcode.commons.base.concurrent.SharedThreadPools;
import net.lbcode.commons.base.concurrent.ThreadUtils;
import com.example.cividleutils.lib.CivIdleInstallation;
import com.example.cividleutils.lib.CivIdleProfile;
import com.example.cividleutils.lib.CivIdleUtils;
import com.example.cividleutils.lib.prepopo.PrepopoModule;
import com.example.cividleutils.obj.DoubleAtTick;
import com.example.cividleutils.obj.SGValueTracker;
import com.example.cividleutils.obj.SaveGame;
import com.example.cividleutils.obj.SaveGameSeries;

@Slf4j
public class Decodesavegame5 {

    public static ObjectMapper mapper = new ObjectMapper();

    public static CivIdleInstallation cii;

    public static void main(String[] args) throws Exception {
	log.info("main...");

	SharedThreadPools.getDaemon().submit(() -> {
	    ThreadUtils.sleepMinNoEx(10);
	    System.exit(0);
	    log.debug("Exiting");

	});

	mapper = new ObjectMapper();
	mapper.registerModule(new PrepopoModule());

	cii = CivIdleInstallation.getDefault();
	Path bp = cii.getBasePath();
	log.debug("cii.basePath: " + bp);

	CivIdleProfile cip = cii.getLastProfile();
	log.debug("cip.userId: " + cip.getUserId());

	SaveGameSeries sgs = new SaveGameSeries();
	sgs.readProfile(cip);

	DoubleAtTick datNewer = sgs.getCurrentEvBetween(0, 1).get();
	DoubleAtTick datOlder = sgs.getCurrentEvBetween(1, 2).get();

	log.error("3n {} @ {}", CivIdleUtils.formatNumber(datNewer.value()),
		CivIdleUtils.ticksToTimeReadable(datNewer.tick()));
	log.error("3o {} @ {}", CivIdleUtils.formatNumber(datOlder.value()),
		CivIdleUtils.ticksToTimeReadable(datOlder.tick()));

	log.error("ct {}", CivIdleUtils.ticksToTimeReadable(sgs.getSaveGame(0).getCurrent().getTick()));

	long tickDelta = datNewer.tick() - datOlder.tick();
	double incPerTick = (datNewer.value() - datOlder.value()) / tickDelta;

	log.error("ev/s {}", CivIdleUtils.formatNumber(incPerTick));
	log.error("ev/24h {}", CivIdleUtils.formatNumber(incPerTick * 60 * 60 * 24));

	if (false) {
	    SaveGame sg0 = sgs.getSaveGame(0);
	    long tick = sg0.getCurrent().getTick();
	    long ticks2 = tick - datNewer.tick();
	    log.error("1 " + CivIdleUtils.ticksToTimeReadable(ticks2));

	    double xx = datNewer.value() + (incPerTick * ticks2);
	    log.error("11 " + CivIdleUtils.formatNumber(xx));

	}

	if (false) {
	    SaveGame sg0 = sgs.getSaveGame(0);
	    TechDefWalker tdw = new TechDefWalker(sg0);

	    long sci = sg0.getCurrent().getCurrentScience();
	    double sciPerSec = sgs.getAverageSciencePerSec().get();

	    for (TechDef td : tdw.getNextToUnlockTechs()) {
		String name = td.name();
		long cost = tdw.getTotalUnlockCost(td);
		long costRemaining = cost - sci;
		long ticks = (long) (costRemaining / sciPerSec);
		String s1 = CivIdleUtils.ticksToTimeReadable(ticks);
		log.info("NextToUnlock: {} / {} / {}", name, cost, s1);
	    }
	}

	if (true) {
	    log.info("6 ");
	    SaveGame sg0 = sgs.getSaveGame(0);
	    long tick0 = sg0.getCurrent().getTick();
	    log.info("66 " + CivIdleUtils.ticksToTimeReadable(tick0));

	    SGValueTracker vt0 = sg0.getCurrent().getValueTrackers().get(0);

	    log.info("666 " + vt0);

	    long xx = vt0.getHistory().length - 1;
	    long xxx = xx * 3600;

	    log.info("66 " + CivIdleUtils.ticksToTimeReadable(xxx));

//	    double dd =vt0.getTotal();
//	    log.info("666 " + CivIdleUtils.formatNumber(dd));

	    log.info("666 6 lfht= " + CivIdleUtils.ticksToTimeReadable(vt0.getLastFullHourTick()));
	    log.info("666 66 lfhv= " + CivIdleUtils.formatNumber(vt0.getLastFullHourValue()));
	    log.info("666 666 cce= " + CivIdleUtils.formatNumber(sg0.getCurrent().getCurrentEv()));

	    log.info("666 666 6 = "
		    + CivIdleUtils.formatNumber(sg0.getCurrent().getCurrentEv() / sg0.getCurrent().getTick()));

	}

    }
}
