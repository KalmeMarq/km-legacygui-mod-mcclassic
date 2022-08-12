package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.InGameHud;
import com.mojang.minecraft.item.Item;
import me.kalmemarq.legacygui.gui.ITickable;
import me.kalmemarq.legacygui.gui.screen.CreativeInventoryScreen;
import me.kalmemarq.legacygui.gui.screen.TitleScreen;
import me.kalmemarq.legacygui.util.GlConst;
import me.kalmemarq.legacygui.util.Language;
import me.kalmemarq.legacygui.util.TileID;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGameHud.class)
public class InGameHudMixin implements ITickable {
    @Shadow private Minecraft minecraft;
    @Shadow private int scaledWidth;
    @Shadow private int scaledHeight;
    private int toolHighlightTimer;
    private int lastToolHighlight;
    private int tickCount;

    @Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
    private void renderHidHud(float hasScreen, boolean mouseX, int mouseY, int par4, CallbackInfo ci) {
        if (TitleScreen.hideHud) {
            ci.cancel();
        }
    }

    public void tick() {
        ++this.tickCount;

        if (this.minecraft.player != null) {
            int id = this.minecraft.player.inventory.getSelected();

            if (id == -1) {
                this.toolHighlightTimer = 0;
            } else if (this.lastToolHighlight != -1 && id == this.lastToolHighlight) {
                if (this.toolHighlightTimer > 0) {
                    --this.toolHighlightTimer;
                }
            } else {
                this.toolHighlightTimer = 40;
            }

            this.lastToolHighlight = id;
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/Font;drawShadow(Ljava/lang/String;III)V"))
    private void render(float hasScreen, boolean mouseX, int mouseY, int par4, CallbackInfo ci) {
//        this.minecraft.font.drawShadow("Fabric Mod in classic 0.30. what a time to be alive...", 50, 1, 0xFF00FF);

        if (this.toolHighlightTimer > 0 && lastToolHighlight > -1) {
            int x = this.scaledWidth / 2 - this.minecraft.font.width(Language.translate(TileID.get(this.lastToolHighlight).getTranslationKey())) / 2;
            int y = this.scaledHeight - 59;

            if (this.minecraft.player != null) {
                if (!this.minecraft.gameMode.creative) {
                    y += 14;
                }
            }

            int textAlpha = (int)((float)this.toolHighlightTimer * 256.0F / 10.0F);
            if (textAlpha > 255) {
                textAlpha = 255;
            }

            if (textAlpha > 0) {
                GL11.glEnable(GlConst.GL_BLEND);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, textAlpha / 255.0f);
                this.minecraft.font.drawShadow(Language.translate(TileID.get(this.lastToolHighlight).getTranslationKey()), x, y, 16777215);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glDisable(GlConst.GL_BLEND);
            }
        }

        if (TitleScreen.showF3) {
        int y = 11;
        if (!this.minecraft.options.showFrameRate) this.minecraft.font.drawShadow(this.minecraft.title, 2, 12, 0xFFFFFF);
        y += 10;

        int eN = 0;

        for (int i = 0; i < this.minecraft.level.blockMap.entityGrid.length; i++) {
           List<Entity> a = this.minecraft.level.blockMap.entityGrid[i];

            for (int j = 0; j < a.size(); j++) {
                eN += 1;
            }
        }

        this.minecraft.font.drawShadow("World Size: " + this.minecraft.level.width + "x" + this.minecraft.level.height + "x" + this.minecraft.level.depth, 1, y, 0xFFFFFF);
        y += 10;

        this.minecraft.font.drawShadow("Spawn XYZ: " + this.minecraft.level.xSpawn + " / " + this.minecraft.level.ySpawn + " / " + this.minecraft.level.zSpawn, 1, y, 0xFFFFFF);
        y += 10;

        this.minecraft.font.drawShadow("Water Level: " + this.minecraft.level.waterLevel, 1, y, 0xFFFFFF);
        y += 10;

        this.minecraft.font.drawShadow("User: " + this.minecraft.user.name, 1, y, 0xFFFFFF);
        y += 10;

        this.minecraft.font.drawShadow("Entities: " + eN, 1, y, 0xFFFFFF);
        y += 10;

        this.minecraft.font.drawShadow("XYZ: " + this.minecraft.level.player.x + " / " + this.minecraft.level.player.y + " / " + this.minecraft.level.player.z, 1, y, 0xFFFFFF);
        y += 10;

        int[] cloudColor = splitColor(this.minecraft.level.cloudColor);
        this.minecraft.font.drawShadow("Cloud Color: " + String.format("%s, %s, %s", cloudColor[0], cloudColor[1], cloudColor[2]), 1, y, 0xFFFFFF);
        y += 10;

        int[] skyColor = splitColor(this.minecraft.level.skyColor);
        this.minecraft.font.drawShadow("Sky Color: " + String.format("%s, %s, %s", skyColor[0], skyColor[1], skyColor[2]), 1, y, 0xFFFFFF);
        y += 10;

        int[] fogColor = splitColor(this.minecraft.level.fogColor);
        this.minecraft.font.drawShadow("Fog Color: " + String.format("%s, %s, %s", fogColor[0], fogColor[1], fogColor[2]), 1, y, 0xFFFFFF);
        y += 10;

        int tile = this.minecraft.level.getTile((int)this.minecraft.player.x, ((int)this.minecraft.player.y) - 2, (int)this.minecraft.player.z);
        this.minecraft.font.drawShadow("Block: " + (int)this.minecraft.level.player.x + " / " + (int)this.minecraft.level.player.y + " / " + (int)this.minecraft.level.player.z + " - ID: " + tile + " / " + getTileName(tile), 1, y, 0xFFFFFF);
        y += 10;

        this.minecraft.font.drawShadow("Brightness: " + this.minecraft.level.getBrightness((int)this.minecraft.player.x, ((int)this.minecraft.player.y) - 2, (int)this.minecraft.player.z), 1, y, 0xFFFFFF);
        y += 10;
        }
    }

    private static String getTileName(int id) {
        switch (id) {
            case 49: return "obdisian";
            case 48: return "mossy cobblestone";
            case 47: return "bookshelf";
            case 46: return "tnt";
            case 45: return "bricks";
            case 44: return "slab";
            case 43: return "double slab";
            case 42: return "iron block";
            case 41: return "gold block";
            case 40: return "red mushroom";
            case 39: return "brown mushroom";
            case 38: return "rose";
            case 37: return "dandelion";
            case 36: return "white cloth";
            case 35: return "light gray cloth";
            case 34: return "dark gray cloth";
            case 33: return "rose cloth";
            case 32: return "magenta cloth";
            case 31: return "purple cloth";
            case 30: return "violet cloth";
            case 29: return "ultramarine cloth";
            case 28: return "capri cloth";
            case 27: return "cyan cloth";
            case 26: return "spring green cloth";
            case 25: return "green cloth";
            case 24: return "chartreuse cloth";
            case 23: return "yellow cloth";
            case 22: return "orange cloth";
            case 21: return "red cloth";
            case 20: return "glass";
            case 19: return "sponge";
            case 18: return "leaves";
            case 17: return "log";
            case 16: return "coal ore";
            case 15: return "iron ore";
            case 14: return "gold ore";
            case 13: return "gravel";
            case 12: return "sand";
            case 11: return "calm lava";
            case 10: return "lava";
            case 9: return "calm water";
            case 8: return "water";
            case 7: return "unbreakable (invisible bedrock?)";
            case 6: return "bush";
            case 5: return "wood";
            case 4: return "stone brick";
            case 3: return "dirt";
            case 2: return "grass";
            case 1: return "rock";
            case 0: return "air";
        }

        return "UNKNOWN";
    }

    private static int[] splitColor(int color) {
        return new int[]{ (color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF };
    }
}
