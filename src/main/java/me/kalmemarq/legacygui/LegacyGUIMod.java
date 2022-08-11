package me.kalmemarq.legacygui;

import me.kalmemarq.legacygui.util.Language;
import me.kalmemarq.legacygui.util.SplashManager;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class LegacyGUIMod implements ModInitializer {
	public static final String MOD_ID = "kmlegacygui";
	public static final Logger LOGGER =	Logger.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		SplashManager.load();
		Language.load();
	}
}
