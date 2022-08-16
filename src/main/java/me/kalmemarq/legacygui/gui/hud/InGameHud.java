package me.kalmemarq.legacygui.gui.hud;

import com.mojang.minecraft.ChatMessage;
import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gamemode.CreativeMode;
import com.mojang.minecraft.gamemode.SurvivalMode;
import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.player.Inventory;
import com.mojang.minecraft.player.Player;
import com.mojang.minecraft.renderer.Tesselator;
import com.mojang.minecraft.util.Mth;
import me.kalmemarq.legacygui.LegacyGUIMod;
import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;
import me.kalmemarq.legacygui.gui.screen.ExtraScreen;
import me.kalmemarq.legacygui.gui.screen.TitleScreen;
import me.kalmemarq.legacygui.util.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

public class InGameHud extends ExtraDrawableHelper {
    private final Minecraft minecraft;
    private final DebugHud debugHud;
    private final PlayerListHud playerListHud;
    private final ChatHud chatHud;
    private int tickCount;
    private int screenWidth;
    private int screenHeight;

    private int toolHighlightTimer;
    private int lastToolHighlight;

    public InGameHud(Minecraft mc) {
        this.minecraft = mc;
        this.debugHud = new DebugHud(mc);
        this.chatHud = new ChatHud(mc);
        this.playerListHud = new PlayerListHud(mc, this);
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

        int var19;
        for(var19 = 0; var19 < LegacyGUIMod.inGameHud.chatHud.messages.size(); ++var19) {
            ++this.chatHud.messages.get(var19).age;
        }
    }

    public void render(float deltaTime) {
        this.screenWidth = this.minecraft.width / ExtraScreen.scale;
        this.screenHeight = this.minecraft.height / ExtraScreen.scale;
        this.minecraft.q.a();

        RenderHelper.enableBlend();

        if (!TitleScreen.hideHud) {
            this.renderHotbar(deltaTime);


            if (this.toolHighlightTimer > 0 && lastToolHighlight > -1) {
                int x = screenWidth / 2 - this.minecraft.font.width(Language.translate(TileID.get(this.lastToolHighlight).getTranslationKey())) / 2;
                int y = screenHeight - 59;

                if (this.minecraft.player != null) {
                    if (this.minecraft.gameMode.creative) {
                        y += 14;
                    }
                }

                int textAlpha = (int)((float)this.toolHighlightTimer * 256.0F / 10.0F);
                if (textAlpha > 255) {
                    textAlpha = 255;
                }

                if (textAlpha > 0) {
                    RenderHelper.setGlobalColor(1.0f, 1.0f, 1.0f, 1.0f);
                    RenderHelper.enableBlend();
                    RenderHelper.defaultBlendFunc();
                    TextRenderer.drawStringShadow(Language.translate(TileID.get(this.lastToolHighlight).getTranslationKey()), x, y, 16777215 + (textAlpha << 24));
                    RenderHelper.disableBlend();
                }
            }
        }

        RenderHelper.bindTexture("/gui/icons.png");
        RenderHelper.setGlobalColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.enableBlend();
        this.renderCrosshair();
        RenderHelper.defaultBlendFunc();

        if (!(this.minecraft.gameMode instanceof CreativeMode) && !TitleScreen.hideHud && !TitleScreen.showF3) {
            String score = "Score: &e" + this.minecraft.player.getScore();
            TextRenderer.drawStringShadow(score, this.screenWidth - this.minecraft.font.width(score) - 2, 2, 16777215);
//            TextRenderer.drawStringShadow("Arrows: " + this.minecraft.player.arrows, this.screenWidth / 2 + 8, this.screenHeight - 33, 16777215);
        }

        if (!TitleScreen.hideHud && TitleScreen.showF3) {
            this.debugHud.render();
        } else if (!TitleScreen.hideHud) {
            drawStringShadow("c0.30", 2, 2, 0xFFFFFF);
        }

        if (Keyboard.isKeyDown(LegacyGUIMod.PLAYERLIST_KEYBIND.key) && this.minecraft.connectionManager != null && this.minecraft.connectionManager.isConnected()) {
            this.playerListHud.render(screenWidth);
        }

        if (!TitleScreen.hideHud) {
            this.chatHud.render();
        }

        RenderHelper.disableBlend();
    }

    private void renderHotbar(float deltaTime) {
        Player player = this.minecraft.player;

        if (player != null) {
            RenderHelper.setGlobalColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderHelper.bindTexture("/gui/gui.png");

            blitOffset = -90.0f;

            int scrnCenter = this.screenWidth / 2;
            drawTextureXX(scrnCenter - 91, this.screenHeight - 22, 0, 0, 182, 22);
            drawTextureXX(scrnCenter - 91 - 1 + player.inventory.selected * 20, this.screenHeight - 22 - 1, 0, 22, 24, 22);

            blitOffset = 0.0f;

            String var21;
            Inventory var8 = player.inventory;
            int var15;
            for(int var12 = 0; var12 < var8.slots.length; ++var12) {
                int var13 = screenWidth / 2 - 90 + var12 * 20;
                int var14 = screenHeight - 16;
                if ((var15 = var8.slots[var12]) > 0) {
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)var13, (float)var14, -50.0F);

                    float vara = (float)var8.popTime[var12] - deltaTime;
                    if (vara > 0.0F) {
                        float var9 = 1.0F + vara / 5.0F;
                        GL11.glTranslatef((float)(8), (float)(12), 0.0f);
                        GL11.glScalef(1.0F / var9, (var9 + 1.0F) / 2.0F, 1.0F);
                        GL11.glTranslatef((float)(-(8)), (float)(-(12)), 0.0f);
                    }

                    GL11.glScalef(10.0F, 10.0F, 10.0F);
                    GL11.glTranslatef(1.0F, 0.5F, 0.0F);
                    GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(-1.5F, 0.5F, 0.5F);
                    GL11.glScalef(-1.0F, -1.0F, -1.0F);
                    RenderHelper.bindTexture("/terrain.png");
                    Tesselator var7 = Tesselator.instance;
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    var7.begin();
                    Tile.tiles[var15].renderAsIcon(var7);
                    var7.end();
                    GL11.glPopMatrix();
                    if (var8.count[var12] > 1) {
                        var21 = "" + var8.count[var12];
                        TextRenderer.drawStringShadow(var21, var13 + 19 - this.minecraft.font.width(var21), var14 + 6, 16777215);
                    }
                }
            }
        }
    }

    private void renderCrosshair() {
        if (!TitleScreen.hideHud) {
            GL14.glBlendFuncSeparate(GlConst.ONE_MINUS_DST_COLOR, GlConst.ONE_MINUS_SRC_COLOR, GlConst.ONE, GlConst.ZERO);

            this.blit((this.screenWidth - 15) / 2, (this.screenHeight - 15) / 2, 0, 0, 15, 15);
        }
    }

    public void addMessage(String message) {
        this.chatHud.addMessage(message);
    }
}
