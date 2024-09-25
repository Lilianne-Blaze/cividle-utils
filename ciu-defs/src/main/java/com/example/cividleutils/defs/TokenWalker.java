package com.example.cividleutils.defs;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(fluent = true)
@Slf4j
public class TokenWalker implements Iterator<String> {

    @Getter
    private List<String> tokenList;

    @Getter
    private int currentTokenIndex = -1;

    public TokenWalker(List<String> tokenList) {
	this.tokenList = tokenList;
    }

    public int size() {
	return tokenList.size();
    }

    @Override
    public boolean hasNext() {
	return !tokenList.isEmpty() && (currentTokenIndex+1) < tokenList.size();
    }

    @Override
    public String next() {
	if (!hasNext()) {
	    throw new NoSuchElementException();
	}
	currentTokenIndex++;
	return tokenList.get(currentTokenIndex);
    }

    public String current() {
	return tokenList.get(currentTokenIndex);
    }
    
    public int currentIndex()
    {
	return currentTokenIndex;
    }

    public String relative(int relPos) throws IndexOutOfBoundsException {
	int ind = currentTokenIndex + relPos;
	if (ind < 0 || ind >= size()) {
	    throw new IndexOutOfBoundsException();
	}
	return tokenList.get(ind);
    }

    public String relative(int relPos, String defVal) {
	int ind = currentTokenIndex + relPos;
	if (ind < 0 || ind >= size()) {
	    return defVal;
	}
	return tokenList.get(ind);
    }

    public boolean hasMore(int howMany) {
	int ind = currentTokenIndex + howMany;
	return ind < size();
    }

    public boolean anyNextEquals(String s) {
	for (int i = currentTokenIndex + 1; i < size(); i++) {
	    String tok = tokenList.get(i);
	    if (tok.equals(s)) {
		return true;
	    }
	}
	return false;
    }

    public boolean currentAndNextEquals(String s1, String s2) {
	return current().equals(s1) && relative(1, "").equals(s2);
    }

    public boolean isCurrentAWord() {
	return current().matches("[a-zA-Z0-9]+");
    }

    public void skipSilently(int howMany) {
	currentTokenIndex += howMany;
    }

}
