package com.example.cividleutils.obj;

import com.example.cividleutils.lib.CivIdleGlobals;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class SaveGames {

    public static SaveGame readSaveGame(Path path) throws IOException {
	byte[] bs = Files.readAllBytes(path);
	return readSaveGame(bs);
    }

    public static SaveGame readSaveGame(byte[] bytes) throws IOException {
	byte[] bytes2 = inflateBytes(bytes);
	SaveGame sg = CivIdleGlobals.OBJECT_MAPPER.readValue(bytes2, SaveGame.class);
	return sg;
    }

    public static JsonNode readSaveGameRaw(Path path) throws IOException {
	byte[] bs = Files.readAllBytes(path);
	byte[] bs2 = inflateBytes(bs);
	JsonNode node = CivIdleGlobals.RAW_OBJECT_MAPPER.readTree(bs2);
	return node;
    }

    private static byte[] inflateBytes(byte[] inBytes) throws IOException {
	Inflater inf = new Inflater(true);
	ByteArrayInputStream bais = new ByteArrayInputStream(inBytes);
	InflaterInputStream iis = new InflaterInputStream(bais, inf);
	byte[] outBytes = iis.readAllBytes();
	return outBytes;
    }

}
