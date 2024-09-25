package com.example.cividleutils.obj;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@JsonDeserialize(using = SGValueTracker.Deserializer.class)
@Slf4j
@ToString
public class SGValueTracker {

    public static class Deserializer extends StdDeserializer<SGValueTracker> {

	public Deserializer() {
	    this(null);
	}

	public Deserializer(Class<?> vc) {
	    super(vc);
	}

	@Override
	public SGValueTracker deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
	    log.trace("Entering deserialize...");
	    SGValueTracker retVal = new SGValueTracker();
	    ObjectMapper objMap = (ObjectMapper) jp.getCodec();

	    JsonNode rootNode = objMap.readTree(jp);

	    retVal.accumulated = rootNode.get("accumulated").asDouble();

	    JsonNode rootHistory = rootNode.get("history");

	    retVal.history = new double[rootHistory.size()];
	    for (int i = 0; i < rootHistory.size(); i++) {
		retVal.history[i] = rootHistory.get(i).asDouble();
	    }

	    return retVal;
	}

    }

    @Getter
    private double accumulated;

    @Getter
    private double[] history;

    public double getLastFullHourValue() {
	return history[history.length - 1];
    }

    public long getLastFullHourTick() {
	return (history.length - 1) * 3600;
    }

}
