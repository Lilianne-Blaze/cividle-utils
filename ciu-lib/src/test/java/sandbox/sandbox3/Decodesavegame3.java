package sandbox.sandbox3;

import com.example.cividleutils.defs.CityDef;
import com.example.cividleutils.defs.CityDefs;
import com.example.cividleutils.defs.TechDef;
import com.example.cividleutils.lib.utils.MiscUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.StdConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import lombok.extern.slf4j.Slf4j;
import com.example.cividleutils.obj.SGTile;
import com.example.cividleutils.obj.SGTiles;
import com.example.cividleutils.obj.SGValueTrackers;
import com.example.cividleutils.obj.SaveGame;
import com.example.cividleutils.lib.prepopo.PrepopoModule;

@Slf4j
public class Decodesavegame3 {

    public static ObjectMapper mapper = new ObjectMapper();

    Converter<Object, Object> un1;

    StdConverter<Object, Object> un2;

    public static ByteArrayOutputStream txtOutBuffer;

    public static PrintWriter txtOut;

    public static void main(String[] args) throws Exception {
	log.info("main...");

	String inFileArg = "CivIdle";
	if (args.length == 1) {
	    inFileArg = args[0];
	} else {
	}

	long sysMillis = System.currentTimeMillis();
	Path inFilePath = Paths.get(inFileArg);
	Path outFilePath = inFilePath.resolveSibling(inFileArg + "." + sysMillis + ".json");
	Path txtFilePath = inFilePath.resolveSibling(inFileArg + "." + sysMillis + ".txt");

	// read and inflate
	byte[] bytesRaw = Files.readAllBytes(inFilePath);
	ByteArrayInputStream bais = new ByteArrayInputStream(bytesRaw);
	Inflater inf = new Inflater(true);
	InflaterInputStream iis = new InflaterInputStream(bais, inf);
	byte[] bytesJson = iis.readAllBytes();

	test3(bytesJson);

	// format json
	ObjectMapper map = new ObjectMapper();
	JsonNode root = map.readTree(bytesJson);
	byte[] bytesPretty = map.writerWithDefaultPrettyPrinter().writeValueAsBytes(root);

	// Files.write(outFilePath, bytesPretty);

	txtOutBuffer = new ByteArrayOutputStream();
	txtOut = new PrintWriter(txtOutBuffer, true, StandardCharsets.UTF_8);

//	processCity(root);

	// Files.write(txtFilePath, txtOutBuffer.toByteArray());

	System.out.println(txtOutBuffer.toString(StandardCharsets.UTF_8));

//	System.out.println("Press ctrl+c or wait 15 min to continue");
	// Thread.sleep(15 * 60 * 1000);
    }

    public static void processCity(JsonNode root) {

	ObjectNode current = (ObjectNode) root.get("current");
	String id = current.get("id").asText();
	String cityName = current.get("city").asText();
	CityDef city = CityDefs.CITIES_BY_NAME.get(cityName);

	txtOut.println("Savegame id: " + id);
	txtOut.println("Active city: " + city.name());
	txtOut.println();

	ObjectNode tiles = (ObjectNode) current.get("tiles");
	ArrayNode value = (ArrayNode) tiles.get("value");

	Map<Integer, ObjectNode> tileMap = new LinkedHashMap<>();

	Iterator<JsonNode> valueIt = value.iterator();
	while (valueIt.hasNext()) {
	    ArrayNode array2 = (ArrayNode) valueIt.next();
	    int tileNum = array2.get(0).asInt();
	    ObjectNode tileObj = (ObjectNode) array2.get(1);
	    tileMap.put(tileNum, tileObj);
	}

	txtOut.println("Natural wonders:");
	for (ObjectNode tileNode : tileMap.values()) {
	    int tileNum = tileNode.get("tile").asInt();
	    String buildingType = MiscUtils.getBuildingType(tileNode);
	    if (MiscUtils.isNaturalWonder(tileNode, city)) {
		String coords = MiscUtils.getCoordsWithPercents(tileNum, city);
		txtOut.println("Natural wonder " + buildingType + " found at " + coords);
	    }
	}
	for (int iy = 0; iy < city.height(); iy++) {
	    txtOut.print("|");
	    for (int ix = 0; ix < city.width(); ix++) {
		ObjectNode tileNode = tileMap.get(MiscUtils.getTileNum(ix, iy));
		if (MiscUtils.isNaturalWonder(tileNode, city)) {
		    // txtOut.print("&");
		    String letter = MiscUtils.getBuildingType(tileNode).substring(0, 1);
		    txtOut.print(letter);

		} else if (MiscUtils.isHq(tileNode)) {
		    txtOut.print("@");
		} else if (MiscUtils.isExplored(tileNode)) {
		    txtOut.print(" ");
		} else {
		    txtOut.print("-");
		}

	    }
	    txtOut.println("|");
	}
	txtOut.println();

    }

    static void prettyPrint(Object obj) throws JsonProcessingException {
	String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
	System.out.println(s);
    }

    static void test3(byte[] bytesJson) throws Exception {

	mapper.registerModule(new PrepopoModule());
	JsonNode root = mapper.readTree(bytesJson);

	log.info("mapper = " + mapper);

	SaveGame sg = mapper.readValue(bytesJson, SaveGame.class);

	Object obj;

	obj = sg;

//	log.warn("2454 "+sg.getCurrent().getParent());

//	log.info("obj = " + obj);

	byte[] bytesPretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(obj);
	String jsonPretty = new String(bytesPretty);

//	log.info("p = {}", jsonPretty);

	if (false) {
	    // show techs
	    sg.getCurrent().getUnlockedTechs().forEach((t) -> {
		log.info("Unlocked tech: " + t);
	    });

	}

	if (false) {
	    // show upgrades
	    sg.getCurrent().getUnlockedUpgradeNames().forEach((t) -> {
		log.info("Unlocked upgrade: " + t);
	    });
	}

	if (false) {
	    SGTiles tiles = sg.getCurrent().getTiles();

	    SGTile tile = tiles.getXY(1, 1);
	    tile = tiles.getFirstByType("Headquarter");

	    log.error("srgsg " + tile);
	    log.error("srgsg " + tile.getTile());

	    log.error("srgsg " + tile.getResource("Science", 0));
	    log.error("srgsg " + tile.getResource("Festival", 0));

	    prettyPrint(tile);

	    log.error("======");

	}

	if (true) {
	    SGValueTrackers vts = sg.getCurrent().getValueTrackers();

	    log.error("sgsees " + vts);

	    prettyPrint(vts);

	    log.error("======");
	}

	if (false) {
	    long time1 = System.currentTimeMillis();
	    List<TechDef> unlockedTechs = sg.getCurrent().getUnlockedTechs();
	    sg.getCurrent().getLockedTechs().forEach((td) -> {
		log.error("");
		log.error("Locked tech: " + td);

//		for (TechDef td2 : td.getAllRequiredTechsExcept(unlockedTechs)) {
		for (TechDef td2 : td.getAllRequiredTechsExcept(null)) {
//		for (TechDef td2 : td.getAllRequiredTechs()) {
		    log.error("  ..required tech: " + td2);
		}

	    });
	    long time2 = System.currentTimeMillis();
	    log.error("millis: " + (time2 - time1));
	}

	if (true) {
	    long time1 = System.currentTimeMillis();

	    TechDefWalker tdw = new TechDefWalker(sg);

//	    List<TechDef> unlockedTechs = sg.getCurrent().getUnlockedTechs();

//	    for (TechDef lt : tdw.getLockedTechs()) {
	    for (TechDef lt : tdw.getNextToUnlockTechs()) {
		log.error("");
		log.error("Locked tech: " + lt);

//		long totalCost = 0;
		for (TechDef rt : tdw.getLockedReqTechs(lt)) {
		    log.error("..req tech: " + rt);
//		    log.error("....with cost: " + tdw.getTotalUnlockCost(rt));
		}
		log.error("..total cost: "+tdw.getTotalUnlockCost(lt));
		

	    }

//	    sg.getCurrent().getLockedTechs().forEach((td) -> {
//		log.error("");
//		log.error("Locked tech: " + td);
//
////		for (TechDef td2 : td.getAllRequiredTechsExcept(unlockedTechs)) {
//		for (TechDef td2 : td.getAllRequiredTechsExcept(null)) {
////		for (TechDef td2 : td.getAllRequiredTechs()) {
//		    log.error("  ..required tech: " + td2);
//		}
//
//	    });
	    long time2 = System.currentTimeMillis();
	    log.error("");
	    log.error("millis: " + (time2 - time1));
	}
    }

}
