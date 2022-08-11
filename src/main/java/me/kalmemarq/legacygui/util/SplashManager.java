package me.kalmemarq.legacygui.util;

import me.kalmemarq.legacygui.LegacyGUIMod;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SplashManager {
    private final static Random RANDOM = new Random();
    private static List<String> splashes = new ArrayList<>();

    public static void load() {
        ModContainer self = FabricLoader.getInstance().getModContainer("kmlegacygui").get();

        try {
            Files.readAllLines(self.findPath("assets/kmlegacygui/texts/splashes.txt").get()).stream().filter(x -> !x.codePoints().anyMatch((y) -> y > 256)).forEach(splashes::add);
        } catch (Exception e) {
            LegacyGUIMod.LOGGER.info("Failed to load splashes");
        }
    }

    public static String getRandom() {
        if (SplashManager.splashes.size() > 0) {
            return SplashManager.splashes.get(RANDOM.nextInt(SplashManager.splashes.size()));
        }

        return "MISSINGNO";
    }
}
