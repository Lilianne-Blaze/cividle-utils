package com.example.cividleutils.obj;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import com.example.cividleutils.lib.utils.MiscUtils;

@JsonDeserialize(using = SGTiles.Deserializer.class)
@Slf4j
public class SGTiles implements Map<Integer, SGTile> {

    public static class Deserializer extends StdDeserializer<SGTiles> {

	public Deserializer() {
	    this(null);
	}

	public Deserializer(Class<?> vc) {
	    super(vc);
	}

	@Override
	public SGTiles deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
//	    log.debug("Entering deserialize...");
	    SGTiles retVal = new SGTiles();
	    ObjectMapper objMap = (ObjectMapper) jp.getCodec();

	    JsonNode rootNode = objMap.readTree(jp);
	    JsonNode tilesNode = rootNode.get("value");

	    Iterator<JsonNode> tilesNodeId = tilesNode.iterator();
	    while (tilesNodeId.hasNext()) {
		JsonNode tileIdObjNode = tilesNodeId.next();
		int tileId = tileIdObjNode.get(0).asInt();
		JsonNode tileObjNode = tileIdObjNode.get(1);
		SGTile tileObj = objMap.treeToValue(tileObjNode, SGTile.class);

		retVal.put(tileId, tileObj);
	    }
	    return retVal;
	}

    }

    @Delegate
    private Map<Integer, SGTile> delegate = new TreeMap<>();

    public SGTile getXY(int x, int y) {
	int num = MiscUtils.getTileNum(x, y);
	return get(num);
    }

    public SGTile getHeadquarters() {
	return getFirstByType("Headquarter");
    }

    public SGTile getFirstByType(String type) {
	for (SGTile tile : values()) {
	    if (tile.getBuildingOpt().isPresent()) {
		if (Objects.equals(tile.getBuilding().getType(), type)) {
		    return tile;
		}
	    }
	}
	return null;
    }

}
