package com.example.cividleutils.lib.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    public <T> List<T> copyLast(List<T> list, int num) {
	int start = Math.max(0, list.size() - num);
	List<T> newList = new ArrayList<>(list.subList(start, list.size()));
	return newList;
    }

}
