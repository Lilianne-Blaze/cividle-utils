/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.cividleutils.defs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Lilianne_Blaze
 */
public class TechDefinitionParser {

// This method takes input string and tokenizes whole digit-letter words.
    public static List<String> tokenizeInput(String input) {
        // Regular expression to match words (letters and digits only)
        Pattern wordPattern = Pattern.compile("\\b\\w+\\b");
        Matcher matcher = wordPattern.matcher(input);
        
        List<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
	    
	    System.err.println("token "+matcher);
        }
        return tokens;
    }

    // This method extracts the buildings, the tech names, and their requirements
    public static void parseDefinitions(List<String> tokens) {
        String building = "";
        String techName = "";
        List<String> requirements = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            // Detect the building name (word before ITechDefinition)
            if (tokens.get(i).equals("ITechDefinition")) {
                techName = tokens.get(i - 1);
            }
            // Detect the building (after unlockBuilding)
            if (tokens.get(i).equals("unlockBuilding")) {
                building = tokens.get(i + 2); // The building is two words ahead
            }
            // Detect the requirements (between requireTech and unlockBuilding)
            if (tokens.get(i).equals("requireTech")) {
                i += 2; // Skip "requireTech" and the opening bracket
                requirements.clear(); // Clear previous requirements
                while (!tokens.get(i).equals("unlockBuilding")) {
                    requirements.add(tokens.get(i).replace("\"", ""));
                    i++;
                }
            }

            // Output the findings
            if (!techName.isEmpty() && !building.isEmpty() && !requirements.isEmpty()) {
                System.out.println("Building: " + building);
                System.out.println("Tech Name: " + techName);
                System.out.println("Requirements: " + requirements);
                System.out.println();
                // Reset after printing
                techName = "";
                building = "";
                requirements.clear();
            }
        }
    }

    public static void main(String[] args) {
        String input = "  Optics: ITechDefinition = { name: () => t(L.Optics), column: 11, requireTech: [\"Navigation\"], unlockBuilding: [\"LensWorkshop\"], globalMultiplier: { builderCapacity: 1 }, buildingMultiplier: { Library: { output: 1 }, School: { output: 1 }, }, additionalUpgrades: () => [t(L.SeaTradeUpgrade, { tariff: formatPercent(SEA_TILE_COST_2) })], };"
                     + " Banking: ITechDefinition = { name: () => t(L.Banking), column: 11, requireTech: [\"CivilService\", \"Navigation\"], unlockBuilding: [\"Bank\"], additionalUpgrades: () => [t(L.BankingAdditionalUpgrade)], };"
                     + " University: ITechDefinition = { name: () => t(L.University), column: 11, requireTech: [\"HolyEmpire\", \"Education\", \"CivilService\"], unlockBuilding: [\"University\", \"OxfordUniversity\"], globalMultiplier: { sciencePerBusyWorker: 1 }, };";

        // Tokenize the input
        List<String> tokens = tokenizeInput(input);
        
        // Parse the definitions and extract the information
        parseDefinitions(tokens);
    }
}
