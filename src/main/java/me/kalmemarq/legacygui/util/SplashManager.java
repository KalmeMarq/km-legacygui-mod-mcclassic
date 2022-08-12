package me.kalmemarq.legacygui.util;

import me.kalmemarq.legacygui.LegacyGUIMod;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.nio.file.Files;
import java.util.*;

public class SplashManager {
    private final static Random RANDOM = new Random();
    private final static List<String> splashes = new ArrayList<>();

    public static void load() {
        ModContainer self = FabricLoader.getInstance().getModContainer("kmlegacygui").get();

        try {
            Files.readAllLines(self.findPath("assets/kmlegacygui/texts/splashes.txt").get()).stream().filter(x -> !x.codePoints().anyMatch((y) -> y > 256)).forEach(splashes::add);
        } catch (Exception e) {
            LegacyGUIMod.LOGGER.info("Failed to load splashes");
        }
    }

    public static String getRandom() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (calendar.get(Calendar.MONTH) + 1 == 11 && calendar.get(Calendar.DATE) == 9) {
            return "Happy birthday, ez!";
        } else if (calendar.get(Calendar.MONTH) + 1 == 6 && calendar.get(Calendar.DATE) == 1) {
            return "Happy birthday, Notch!";
        } else if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) == 24) {
            return "Merry X-mas!";
        } else if (calendar.get(Calendar.MONTH) + 1 == 1 && calendar.get(Calendar.DATE) == 1) {
            return "Happy new year!";
        }

        if (SplashManager.splashes.size() > 0) {
            return SplashManager.splashes.get(RANDOM.nextInt(SplashManager.splashes.size()));
        }

        return "MISSINGNO";
    }
}
