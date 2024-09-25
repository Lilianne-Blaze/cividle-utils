package sandbox;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class DateTimeConverter {
    public static OffsetDateTime convertToOffsetDateTime(String datetimeString) {
	// Regular expression to match all supported formats
	String pattern = "^(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})(\\.\\d+)?(Z|([+-]\\d{2}):(\\d{2}))?$";

	if (!Pattern.matches(pattern, datetimeString)) {
	    throw new IllegalArgumentException("Invalid datetime format");
	}

	// Parse the datetime string using the appropriate formatter
	DateTimeFormatter formatter;
	if (datetimeString.endsWith("Z")) {
	    formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneOffset.UTC);
	} else if (datetimeString.contains("+") || datetimeString.contains("-")) {
	    formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
	} else {
	    formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	}

	return OffsetDateTime.parse(datetimeString, formatter);
    }

    public static void main(String[] args) {
	String[] datetimeStrings = { "2024-09-13T15:27:49.715072900Z[UTC]", "2024-09-13T15:27:49.715072900Z",
		"2024-09-13T17:27:49.715072900+02:00", "2024-09-13T15:27:49.715Z", "2024-09-13T15-27-49-715Z" };

	for (String datetimeString : datetimeStrings) {
	    System.out.println("Datetime string: " + datetimeString);
	    try {
		OffsetDateTime offsetDateTime = convertToOffsetDateTime(datetimeString);
		System.out.println("  OffsetDateTime: " + offsetDateTime);
	    } catch (IllegalArgumentException e) {
		System.out.println("  Error: " + e.getMessage());
	    }
	}
    }
}
