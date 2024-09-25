package com.example.cividleutils.defs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NewClass1 {

    public static void main(String[] args) {
	log.info("main...");
	try {

	    //String inText = new String(NewClass1.class.getResourceAsStream("tech_ts_defs.txt").readAllBytes());
	    String inText = ResourceUtils.loadStringOpt(NewClass1.class, ("tech_ts_defs.txt")).get();

	    List<String> tokens = new PseudotsTokenizer().tokenize(inText);

	    TokenWalker walker = new TokenWalker(tokens);
	    log.info("walker.size = {}", walker.size());

	    String buildingName = null;
	    int column = 0;
	    List<String> reqTechs = new ArrayList<>();

	    while (walker.hasNext()) {
		String curTok = walker.next();

		log.debug("/curTok = " + walker.currentIndex() + " " + curTok);
		log.debug("\\nextTok = " + walker.currentIndex()+1 + " " + walker.relative(1, ""));

		if (walker.relative(2, "").equals("ITechDefinition")) {
		    buildingName = walker.current();

		    log.debug("building name found: " + buildingName);
		} else if (walker.current().equals("column")) {
		    String s = walker.relative(2);
		    column = Integer.valueOf(s);
		    walker.skipSilently(2);
		    
		    log.debug("column found: "+column);
		} else if (walker.current().equals("requireTech")) {
		    log.debug("reqTech start");
		    walker.skipSilently(2);
		    while (!walker.current().equals("]")) {
			if (walker.isCurrentAWord()) {
			    log.debug("reqTech "+walker.current());
			    reqTechs.add(walker.current());
			}
			walker.next();
		    }
		} else if (walker.currentAndNextEquals("}", ";")) {
		    log.error("bu {} / {} / {}", buildingName, column, reqTechs);
		    reqTechs.clear();
		}

	    }

//	log.error(""+tokens);

	} catch (Exception e) {
	    log.error("Exception: " + e.getMessage(), e);
	}
    }

}
