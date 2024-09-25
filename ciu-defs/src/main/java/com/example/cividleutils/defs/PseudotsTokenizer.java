package com.example.cividleutils.defs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PseudotsTokenizer {

    public List<String> tokenize(InputStream is) throws IOException {
	String s = new String(is.readAllBytes());
	return tokenize(s);
    }

    public List<String> tokenize(String s) {
	List<String> tokens = new ArrayList<>();

	// Pattern wordPattern = Pattern.compile("\\b\\w+\\b");
	Pattern wordPattern = Pattern.compile("\\b\\w+\\b|[(){}.,:;=>\\]\\[]");
	Matcher matcher = wordPattern.matcher(s);

	while (matcher.find()) {
	    tokens.add(matcher.group());
	}

	return tokens;
    }

}
