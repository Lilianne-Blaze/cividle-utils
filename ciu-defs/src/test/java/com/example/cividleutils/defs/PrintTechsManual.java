package com.example.cividleutils.defs;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintTechsManual {

    public static void main(String[] args) {

	System.out.println("TechAges amount = " + TechAges.LIST.size());
	TechAges.LIST.forEach((t) -> {
	    System.out.println("TechAge: " + t.toString());
	});
	System.out.println();

	System.out.println("Techs amount = " + Techs.LIST.size());
	Techs.LIST.forEach((t) -> {
	    System.out.println("Tech: " + t.toString());
	});
	System.out.println();

    }
}
