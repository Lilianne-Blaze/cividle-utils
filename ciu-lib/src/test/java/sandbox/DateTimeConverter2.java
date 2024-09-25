package sandbox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTimeConverter2 {

    public static void main(String[] args) {
	String[] datetimeStrings = { "2024-09-13T15:27:49.715072900Z[UTC]", "2024-09-13T15:27:49.715072900Z",
		"2024-09-13T17:27:49.715072900+02:00", "2024-09-13T15:27:49.715Z", "2024-09-13T15-27-44-715Z",
		"20240913T152749715Z", "2024-09-13T15-27:49715Z", "2024-09-13T15-27-49.715Z", "2024-09 13T15 27:49",
		"2024-09-13T15-27-49" };

	for (String dts : datetimeStrings) {
	    System.out.println();
	    log.info("dts=" + dts);
	    log.info("  res=" + formatDateTime(dts));

	    log.info("");
	}

    }

    public static String formatDateTime(String dateTimeString) {
	// Regular expression to match the underscore or nothing between 4th and 5th,
	// 6th and 7th, 10th and 11th, and 12th and 13th digits

	if (true) {

	    String dash = "\\x2d";
	    String underscore = "\\x5f";
	    String colon = "\\x3a";
	    String space = "\\x20";

	    String dddd = "(\\d{4})";
	    String dd = "(\\d{2})";

	    String ddtdd = "(\\d{2}[tT]\\d{2})";

//	String sepDt = "[-_]?";
	    String sepDt = "[" + dash + underscore + space + "]?";

//	String sepH = "[_\\:\\s]?";
	    String sepH = "[" + dash + underscore + colon + space + "]?";

//	String sepHMil = "["+dash+"_\\:\\s]?";
	    String sepHMil = "[" + dash + underscore + colon + space + "]?";

	    String rest = "(.*)$";

	    // handles everything up to hours
//	    String p2 = "^" + dddd + sepDt + dd + sepDt + ddtdd + sepHMil + rest;
	    String p2 = "^" + dddd + sepDt + dd + sepDt + ddtdd + sepH + rest;

	    System.out.println("" + p2);

	    Pattern pattern = Pattern.compile(p2);
	    Matcher matcher = pattern.matcher(dateTimeString);

	    log.error("dts1?");
	    if (matcher.find()) {
		// Replace the underscore or nothing with a dash for 4th-5th and 6th-7th digits,
		// and a colon for 10th-11th and 12th-13th digits
//		dateTimeString = matcher.replaceFirst("$1-$2-$3:$4");
		dateTimeString = matcher.replaceFirst("$1-$2-$3:$4");
		log.error("  dts1=" + dateTimeString);
	    }

	}

	if (true) {
	    log.error("3333vvv");
	    log.error("3333^^^");
	}

//	Pattern pattern = Pattern
//		.compile("(\\d{4})[_\\s]*\\d{2}([_\\s]*\\d{2})?[_\\s]*\\d{2}([_\\s]*\\d{2})?[_\\s]*\\d{2}");
//	Pattern pattern = Pattern.compile(p2);
//	Matcher matcher = pattern.matcher(dateTimeString);
//
//	if (matcher.find()) {
//	    // Replace the underscore or nothing with a dash for 4th-5th and 6th-7th digits,
//	    // and a colon for 10th-11th and 12th-13th digits
//	    dateTimeString = matcher.replaceFirst("$1-$2-$3:$4:$5");
//	}

	// Check if the string is in ISO 8601 format
	DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
	try {
	    LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
	    return dateTimeString; // Valid ISO 8601 format
	} catch (Exception e) {
	    return null; // Invalid ISO 8601 format
	}
    }
}
