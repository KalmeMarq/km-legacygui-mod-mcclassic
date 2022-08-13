package me.kalmemarq.legacygui.gui.hud;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gamemode.CreativeMode;
import com.mojang.minecraft.gamemode.SurvivalMode;
import com.mojang.minecraft.player.Player;
import me.kalmemarq.legacygui.LegacyGUIMod;
import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;
import me.kalmemarq.legacygui.gui.screen.ExtraScreen;
import me.kalmemarq.legacygui.gui.screen.TitleScreen;
import me.kalmemarq.legacygui.util.GlConst;
import me.kalmemarq.legacygui.util.RenderHelper;
import me.kalmemarq.legacygui.util.TextRenderer;
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

    public InGameHud(Minecraft mc) {
        this.minecraft = mc;
        this.debugHud = new DebugHud(mc);
        this.chatHud = new ChatHud(mc);
        this.playerListHud = new PlayerListHud(mc, this);
    }

    public void tick() {
        ++this.tickCount;
    }

    public void render(float deltaTime) {
        this.screenWidth = this.minecraft.width / ExtraScreen.scale;
        this.screenHeight = this.minecraft.height / ExtraScreen.scale;
        this.minecraft.q.a();

        RenderHelper.enableBlend();

        if (!TitleScreen.hideHud) {
            this.renderHotbar();
        }

        RenderHelper.bindTexture("/gui/icons.png");
        RenderHelper.setGlobalColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.enableBlend();
        this.renderCrosshair();
        RenderHelper.defaultBlendFunc();

        if (!(this.minecraft.gameMode instanceof CreativeMode) && !TitleScreen.hideHud && !TitleScreen.showF3) {
            String score = "Score: &e" + this.minecraft.player.getScore();
            TextRenderer.drawStringShadow(score, this.screenWidth - this.minecraft.font.width(score) - 2, 2, 16777215);
            TextRenderer.drawStringShadow("Arrows: " + this.minecraft.player.arrows, this.screenWidth / 2 + 8, this.screenHeight - 33, 16777215);
        }

        if (!TitleScreen.hideHud && TitleScreen.showF3) {
            this.debugHud.render();
        } else if (!TitleScreen.hideHud) {
            drawStringShadow("c0.30", 2, 2, 0xFFFFFF);
        }

        if (Keyboard.isKeyDown(LegacyGUIMod.PLAYERLIST_KEYBIND.key) && this.minecraft.connectionManager != null && this.minecraft.connectionManager.isConnected()) {
            this.playerListHud.render(screenWidth);
        }

        RenderHelper.disableBlend();
    }

    private void renderHotbar() {
        Player player = this.minecraft.player;

        if (player != null) {
            RenderHelper.setGlobalColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderHelper.bindTexture("/gui/gui.png");

            blitOffset = -90.0f;

            int scrnCenter = this.screenWidth / 2;
            drawTextureXX(scrnCenter - 91, this.screenHeight - 22, 0, 0, 182, 22);
            drawTextureXX(scrnCenter - 91 - 1 + player.inventory.selected * 20, this.screenHeight - 22 - 1, 0, 22, 24, 22);

            blitOffset = 0.0f;
        }
    }

    private void renderCrosshair() {
        if (!TitleScreen.hideHud) {
            GL14.glBlendFuncSeparate(GlConst.ONE_MINUS_DST_COLOR, GlConst.ONE_MINUS_SRC_COLOR, GlConst.ONE, GlConst.ZERO);

            this.blit((this.screenWidth - 15) / 2, (this.screenHeight - 15) / 2, 0, 0, 15, 15);
        }
    }
}
