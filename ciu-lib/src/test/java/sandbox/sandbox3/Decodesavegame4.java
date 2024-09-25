package sandbox.sandbox3;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.lbcode.commons.base.concurrent.SharedThreadPools;
import net.lbcode.commons.base.concurrent.ThreadUtils;
import net.lbcode.commons.base.io.FileWatcher;
import net.lbcode.commons.base.io.FileWatcherEvent;
import com.example.cividleutils.lib.CivIdleInstallation;
import com.example.cividleutils.lib.CivIdleProfile;
import com.example.cividleutils.lib.CivIdleUtils;
import com.example.cividleutils.obj.SaveGame;
import com.example.cividleutils.obj.SaveGameSeries;
import com.example.cividleutils.obj.SaveGames;
import com.example.cividleutils.lib.prepopo.PrepopoModule;

@Slf4j
public class Decodesavegame4 {

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

//	log.debug("cip.userId: " + cip.getUserId());

	SaveGame sg = SaveGames.readSaveGame(cip.getCurrentSaveDirPath().resolve("CivIdle"));

	{
	    double[] dd = sg.getCurrent().getValueTrackers().get(0).getHistory();
	    log.error("dd.s " + dd.length);
	    log.error("dd[last] " + dd[dd.length - 1]);
	}

//	log.debug("sg.c.id: " + sg.getCurrent().getId());
//	log.debug("sg.c.city: " + sg.getCurrent().getCity());
//	log.debug("sg.c.tick: " + sg.getCurrent().getTick());
//	log.debug("sg.c.tickR: " + CivIdleUtils.ticksToTimeReadable(sg.getCurrent().getTick()));

	FileWatcher fw = new FileWatcher();
	fw.watchPath(cip.getCurrentSaveDirPath());

	SaveGameSeries sgs = new SaveGameSeries();

	while (true) {
	    FileWatcherEvent fwe = fw.take();
	    if (fwe.isCreate() && fwe.path().endsWith("CivIdle")) {

		log.debug("");
		SaveGame sg2 = SaveGames.readSaveGame(cip.getCurrentSaveDirPath().resolve("CivIdle"));

		sgs.addLatest(sg2);

		log.debug("sgs.size: " + sgs.getDeque().size());

		log.debug("sg2.c.id: " + sg2.getCurrent().getId());
		log.debug("sg2.c.city: " + sg2.getCurrent().getCity());
		log.debug("sg2.c.tick: " + sg2.getCurrent().getTick());
		log.debug("sg2.c.tickR: " + CivIdleUtils.ticksToTimeReadable(sg2.getCurrent().getTick()));

//		long accu = (Long)((Map) sg2.getCurrent().getValueTrackers().get(0)).get("accumulated");
//		double accu = sg2.getCurrent().getValueTrackers().get(0).getAccumulated();
		log.debug("sg2 accu: " + CivIdleUtils.formatNumber(sg2.getCurrent().getAccumulatedValue()));
		log.debug("sg2 sci: " + CivIdleUtils.formatNumber(sg2.getCurrent().getCurrentScience()));

//				log.debug("sg2.c.tick: " + sg2.getCurrent().getTiles().getHeadquarters()

		if (sgs.hasTwoOrMore()) {
		    double sciPerSec = sgs.getAverageSciencePerSec().get();
		    double sciPerDay = sciPerSec * 60 * 60 * 24;

		    log.debug("Sci/sec: " + CivIdleUtils.formatNumber(sciPerSec));
		    log.debug("Sci/day: " + CivIdleUtils.formatNumber(sciPerDay));

		    double curEv = sgs.getCurrentEv().get();
		    log.debug("EV: " + CivIdleUtils.formatNumber(curEv));

		    double evPerSec = sgs.getEvPerSec().orElse(0d);
		    log.debug("EV/sec: " + CivIdleUtils.formatNumber(evPerSec));

//		    log.debug("5 "+ CivIdleUtils.formatNumber(curEv*1425840));

		    if (true && sgs.getDeque().size() >= 3) {

			SaveGame s0 = sgs.getSaveGame(0);
			SaveGame s1 = sgs.getSaveGame(1);
			SaveGame s2 = sgs.getSaveGame(2);

			long tick0 = s0.getCurrent().getTick();
			log.debug("tick0=" + tick0);
			long tick1 = s1.getCurrent().getTick();
			log.debug("tick1=" + tick1);
			long tick2 = s2.getCurrent().getTick();
			log.debug("tick2=" + tick2);

			long tickDelta01 = tick0 - tick1;
			log.debug("tickDelta01=" + tickDelta01);
			long tickDelta12 = tick1 - tick2;
			log.debug("tickDelta12=" + tickDelta12);

			long midTick01 = s0.getCurrent().getTick() - (tickDelta01 / 2);
			log.debug("midTick01=" + midTick01);
			long midTick12 = s1.getCurrent().getTick() - (tickDelta12 / 2);
			log.debug("midTick12=" + midTick12);

			double accu0 = s0.getCurrent().getAccumulatedValue();
			log.debug("accu0=" + accu0);
			double accu1 = s1.getCurrent().getAccumulatedValue();
			log.debug("accu1=" + accu1);
			double accu2 = s2.getCurrent().getAccumulatedValue();
			log.debug("accu2=" + accu2);

			double accuDelta01 = accu0 - accu1;
			log.debug("accuDelta01=" + accuDelta01);
			double accuDelta12 = accu1 - accu2;
			log.debug("accuDelta12=" + accuDelta12);

			double accu01 = accuDelta01 / tickDelta01;
			log.debug("accu01=" + accu01);
			double accu12 = accuDelta12 / tickDelta12;
			log.debug("accu12=" + accu12);

			// !!!
			double accu012 = (accu01 + accu12) / 2;
			log.debug("accu012=" + accu012);
			log.debug("accu012=" + CivIdleUtils.formatNumber(accu012));

			// ?
			long tick123 = (tickDelta01 + tickDelta12) / 2;
			log.debug("tick123=" + tick123);
			log.debug("tick123=" + CivIdleUtils.ticksToTimeReadable(tick123));

			long midTick123 = (midTick01 + midTick12) / 2;
			log.debug("midTick123=" + midTick123);
			log.debug("midTick123=" + CivIdleUtils.ticksToTimeReadable(midTick123));

			double xx = accu01 - accu12;
			log.debug("xx=" + xx);
			double xxx = xx / tick123;
			log.debug("xxx=" + xxx);
			log.debug("xxx=" +CivIdleUtils.formatNumber(xxx));

		    }
		}

	    }

	}

    }
}
