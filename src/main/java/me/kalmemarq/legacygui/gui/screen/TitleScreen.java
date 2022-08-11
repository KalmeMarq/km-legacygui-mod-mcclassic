package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.gui.*;
import com.mojang.minecraft.renderer.Tesselator;
import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;
import me.kalmemarq.legacygui.gui.component.ButtonWidget;
import org.lwjgl.opengl.GL11;


public class TitleScreen extends ExtraScreen {
    public static boolean showF3 = false;
    public static boolean isInGame = true;

    @Override
    public void init() {
        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 45, 200, 20, "Create New Level", (button) -> {
            this.minecraft.openScreen(new CreateNewLevelScreen(this));
        }));

        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 45 + 24, 200, 20, "Custom screen", (button -> {
            this.minecraft.openScreen(new CustomScreen(this));
        }), (button, mouseX, mouseY) -> {
            this.drawTooltip("Custom screen with extra screen class", mouseX, mouseY);
        }));

        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 45 + 48, 98, 20, "Options...", (button) -> {
            this.minecraft.openScreen(new OptionsScreen(this, this.minecraft.options));
        }));

        this.addWidget(new ButtonWidget(this.width / 2 + 2, this.height / 4 + 45 + 48, 98, 20, "Quit Game", (button -> {
            this.minecraft.destroy();
            System.exit(0);
        })));
    }

    @Override
    public void render(int mouseX, int mouseY) {
        this.renderDirtBackground();

        int titleX = this.width / 2 - 137;
        drawTexture("minecraft.png", titleX, 30, 155, 44, 0, 0, 155, 44, 256, 256);
        drawTexture("minecraft.png", titleX + 155, 30, 155, 44, 0, 45, 155, 44, 256, 256);

        drawString(this.font, "Minecraft c0.30", 1, 1, 0x999999);

        super.render(mouseX, mouseY);
    }

    private void drawTexture(String name, int x, int y, int width, int height, int u, int v, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
        int id = this.minecraft.textures.getTextureId("/" + name);
        GL11.glBindTexture(3553, id);

        int x0 = x;
        int x1 = x + width;
        int y0 = y;
        int y1 = y + height;

        float u0 = u / (float)textureWidth;
        float v0 = v / (float)textureHeight;
        float u1 = (u + regionWidth) / (float)textureWidth;
        float v1 = (v + regionHeight) / (float)textureHeight;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Tesselator builder = Tesselator.instance;
        builder.begin();
        builder.vertexUV(x0, y1, this.blitOffset, u0, v1);
        builder.vertexUV(x1, y1, this.blitOffset, u1, v1);
        builder.vertexUV(x1, y0, this.blitOffset, u1, v0);
        builder.vertexUV(x0, y0, this.blitOffset, u0, v0);
        builder.end();
    }

    private void drawTooltip(String text, int x, int y) {
        drawTooltip(new String[]{text}, x, y);
    }

    private void drawTooltip(String[] text, int x, int y) {
        if (text.length > 0) {
            int fontHeight = 10;

            int maxWidth = 0;
            int var6 = text.length == 1 ? -2 : 0;

            for (int i = 0; i < text.length; i++) {
                int lineWidth = this.minecraft.font.width(text[i]);
                var6 += fontHeight;
                if (lineWidth > maxWidth) {
                    maxWidth = lineWidth;
                }
            }

            int var23 = x + 12;
            int var24 = y - 12;

            if (var23 + maxWidth > this.width) {
                var23 -= 28 + maxWidth;
            }

            if (var24 + var6 + 6 > this.height) {
                var24 = this.height - var6 - 6;
            }

            if (y - var6 - 8 < 0) {
                var24 = y + 8;
            }

            int z = 100;
            GL11.glTranslatef(0, 0, z);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glBegin(GL11.GL_QUADS);
            fillGradient(var23 - 3, var24 - 4, var23 + maxWidth + 3, var24 - 3, 0,-267386864, -267386864);
            fillGradient(var23 - 3, var24 + var6 + 3, var23 + maxWidth + 3, var24 + var6 + 4, 0, -267386864, -267386864);
            fillGradient(var23 - 3, var24 - 3, var23 + maxWidth + 3, var24 + var6 + 3, 0,-267386864, -267386864);
            fillGradient(var23 - 4, var24 - 3, var23 - 3, var24 + var6 + 3, 0,-267386864, -267386864);
            fillGradient(var23 + maxWidth + 3, var24 - 3, var23 + maxWidth + 4, var24 + var6 + 3, 0,-267386864, -267386864);
            fillGradient(var23 - 3, var24 - 3 + 1, var23 - 3 + 1, var24 + var6 + 3 - 1, 0,1347420415, 1344798847);
            fillGradient(var23 + maxWidth + 2, var24 - 3 + 1, var23 + maxWidth + 3, var24 + var6 + 3 - 1, 0,1347420415, 1344798847);
            fillGradient(var23 - 3, var24 - 3, var23 + maxWidth + 3, var24 - 3 + 1, 0,1347420415, 1347420415);
            fillGradient(var23 - 3, var24 + var6 + 2, var23 + maxWidth + 3, var24 + var6 + 3, 0,1344798847, 1344798847);
            GL11.glEnd();
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_TEXTURE_2D);

            int x00 = var23;
            int y00 = var24;
            for (int i = 0; i < text.length; i++) {
                this.minecraft.font.drawShadow(text[i], x00, y00, 0xFFFFFF);
                y00 += fontHeight;
            }

            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glTranslatef(0, 0, -z);
        }
    }

    private static void fillGradient(int x0, int y0, int x1, int y1, int z, int color1, int color2) {
        float var6 = (float)(color1 >>> 24) / 255.0F;
        float var7 = (float)(color1 >> 16 & 255) / 255.0F;
        float var8 = (float)(color1 >> 8 & 255) / 255.0F;
        int col1 = (int) ((float)(color1 & 255) / 255.0F);
        float var9 = (float)(color2 >>> 24) / 255.0F;
        float var10 = (float)(color2 >> 16 & 255) / 255.0F;
        float var11 = (float)(color2 >> 8 & 255) / 255.0F;
        int col2 = (int) ((float)(color2 & 255) / 255.0F);
        GL11.glColor4f(var7, var8, col1, var6);
        GL11.glVertex3f((float)x1, (float)y0, z);
        GL11.glVertex3f((float)x0, (float)y0, z);
        GL11.glColor4f(var10, var11, col2, var9);
        GL11.glVertex3f((float)x0, (float)y1, z);
        GL11.glVertex3f((float)x1, (float)y1, z);
    }
}
