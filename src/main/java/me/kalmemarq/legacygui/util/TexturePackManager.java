package me.kalmemarq.legacygui.util;

import com.mojang.minecraft.Minecraft;
import me.kalmemarq.legacygui.resource.TexturePack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TexturePackManager {
    private List<TexturePack> packs = new ArrayList<>();

    private final Minecraft minecraft;
    private File texturepacksFolder;

    public TexturePackManager(Minecraft mc, File gameDir) {
        this.minecraft = mc;
        this.texturepacksFolder = new File(gameDir, "texturepacks");

        if (!this.texturepacksFolder.exists()) {
            this.texturepacksFolder.mkdirs();
        }
    }
}
