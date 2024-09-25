package com.example.cividleutils.lib;

import java.nio.file.Path;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CivIdleProfile {

    @Getter
    private CivIdleInstallation installation;

    @Getter
    private String userId;

    public CivIdleProfile(CivIdleInstallation installation, String userType, String userId) {
	this.installation = installation;
	this.userId = userId;
    }

    public Path getCurrentSaveDirPath() {
	return installation.getBasePath().resolve("CivIdleSaves\\" + userId);
    }

    public Path getPreviousSaveDirPath() {
	return installation.getBasePath().resolve("CivIdleLocal\\" + userId);
    }

}
