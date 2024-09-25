package sandbox.sandbox3;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CityDefs {

    public static final List<CityDef> CITIES = new ArrayList<>();

    public static final Map<String, CityDef> CITIES_BY_NAME = new LinkedHashMap<>();

    static {
	initDefs();
    }

    private static void initDefs() {

	CityDef cityMemphis = new CityDef("Memphis", 50).withNaturalWonderNames(List.of("NileRiver", "MountSinai"));
	CITIES.add(cityMemphis);
	CITIES_BY_NAME.put(cityMemphis.name(), cityMemphis);

	CityDef cityKyoto = new CityDef("Kyoto", 35).withNaturalWonderNames(List.of("MountFuji", "Kanagawa"));
	CITIES.add(cityKyoto);
	CITIES_BY_NAME.put(cityKyoto.name(), cityKyoto);

    }

}
