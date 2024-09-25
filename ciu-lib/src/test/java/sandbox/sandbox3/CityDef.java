package sandbox.sandbox3;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import lombok.experimental.Accessors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Accessors(fluent = true)
@With
public class CityDef {

    @Getter
    private String name;

    @Getter
    private int width;

    @Getter
    private int height;

    @Getter
    private List<String> naturalWonderNames;

    public CityDef(String name, int size) {
	this.name = name;
	this.width = this.height = size;
	this.naturalWonderNames = new ArrayList<>();
    }

}
