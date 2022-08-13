package me.kalmemarq.legacygui.gui.hud;

import com.mojang.minecraft.Minecraft;
import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;
import me.kalmemarq.legacygui.util.RenderHelper;
import me.kalmemarq.legacygui.util.TextRenderer;

import java.util.Iterator;
import java.util.List;

public class PlayerListHud extends ExtraDrawableHelper {
    private final Minecraft minecraft;
    private final InGameHud inGameHud;

    public PlayerListHud(Minecraft mc, InGameHud inGameHud) {
        this.minecraft = mc;
        this.inGameHud = inGameHud;
    }

    public void render(int screenWidth) {
        List<String> names = this.minecraft.connectionManager.getPlayerNames();
        Iterator<String> namesIt = names.iterator();
        int maxNameWidth = 0;
        int var8 = 0;

        int nameWidth;
        while (namesIt.hasNext()) {
            String name = namesIt.next();
            nameWidth = this.minecraft.font.width(name);
            maxNameWidth = Math.max(maxNameWidth, nameWidth);
        }

        names = names.subList(0, Math.min(names.size(), 80));
        int namesSize = names.size();
        int var35 = namesSize;

        for(nameWidth = 1; var35 > 20; var35 = (namesSize + nameWidth - 1) / nameWidth) {
            ++nameWidth;
        }

        int yPl = 10;
        int var14 = Math.min((maxNameWidth + 13), screenWidth - 50) / nameWidth;
        int var15 = screenWidth / 2 - (var14 * nameWidth + (nameWidth - 1) * 5) / 2;
        int var17 = var14 * nameWidth + (nameWidth - 1) * 5;

        fill(screenWidth / 2 - var17 / 2 - 1, yPl - 1, screenWidth / 2 + var17 / 2 + 1, yPl + var35 * 9, Integer.MIN_VALUE);

        int var23;
        int var22;
        for(int i = 0; i < namesSize; ++i) {
            var22 = i / var35;
            var23 = i % var35;
            int x0 = var15 + var22 * var14 + var22 * 5;
            int y0 = yPl + var23 * 9;
            fill(x0, y0, x0 + var14, y0 + 8, 553648127);
            RenderHelper.enableBlend();
            RenderHelper.defaultBlendFunc();

            if (i < names.size()) {
                String name = names.get(i);
                TextRenderer.drawStringShadow(name, x0, y0, -1);
            }
        }
    }
}
