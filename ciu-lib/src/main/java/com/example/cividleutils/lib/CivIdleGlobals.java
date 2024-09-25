package com.example.cividleutils.lib;

import com.example.cividleutils.lib.prepopo.PrepopoModule;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CivIdleGlobals {

    public static final ObjectMapper RAW_OBJECT_MAPPER = new ObjectMapper();

    public static final ObjectMapper OBJECT_MAPPER = initObjectMapper();

    private static ObjectMapper initObjectMapper() {
	ObjectMapper om = new ObjectMapper();
	om.registerModule(new PrepopoModule());
	return om;
    }

}
