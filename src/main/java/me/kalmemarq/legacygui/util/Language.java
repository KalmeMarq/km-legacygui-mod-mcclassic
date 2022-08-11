package me.kalmemarq.legacygui.util;

import me.kalmemarq.legacygui.LegacyGUIMod;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.spongepowered.include.com.google.common.collect.Maps;
import org.spongepowered.include.com.google.gson.Gson;
import org.spongepowered.include.com.google.gson.GsonBuilder;
import org.spongepowered.include.com.google.gson.JsonElement;
import org.spongepowered.include.com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Map;

public class Language {
    private final static Map<String, String> storage = Maps.newHashMap();
    private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static String language = "en_us";

    public static void load() {
        ModContainer container = FabricLoader.getInstance().getModContainer(LegacyGUIMod.MOD_ID).get();

        File f = container.findPath("assets/kmlegacygui/lang/" + language +".json").get().toFile();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(f.toPath())));
            JsonObject obj = GSON.fromJson(reader, JsonObject.class);

            storage.clear();
            for (Map.Entry<String, JsonElement> e : obj.entrySet()) {
                storage.put(e.getKey(), e.getValue().getAsString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String translate(String key) {
        return storage.getOrDefault(key, key);
    }
}
