package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.util.Mth;
import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;
import me.kalmemarq.legacygui.gui.widget.ButtonWidget;
import me.kalmemarq.legacygui.gui.widget.TexturedButtonWidget;
import me.kalmemarq.legacygui.util.*;
import net.fabricmc.loader.api.FabricLoader;
import org.lwjgl.opengl.GL11;

import java.util.Random;


public class TitleScreen extends ExtraScreen {
    private static final String COPYRIGHT = "Copyright Mojang AB. Do not distribute!";
    private int copyrightWidth;
    public static boolean showF3 = false;
    public static boolean hideHud = false;
    private String splash;
    private final boolean showMinceraft;
    private final boolean showLegacyTitle;

    public TitleScreen() {
        this.showMinceraft = new Random().nextFloat() < 0.09f;
        this.showLegacyTitle = new Random().nextFloat() < 0.06f;
    }

    @Override
    public void init() {
        if (this.splash == null) {
            this.splash = SplashManager.getRandom();
        }

        copyrightWidth = this.font.width(COPYRIGHT);

        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 45, 200, 20, Language.translate("menu.singleplayer"), button -> {
            this.minecraft.openScreen(new CreateNewLevelScreen(this));
        }));

        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 45 + 24, 200, 20, Language.translate("menu.multiplayer"), button -> {
            this.minecraft.openScreen(new MultiplayerScreen(this));
        }));

        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 45 + 48, 98, 20, Language.translate("menu.mods"), button -> {
            this.minecraft.openScreen(new ModsScreen(this));
        }, ((button, mouseX, mouseY) -> {
            this.drawTooltip(FabricLoader.getInstance().getAllMods().size() + " Mods", mouseX, mouseY);
        })));

        this.addWidget(new ButtonWidget(this.width / 2 + 2, this.height / 4 + 45 + 48, 98, 20, Language.translate("Texture Packs"), button -> {
        }));

        this.addWidget(new TexturedButtonWidget(this.width / 2 - 100 - 24, this.height / 4 + 45 + 86, 20, 20, 0, 106, 20, "gui/gui.png", button -> {
            this.minecraft.openScreen(new LanguageScreen(this));
        }));

        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 45 + 86, 98, 20, Language.translate("menu.options"), button -> {
            this.minecraft.openScreen(new OptionsScreen(this, this.minecraft.options));
        }));

        this.addWidget(new ButtonWidget(this.width / 2 + 2, this.height / 4 + 45 + 86, 98, 20, Language.translate("menu.quit"), button -> {
            this.minecraft.destroy();
            System.exit(0);
        }));
    }

    @Override
    public boolean canBeClosed() {
        return false;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        this.renderDirtBackground();

        int titleX = this.width / 2 - 137;

        RenderHelper.setGlobalColor(1.0F, 1.0F, 1.0F, 1.0F);

        if (showLegacyTitle) {
            titleX = this.width / 2 - 245 / 2;
            RenderHelper.bindTexture("/assets/kmlegacygui/textures/gui/cobblestone_title.png");
            ExtraDrawableHelper.drawTextureXX(titleX, 30, 0, 0, 245, 40, 245, 64);
        } else {
            RenderHelper.bindTexture("/assets/kmlegacygui/textures/gui/minecraft.png");

            if (showMinceraft) {
                ExtraDrawableHelper.drawTextureXX(titleX, 30, 0, 0, 99, 44);
                ExtraDrawableHelper.drawTextureXX(titleX + 99, 30, 129, 0, 27, 44);
                ExtraDrawableHelper.drawTextureXX(titleX + 99 + 26, 30, 126, 0, 3, 44);
                ExtraDrawableHelper.drawTextureXX(titleX + 99 + 26 + 3, 30, 99, 0, 26, 44);
                ExtraDrawableHelper.drawTextureXX(titleX + 155, 30, 0, 45, 155, 44);
            } else {
                ExtraDrawableHelper.drawTextureXX(titleX, 30, 0, 0, 155, 44);
                ExtraDrawableHelper.drawTextureXX(titleX + 155, 30, 0, 45, 155, 44);
            }
        }

        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.width / 2 + 90), 70.0f, 0.0f);
        GL11.glRotatef(-20, 0, 0, 1.0f);

        float scale = 1.8F - Math.abs(Mth.sin((float) (System.currentTimeMillis() % 1000L) / 1000.0F * 6.2831855F) * 0.1F);
        scale = scale * 100.0F / (float) (this.font.width(this.splash) + 32);
        GL11.glScalef(scale, scale, 0);

        drawCenteredString(this.font, this.splash, 0, -8, 0xfff717);
        GL11.glPopMatrix();

        TextRenderer.drawStringShadow("Minecraft c0.30 (" + FabricLoader.getInstance().getAllMods().size() + " Mods)", 1, 1, 0xAAAAAA);
        drawString(this.font, COPYRIGHT, this.width - this.copyrightWidth - 1, this.height - 10, 0xAAAAAA);

        super.render(mouseX, mouseY);
    }
}
