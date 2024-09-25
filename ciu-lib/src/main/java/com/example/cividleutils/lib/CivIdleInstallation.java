package com.example.cividleutils.lib;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.lbcode.commons.base.io.FileUtils;
import net.lbcode.commons.base.io.PathUtils;

@Slf4j
public class CivIdleInstallation {

    private static CivIdleInstallation INSTANCE = new CivIdleInstallation();

    @Getter
    private Path basePath;

    public static CivIdleInstallation getDefault() {
	return INSTANCE;
    }

    public CivIdleInstallation() {
	basePath = PathUtils.getUserHome().resolve("AppData\\Roaming");

    }

    public CivIdleProfile getLastProfile() throws IOException {
	List<String> lastLogLines = FileUtils.readLinesFromLastBytes(basePath.resolve("CivIdleLocal\\CivIdle.log"),
		1024 * 1024);
	lastLogLines = lastLogLines.reversed();

	String steamId = null;
	for (String line : lastLogLines) {
	    String mark1 = "\"[WelcomeMessage] CurrentTick: ";
	    if (line.contains(mark1)) {
		String sep1 = "steam:";
		String sep2 = "\"";
		int i = line.indexOf(sep1);
		String s = line.substring(i + sep1.length());
		i = s.indexOf(sep2);
		s = s.substring(0, i);
		steamId = s;
		break;
	    }
	}

	return new CivIdleProfile(this, "steam", steamId);
    }

}
