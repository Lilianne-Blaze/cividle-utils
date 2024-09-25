package sandbox.decoderlogger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import lombok.extern.slf4j.Slf4j;
import net.lbcode.commons.base.io.FileWatcher;
import net.lbcode.commons.base.io.FileWatcherEvent;
import static sandbox.sandbox3.Decodesavegame5.cii;
import static sandbox.sandbox3.Decodesavegame5.mapper;
import com.example.cividleutils.lib.CivIdleGlobals;
import com.example.cividleutils.lib.CivIdleInstallation;
import com.example.cividleutils.lib.CivIdleProfile;
import com.example.cividleutils.obj.SaveGame;
import com.example.cividleutils.obj.SaveGames;
import com.example.cividleutils.lib.prepopo.PrepopoModule;

@Slf4j
public class DecoderLogger {

    public static void main(String[] args) {
	log.info("Starting decoderlogger...");
	try {

	    ObjectMapper rawMapper = new ObjectMapper();

	    ObjectMapper mapper = new ObjectMapper();
	    mapper.registerModule(new PrepopoModule());

	    CivIdleInstallation cii = CivIdleInstallation.getDefault();
	    CivIdleProfile cip = cii.getLastProfile();
	    Path saveDirPath = cip.getCurrentSaveDirPath();
	    log.info("Using path: " + saveDirPath);

	    Path debugDirPath = saveDirPath.resolve("debug");
	    Files.createDirectories(debugDirPath);

	    FileWatcher fw = new FileWatcher();
	    fw.watchPath(saveDirPath);

	    Path saveIn = saveDirPath.resolve("CivIdle");
	    while (true) {
		FileWatcherEvent fwe = fw.take();
		if (fwe.isCreate() && fwe.path().endsWith("CivIdle")) {

		    log.info("Detected new savegame file, attempting to read...");
		    String timestampStr = "" + System.currentTimeMillis();

		    try {
			Path saveOut = saveDirPath.resolve("debug\\CivIdle." + timestampStr+".raw");
			Path saveOutJson = saveDirPath.resolve("debug\\CivIdle." + timestampStr + ".json");

			Files.copy(saveIn, saveOut);

			JsonNode saveGameNode = SaveGames.readSaveGameRaw(saveIn);
			CivIdleGlobals.RAW_OBJECT_MAPPER.writerWithDefaultPrettyPrinter()
				.writeValue(saveOutJson.toFile(), saveGameNode);

		    } catch (Exception e) {
			log.warn("Problem decoding and logging the savegame file, waiting for the next one. "
				+ e.getMessage());
		    }
		}
	    }

	} catch (Exception e) {
	    log.error("Exception: " + e.getMessage(), e);
	}
    }

}
