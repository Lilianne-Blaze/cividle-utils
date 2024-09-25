package com.example.cividleutils.lib.utils;

import lombok.extern.slf4j.Slf4j;
import net.lbcode.commons.base.concurrent.SharedThreadPools;
import net.lbcode.commons.base.concurrent.ThreadUtils;

@Slf4j
public class KillAppUtils {

    public static void killAppAfterMin(int minutes) {
	SharedThreadPools.getDaemon().submit(() -> {
	    ThreadUtils.sleepMinNoEx(30);
	    System.exit(0);
	    log.debug("Exiting");
	});
    }

}
