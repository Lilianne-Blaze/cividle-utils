package com.example.cividleutils.defs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NewClass {

    public static void main(String[] args) {
	log.info("main...");
	try {

	    String inText = new String(NewClass.class.getResourceAsStream("tech_ts_defs.txt").readAllBytes());
	    List<String> tokens = new ArrayList<>();

	    // Pattern wordPattern = Pattern.compile("\\b\\w+\\b");
	    Pattern wordPattern = Pattern.compile("\\b\\w+\\b|[(){}.,:=>\\]\\[]");
	    Matcher matcher = wordPattern.matcher(inText);

	    while (matcher.find()) {
		tokens.add(matcher.group());

		// log.error("1@$@!%$ "+ inText.substring(matcher.end(),matcher.end()+2));
		log.error("token " + matcher.group());
	    }

	    if (true) {
		String curBuName;
		int curColumn;
		List<String> curReqTechs = new ArrayList<>();
		

		for (int curTokInd = 0; curTokInd < tokens.size(); curTokInd++) {
		    String curTok = tokens.get(curTokInd);
		    if (tokens.get(curTokInd + 2).equals("ITechDefinition")) {
			curBuName = curTok;
		    }
		    
		    
		}

	    }

//	log.error(""+tokens);

	} catch (Exception e) {
	    log.error("Exception: " + e.getMessage(), e);
	}
    }

}
