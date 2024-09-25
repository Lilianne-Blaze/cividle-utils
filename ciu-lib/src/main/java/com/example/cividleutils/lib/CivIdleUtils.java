package com.example.cividleutils.lib;

import java.text.DecimalFormat;
import java.time.Duration;

public class CivIdleUtils {

    public static String ticksToTimeReadable(long ticks) {
	boolean hideSecsIfMoreThanDay = true;

	Duration ticksDur = Duration.ofSeconds(ticks);
	StringBuilder sb = new StringBuilder();

	long days = ticksDur.toDaysPart();
	if (days > 0) {
	    sb.append(days).append("d ");
	}

	long hours = ticksDur.toHoursPart() % 24;
	if (hours > 0 || days > 0) {
	    sb.append(hours).append("h ");
	}

	long minutes = ticksDur.toMinutesPart() % 60;
	sb.append(minutes).append("m ");

	long seconds = ticksDur.toSecondsPart() % 60;
	if (!hideSecsIfMoreThanDay || days == 0) {
	    sb.append(seconds).append("s");
	}

	return sb.toString().trim();
    }

    public static String formatNumber(double number) {
	return formatNumberDefault(number);
    }

    public static String formatNumberDefault(double number) {
	// see https://idlechampions.fandom.com/wiki/Large_number_abbreviations
	String[] suffixes = { "K", "M", "B", "T", "Qa", "Qi", "Sx", "Sp", "Oc", "No", "Dc" };
	double numberAbs = Math.abs(number);
	for (int i = suffixes.length - 1; i >= 0; i--) {
	    double threshold = Math.pow(1000, i + 1);
	    if (numberAbs >= threshold) {
		// DecimalFormat df = new DecimalFormat("#.##");
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(number / threshold) + suffixes[i];
	    }
	}

	// return String.valueOf(number);
	DecimalFormat df = new DecimalFormat("#.00");
	return df.format(number);
    }

}
