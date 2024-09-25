package sandbox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import net.lbcode.commons.base.io.FileUtils;
import net.lbcode.commons.base.io.PathUtils;

@Slf4j
public class NewClass {

    static Pattern civIdlePattern = Pattern.compile("CivIdle(_\\d{1,2})?");

    public static void main(String[] args) throws IOException {

	Path userHome = PathUtils.getUserHome();
	Path appDataRoaming = userHome.resolve("AppData\\Roaming");

	Path saves1 = appDataRoaming.resolve("CivIdleLocal");
	Path saves2 = appDataRoaming.resolve("CivIdleSaves");

	Files.walk(saves2, 3).forEach((t) -> {
	    String ts = t.toString();

	    if (isValidCividleSave(t)) {
		System.out.println("Found save " + t);

		Instant in = FileUtils.getLastModifiedTimeOpt(t).get();
//		OffsetDateTime odt = in.atZone()

		ZoneId zoneId = ZoneId.systemDefault();
		ZoneOffset offset = ZoneOffset.ofTotalSeconds(zoneId.getRules().getOffset(in).getTotalSeconds());

		// Convert the instant to an OffsetDateTime with the current time zone offset
		OffsetDateTime odt;

//         odt = in.atOffset(offset);
		odt = in.atZone(zoneId).toOffsetDateTime();

		log.error("45345 " + odt);
		log.error("45345 " + odt.atZoneSameInstant(ZoneId.of("UTC")));
		log.error("453 " + toUtcOffsetDate(in));
		log.error("453 " + toLocalOffsetDate(in));
		log.error("453 " + toUtcDenseString(in));
		log.error("453 " + toUtcDensestString(in));
	    }
//	    if (Files.isRegularFile(t)) {
//		if (ts.contains("CivIdle")) {
//		    log.error("5252 " + ts);

//		    log.error("2352 " + t.getFileName());
//		    log.error("2352 " + t.getFileName()+": "+ civIdlePattern.matcher(t.getFileName().toString()).matches());

//	    }
	});

    }

    public static OffsetDateTime toUtcOffsetDate(Instant in) {
	return in.atOffset(ZoneOffset.UTC);
    }

    public static OffsetDateTime toLocalOffsetDate(Instant in) {
	ZoneId zid = ZoneId.systemDefault();
	ZonedDateTime zdt = in.atZone(zid);
	return zdt.toOffsetDateTime();
    }

    public static String toUtcDenseString(Instant in) {
	OffsetDateTime odt = toUtcOffsetDate(in);
//	DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	String denseString = odt.format(formatter);
	return denseString;
    }

    public static String toUtcDensestString(Instant in) {
	OffsetDateTime odt = toUtcOffsetDate(in);
//	DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssSSSXXX");
	String densestString = odt.format(formatter);
	return densestString;
    }

    public static boolean isValidCividleSave(Path p) {
	if (!Files.exists(p) || !Files.isRegularFile(p)) {
	    return false;
	}

	String fn = p.getFileName().toString();
	if (!civIdlePattern.matcher(fn).matches()) {
	    return false;
	}

	return true;

    }

}
