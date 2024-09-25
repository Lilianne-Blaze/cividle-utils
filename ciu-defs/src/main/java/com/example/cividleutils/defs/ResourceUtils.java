package com.example.cividleutils.defs;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class ResourceUtils {

    public static Optional<String> loadStringOpt(Class contextClass, String name) {
	try {
	    InputStream is = contextClass.getResourceAsStream(name);
	    byte[] bs = is.readAllBytes();
	    String s = new String(bs, StandardCharsets.UTF_8);
	    return Optional.of(s);
	} catch (Exception e) {
	    return Optional.empty();
	}
    }

}
