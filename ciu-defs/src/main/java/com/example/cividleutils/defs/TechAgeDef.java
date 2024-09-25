package com.example.cividleutils.defs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
@ToString(includeFieldNames = true)
public class TechAgeDef {

    private int index;

    private String name;

    private int fromCol;

    private int toCol;

    private int colorNum;

    public TechAgeDef(int index, String name, int from, int to, int colorNum) {
	this.index = index;
	this.name = name;
	this.fromCol = from;
	this.toCol = to;
	this.colorNum = colorNum;
    }

}
