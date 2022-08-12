package me.kalmemarq.legacygui;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.input.Keybind;
import me.kalmemarq.legacygui.util.Language;
import me.kalmemarq.legacygui.util.SplashManager;
import me.kalmemarq.legacygui.util.TextureManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.lwjgl.input.Keyboard;

import java.util.logging.Logger;

public class LegacyGUIMod implements ModInitializer {
	public static final String MOD_ID = "kmlegacygui";
	public static final Logger LOGGER =	Logger.getLogger(MOD_ID);

	public static final float SPEED = 3;
	public static final Keybind FLYING_KEYBIND = new Keybind("Fly", Keyboard.KEY_G);
	public static final Keybind DESCENDING_KEYBIND = new Keybind("Descend", Keyboard.KEY_LSHIFT);

	public static TextureManager textureManager;

	@Override
	public void onInitialize() {
		SplashManager.load();
	}

	public static void onMinecraftInitalized(Minecraft mc) {
		Language.load();
		textureManager = new TextureManager(mc);
	}
}
