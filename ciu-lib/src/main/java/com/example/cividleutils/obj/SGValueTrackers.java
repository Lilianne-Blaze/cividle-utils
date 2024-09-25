package com.example.cividleutils.obj;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;

@JsonDeserialize(using = SGValueTrackers.Deserializer.class)
@Slf4j
public class SGValueTrackers implements Map<Integer, SGValueTracker> {

    public static class Deserializer extends StdDeserializer<SGValueTrackers> {

	public Deserializer() {
	    this(null);
	}

	public Deserializer(Class<?> vc) {
	    super(vc);
	}

	@Override
	public SGValueTrackers deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
	    log.trace("Entering deserialize...");
	    SGValueTrackers retVal = new SGValueTrackers();
	    ObjectMapper objMap = (ObjectMapper) jp.getCodec();

	    JsonNode rootNode = objMap.readTree(jp);
	    JsonNode pairsNode = rootNode.get("value");

	    pairsNode.forEach((pairNode) -> {
		int index = pairNode.get(0).asInt();
		JsonNode valueTrackerNode = pairNode.get(1);
		SGValueTracker valueTracker = objMap.convertValue(valueTrackerNode, SGValueTracker.class);

		retVal.valueTrackers.put(index, valueTracker);
	    });

	    return retVal;
	}

    }

    @Delegate
    private Map<Integer, SGValueTracker> valueTrackers = new TreeMap<>();

}
