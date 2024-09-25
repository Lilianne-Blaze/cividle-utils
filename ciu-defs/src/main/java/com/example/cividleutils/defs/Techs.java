package com.example.cividleutils.defs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/*
    https://github.com/fishpondstudio/CivIdle/blob/main/shared/definitions/TechDefinitions.ts
*/
@Slf4j
public class Techs {

    public static final List<TechDef> LIST = new ArrayList<>();

    public static final Map<String, TechDef> BY_NAME = new LinkedHashMap<>();

    static {
	loadData();
    }

    private static TechDef initTech(String techName, int column) {
	TechDef td = new TechDef(techName, column);
	LIST.add(td);
	BY_NAME.put(techName, td);
	return td;
    }

    private static TechDef initTech(String techName, int column, TechDef... reqTechs) {
	return initTech(techName, column).addRequireTechs(reqTechs);
    }

    private static void loadData() {

	String resData = ResourceUtils.loadStringOpt(Techs.class, ("tech_ts_defs.txt")).get();
	List<String> tokens = new PseudotsTokenizer().tokenize(resData);
	TokenWalker walker = new TokenWalker(tokens);

	String curTechName = null;
	int curColumn = 0;
	List<String> curReqTechs = new ArrayList<>();

	while (walker.hasNext()) {
	    walker.next();

	    if (walker.relative(2, "").equals("ITechDefinition")) {
		curTechName = walker.current();
	    } else if (walker.current().equals("column")) {
		String s = walker.relative(2);
		curColumn = Integer.valueOf(s);
		walker.skipSilently(2);
	    } else if (walker.current().equals("requireTech")) {
		walker.skipSilently(2);
		while (!walker.current().equals("]")) {
		    if (walker.isCurrentAWord()) {
			curReqTechs.add(walker.current());
		    }
		    walker.next();
		}
	    } else if (walker.currentAndNextEquals("}", ";")) {
		TechDef techDef = initTech(curTechName, curColumn);
		for (String reqTechName : curReqTechs) {
		    TechDef reqTech = Techs.BY_NAME.get(reqTechName);
		    techDef.addRequireTechs(reqTech);
		}
		curReqTechs.clear();
	    }

	}
    }

}
