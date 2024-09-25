package com.example.cividleutils.mapdecoder;

import com.example.cividleutils.defs.CityDef;
import com.example.cividleutils.lib.CivIdleInstallation;
import com.example.cividleutils.lib.CivIdleProfile;
import com.example.cividleutils.lib.CivIdleUtils;
import com.example.cividleutils.lib.utils.MiscUtils;
import com.example.cividleutils.lib.utils.WinConsoleUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import net.lbcode.commons.base.concurrent.ThreadUtils;
import net.lbcode.commons.base.logging.LoggingUtils;
import com.example.cividleutils.obj.SGCurrent;
import com.example.cividleutils.obj.SGTile;
import com.example.cividleutils.obj.SaveGame;
import com.example.cividleutils.obj.SaveGames;

@Slf4j
public class MapDecoderLauncher {

    private static CivIdleInstallation cii;

    private static SaveGame curSaveGame;

    private static SaveGame prevSaveGame;

    public static void main(String[] args) {
	try {
	    LoggingUtils.setLoggingLevel("*", "warn");

//	    KillAppUtils.killAppAfterMin(30);

	    cii = CivIdleInstallation.getDefault();

	    while (true) {
		try {
		    CivIdleProfile cip = cii.getLastProfile();

		    prevSaveGame = curSaveGame;
		    curSaveGame = SaveGames.readSaveGame(cip.getCurrentSaveDirPath().resolve("CivIdle"));
		    forgetPrevIfDiffId();

		    WinConsoleUtils.clearScreenCompletely();

		    printSaveGameInfo(curSaveGame);
		    printSaveGameDiff(curSaveGame, prevSaveGame);
		    printSaveGameMap(curSaveGame);

		    WinConsoleUtils.scrollToBufferTop();
		} catch (Exception e) {
		    e.printStackTrace();
		}

		ThreadUtils.sleepSecNoEx(60);
	    }

	} catch (Exception e) {
	    log.error("Exception: " + e.getMessage(), e);
	}
    }

    private static void forgetPrevIfDiffId() {
	if (curSaveGame != null && prevSaveGame != null) {
	    String curId = curSaveGame.getCurrent().getId();
	    String prevId = prevSaveGame.getCurrent().getId();
	    if (!Objects.equals(curId, prevId)) {
		prevSaveGame = null;
	    }
	}
    }

    private static void printSaveGameInfo(SaveGame sg) throws IOException {
	String tickTime = CivIdleUtils.ticksToTimeReadable(sg.getCurrent().getTick());
	pl("City: " + sg.getCurrent().getCity() + ", tick time: " + tickTime);
	pl("Id: " + sg.getCurrent().getId());
    }

    private static void printSaveGameDiff(SaveGame sg, SaveGame psg) throws IOException {
	if (psg == null) {
	    return;
	}

	long curTick = sg.getCurrent().getTick();
	long prevTick = psg.getCurrent().getTick();
	long diffTick = curTick - prevTick;

	double curEv = sg.getCurrent().getCurrentEv();
	double prevEv = psg.getCurrent().getCurrentEv();
	double diffEv = curEv - prevEv;
	double perSecEv = diffEv / diffTick;

	double curSci = sg.getCurrent().getCurrentScience();
	double prevSci = psg.getCurrent().getCurrentScience();
	double diffSci = curSci - prevSci;
	double perSecSci = diffSci / diffTick;

	String s1 = "~" + CivIdleUtils.formatNumber(perSecEv) + " EV/sec";
	String s2 = "~" + CivIdleUtils.formatNumber(perSecEv * 60 * 60 * 24) + " EV/24h";
	pl(s1 + " " + s2 + " (rough guesstimate)");

	String s3 = "~" + CivIdleUtils.formatNumber(perSecSci) + " Sci/sec";
	String s4 = "~" + CivIdleUtils.formatNumber(perSecSci * 60 * 60 * 24) + " Sci/24h";
	pl(s3 + " " + s4 + " (rough guesstimate)");

    }

    private static void printSaveGameMap(SaveGame sg) throws IOException {
	SGCurrent sgc = sg.getCurrent();
	CityDef cityDef = sgc.getCityDef();

	List<SGTile> natWondTiles = new ArrayList<>();

	for (SGTile tile : sgc.getTiles().values()) {
	    if (!tile.isExplored()) {
		if (tile.getBuilding() != null) {
		    if (cityDef.naturalWonderNames().contains(tile.getBuilding().getType())) {
			natWondTiles.add(tile);
		    }
		}
	    }
	}

	for (int iy = 0; iy < cityDef.height(); iy++) {
	    p("|");
	    for (int ix = 0; ix < cityDef.width(); ix++) {
		SGTile tile = sgc.getTiles().getXY(ix, iy);
		boolean isNatWond = cityDef.naturalWonderNames().contains(tile.getBuildingTypeOpt().orElse(""));

		if (isNatWond) {
		    String letter = tile.getBuildingTypeOpt().orElse("---").substring(0, 1);
		    p(letter);
		} else if (tile.isHq()) {
		    p("@");
		} else if (tile.isExplored()) {
		    p(" ");
		} else {
		    p("-");
		}

	    }
	    pl("|");
	}

	for (int iy = 0; iy < cityDef.height(); iy++) {
	    for (int ix = 0; ix < cityDef.width(); ix++) {
		SGTile tile = sgc.getTiles().getXY(ix, iy);
		boolean isNatWond = cityDef.naturalWonderNames().contains(tile.getBuildingTypeOpt().orElse(""));
		boolean isExplored = tile.isExplored();

		if (isNatWond && !isExplored) {
		    int tileNum = MiscUtils.getTileNum(ix, iy);
		    String coords = MiscUtils.getCoordsWithPercents(tileNum, cityDef);
		    pl("Natural wonder " + tile.getBuildingTypeOpt().get() + " found at " + coords);
		}
	    }
	}

    }

    private static void p(String s) throws IOException {
	System.out.print(s);
    }

    private static void pl(String s) throws IOException {
	System.out.println(s);
    }

}
