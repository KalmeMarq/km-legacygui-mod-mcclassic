package me.kalmemarq.legacygui.util;

import me.kalmemarq.legacygui.LegacyGUIMod;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.spongepowered.include.com.google.common.collect.Maps;
import org.spongepowered.include.com.google.common.collect.Sets;
import org.spongepowered.include.com.google.gson.Gson;
import org.spongepowered.include.com.google.gson.GsonBuilder;
import org.spongepowered.include.com.google.gson.JsonElement;
import org.spongepowered.include.com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Language {
    private static Map<String, String> storage = Maps.newHashMap();
    private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static LanguageInfo DEFAULT_LANG = new LanguageInfo("en_us", "English", "US");
    public static String language = "en_us";
    public static LanguageInfo languageInfo = DEFAULT_LANG;
    public static List<LanguageInfo> languages = Arrays.asList(DEFAULT_LANG);

    public static void load() {
        ModContainer container = FabricLoader.getInstance().getModContainer(LegacyGUIMod.MOD_ID).get();

        {
            File d = container.findPath("pack.json").get().toFile();

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(d.toPath())));
                JsonObject obj = GSON.fromJson(reader, JsonObject.class);

                List<LanguageInfo> langs = new ArrayList<>();

                for (Map.Entry<String, JsonElement> e : obj.entrySet()) {
                    String prop = e.getKey();

                    if (prop.equals("language")) {
                        JsonObject langObj = e.getValue().getAsJsonObject();

                        for (Map.Entry<String, JsonElement> le : langObj.entrySet()) {
                            String code = le.getKey();
                            JsonObject data = le.getValue().getAsJsonObject();
                            String name = "";
                            String region = "";

                            for (Map.Entry<String, JsonElement> lle : data.entrySet()) {
                                if (lle.getKey().equals("name")) {
                                    name = lle.getValue().getAsString();
                                } else if (lle.getKey().equals("region")) {
                                    region = lle.getValue().getAsString();
                                }
                            }

                            langs.add(new LanguageInfo(code, name, region));
                        }

                        break;
                    }
                }

                languages = langs;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        boolean langExists = false;
        for (int i = 0; i < languages.size(); i++) {
            if (languages.get(i).getCode().equals(Language.language)) {
                langExists = true;
                break;
            }
        }

        if (!langExists) {
            Language.language = DEFAULT_LANG.getCode();
        }


        Set<String> codesSet = Sets.newHashSet("en_us", Language.language);
        List<String> codes = new ArrayList<>(codesSet);

        loadStorage(container, codes);
    }

    public static String translate(String key) {
        return storage.getOrDefault(key, key);
    }

    private static void loadStorage(ModContainer container, List<String> codes) {
        Map<String, String> store = Maps.newHashMap();

        for (String code : codes) {
            Optional<Path> fP = container.findPath("assets/kmlegacygui/lang/" + code +".json");

            if (fP.isPresent()) {
                File f = fP.get().toFile();

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(f.toPath())));
                    JsonObject obj = GSON.fromJson(reader, JsonObject.class);

                    for (Map.Entry<String, JsonElement> e : obj.entrySet()) {
                        store.put(e.getKey(), e.getValue().getAsString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        storage = store;
    }
}
