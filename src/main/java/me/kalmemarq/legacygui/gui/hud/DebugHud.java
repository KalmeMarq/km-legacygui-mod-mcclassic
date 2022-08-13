package me.kalmemarq.legacygui.gui.hud;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.Minecraft;
import me.kalmemarq.legacygui.LegacyGUIMod;
import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;
import me.kalmemarq.legacygui.gui.screen.ExtraScreen;
import me.kalmemarq.legacygui.util.TextRenderer;
import me.kalmemarq.legacygui.util.TileID;
import org.spongepowered.include.com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

public class DebugHud extends ExtraDrawableHelper {
    private final Minecraft minecraft;

    public DebugHud(Minecraft mc) {
        this.minecraft = mc;
    }

    public void render() {
        this.renderLeftText();
        this.renderRightText();
    }
    
    protected void renderLeftText() {
        List<String> list = getLeftText();
        int fontHeight = 9;

        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i);

            if (!Strings.isNullOrEmpty(line)) {
                int lineWidth = this.minecraft.font.width(line);
                int y = 2 + fontHeight * i;
                fill(1, y - 1, 2 + lineWidth + 1, y + fontHeight - 1, -1873784752);
                TextRenderer.drawString(line, 2, y, 14737632);
            }
        }
    }

    protected void renderRightText() {
        List<String> list = getRightText();
        int fontHeight = 9;

        for(int i = 0; i < list.size(); ++i) {
            String line = list.get(i);
            if (!Strings.isNullOrEmpty(line)) {
                int lineWidth = this.minecraft.font.width(line);
                int x0 = (this.minecraft.width / ExtraScreen.scale) - 2 - lineWidth;
                int y0 = 2 + fontHeight * i;
                fill(x0 - 1, y0 - 1, x0 + lineWidth + 1, y0 + fontHeight - 1, -1873784752);
                TextRenderer.drawString(line, x0, y0, 14737632);
            }
        }
    }

    protected List<String> getLeftText() {
        List<String> list = new ArrayList<>();

        list.add("Minecraft c0.30");
        list.add(this.minecraft.title);
        list.add("World Size: " + this.minecraft.level.width + "x" + this.minecraft.level.height + "x" + this.minecraft.level.depth);
        list.add("Spawn XYZ: " + this.minecraft.level.xSpawn + " / " + this.minecraft.level.ySpawn + " / " + this.minecraft.level.zSpawn);
        list.add("Water Level: " + this.minecraft.level.waterLevel);
        list.add("User: " + this.minecraft.user.name);

        int eN = 0;

        for (int i = 0; i < this.minecraft.level.blockMap.entityGrid.length; i++) {
            List<Entity> a = this.minecraft.level.blockMap.entityGrid[i];

            for (int j = 0; j < a.size(); j++) {
                eN += 1;
            }
        }

        list.add("Entities: " + eN);
        list.add("XYZ: " + this.minecraft.level.player.x + " / " + this.minecraft.level.player.y + " / " + this.minecraft.level.player.z);

        int[] cloudColor = splitColor(this.minecraft.level.cloudColor);
        list.add("Cloud Color: " + String.format("%s, %s, %s", cloudColor[0], cloudColor[1], cloudColor[2]));

        int[] skyColor = splitColor(this.minecraft.level.skyColor);
        list.add("Sky Color: " + String.format("%s, %s, %s", skyColor[0], skyColor[1], skyColor[2]));

        int[] fogColor = splitColor(this.minecraft.level.fogColor);
        list.add("Fog Color: " + String.format("%s, %s, %s", fogColor[0], fogColor[1], fogColor[2]));

        int tile = this.minecraft.level.getTile((int)this.minecraft.player.x, ((int)this.minecraft.player.y) - 2, (int)this.minecraft.player.z);
        list.add("Block: " + (int)this.minecraft.level.player.x + " / " + (int)this.minecraft.level.player.y + " / " + (int)this.minecraft.level.player.z + " - ID: " + tile + " / " + TileID.get(tile).getName());

        list.add("Brightness: " + this.minecraft.level.getBrightness((int)this.minecraft.player.x, ((int)this.minecraft.player.y) - 2, (int)this.minecraft.player.z));

        return list;
    }

    private List<String> getRightText() {
        List<String> list = new ArrayList<>();

        list.add(String.format("Java: %s %dbit", System.getProperty("java.version"), LegacyGUIMod.is64Bit() ? 64 : 32));

        long maxMem = Runtime.getRuntime().maxMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long freeMem = Runtime.getRuntime().freeMemory();
        long availMem = totalMem - freeMem;

        list.add(String.format("Mem: % 2d%% %03d/%03dMB", availMem * 100L / maxMem, bytesToMegabytes(availMem), bytesToMegabytes(totalMem)));
        list.add(String.format("Allocated: % 2d%% %03dMB", totalMem * 100L / maxMem, bytesToMegabytes(totalMem)));
        list.add(String.format("Display: %dx%d", this.minecraft.width, this.minecraft.height));

        return list;
    }

    private static int[] splitColor(int color) {
        return new int[]{ (color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF };
    }

    private static long bytesToMegabytes(long bytes) {
        return bytes / 1024L / 1024L;
    }
}
