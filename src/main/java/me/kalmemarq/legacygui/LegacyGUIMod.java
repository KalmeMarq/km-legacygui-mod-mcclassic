package me.kalmemarq.legacygui;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.input.Keybind;
import me.kalmemarq.legacygui.gui.hud.InGameHud;
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
	public static final Keybind PLAYERLIST_KEYBIND = new Keybind("Player List", Keyboard.KEY_TAB);

	public static TextureManager textureManager;
	private static Minecraft MC_INSTANCE;

	public static InGameHud inGameHud;
	private static boolean is64bit = checkIs64Bit();

	public static void onMCConstructor(Minecraft mc) {
		MC_INSTANCE = mc;
		inGameHud = new InGameHud(mc);
	}

	public static Minecraft getMCInstance() {
		return MC_INSTANCE;
	}

	@Override
	public void onInitialize() {
		SplashManager.load();
	}

	public static void onMinecraftInitalized(Minecraft mc) {
		Language.load();
		textureManager = new TextureManager(mc);
	}

	public static boolean is64Bit() {
		return is64bit;
	}

	private static boolean checkIs64Bit() {
		String[] props = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};

		for(int i = 0; i < props.length; ++i) {
			String prop = props[i];
			String value = System.getProperty(prop);
			if (value != null && value.contains("64")) {
				return true;
			}
		}

		return false;
	}
}
