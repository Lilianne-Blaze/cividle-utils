package com.example.cividleutils.lib.utils;

import com.example.cividleutils.defs.CityDef;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MiscUtils {

    public static int getTileX(int tileNum) {
	return tileNum >> 16;
    }

    public static int getTileY(int tileNum) {
	return tileNum & 65535;
    }

    public static int getTileNum(int tileX, int tileY) {

	return tileX * 65536 + tileY;
    }

    public static String getBuildingType(ObjectNode tileNode) {
	try {
	    return tileNode.get("building").get("type").asText(null);
	} catch (Exception e) {
	    return null;
	}
    }

    public static String getCoordsWithPercents(int tileNum, CityDef city) {
	int x = getTileX(tileNum);
	int y = getTileY(tileNum);
	int xp = x * 100 / city.width();
	int yp = y * 100 / city.height();
	String s = "" + x + " (" + xp + "%), " + y + " (" + yp + "%)";
	return s;
    }

    public static boolean isHq(ObjectNode tileNode) {
	return "Headquarter".equals(getBuildingType(tileNode));
    }

    public static boolean isNaturalWonder(ObjectNode tileNode, CityDef city) {
	String buildingType = getBuildingType(tileNode);
	if (buildingType != null && city.naturalWonderNames().contains(buildingType)) {
	    return true;
	}
	return false;
    }

    public static boolean isExplored(ObjectNode tileNode) {
	return tileNode.get("explored").asBoolean(false);
    }

}
