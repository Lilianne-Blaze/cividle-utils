package com.example.cividleutils.defs;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/*
    See https://github.com/fishpondstudio/CivIdle/blob/main/shared/definitions/TechDefinitions.ts
*/
@Slf4j
public class TechAges {

    public static final TechAgeDef StoneAge = new TechAgeDef(0, "StoneAge", 0, 1, 0xb2bec3);

    public static final TechAgeDef BronzeAge = new TechAgeDef(1, "BronzeAge", 2, 3, 0xb2bec3);

    public static final TechAgeDef IronAge = new TechAgeDef(2, "IronAge", 4, 5, 0x596275);

    public static final TechAgeDef ClassicalAge = new TechAgeDef(3, "ClassicalAge", 6, 8, 0x81ecec);

    public static final TechAgeDef MiddleAge = new TechAgeDef(4, "MiddleAge", 9, 10, 0xff7675);

    public static final TechAgeDef RenaissanceAge = new TechAgeDef(5, "RenaissanceAge", 11, 13, 0xa29bfe);

    public static final TechAgeDef IndustrialAge = new TechAgeDef(6, "IndustrialAge", 14, 17, 0xfd79a8);

    public static final TechAgeDef WorldWarAge = new TechAgeDef(7, "WorldWarAge", 18, 20, 0xfdcb6e);

    public static final TechAgeDef ColdWarAge = new TechAgeDef(8, "ColdWarAge", 21, 23, 0x74b9ff);

    public static final TechAgeDef InformationAge = new TechAgeDef(9, "InformationAge", 24, 27, 0x55efc4);

    public static final List<TechAgeDef> LIST = new ArrayList<>();

    public static final Map<String, TechAgeDef> BY_NAME = new LinkedHashMap<>();

    public static final Map<Integer, TechAgeDef> BY_COLUMN = new LinkedHashMap<>();

    static {
	initData();
    }

    private static void initTechAge(TechAgeDef ta) {
	LIST.add(ta);
	BY_NAME.put(ta.name(), ta);
	for (int i = ta.fromCol(); i <= ta.toCol(); i++) {
	    BY_COLUMN.put(i, ta);
	}
    }

    private static void initData() {
	initTechAge(StoneAge);
	initTechAge(BronzeAge);
	initTechAge(IronAge);
	initTechAge(ClassicalAge);
	initTechAge(MiddleAge);
	initTechAge(RenaissanceAge);
	initTechAge(IndustrialAge);
	initTechAge(WorldWarAge);
	initTechAge(ColdWarAge);
	initTechAge(InformationAge);
    }

}
