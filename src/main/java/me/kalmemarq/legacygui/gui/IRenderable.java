package me.kalmemarq.legacygui.gui;

import com.mojang.minecraft.Minecraft;

public interface IRenderable {
    void render(Minecraft mc, int mouseX, int mouseY);
}
