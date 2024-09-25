package sandbox;

import com.example.cividleutils.defs.Techs;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NewClass2 {

    public static void main(String[] args) {

	long count = 46;
	long x = (long) (Math.pow(4 * count, 3) * 1e6);
	log.error("x = " + x);
    }

}
