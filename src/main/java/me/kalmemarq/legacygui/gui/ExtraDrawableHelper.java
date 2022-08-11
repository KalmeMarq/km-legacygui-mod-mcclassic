package me.kalmemarq.legacygui.gui;

import com.mojang.minecraft.gui.DrawableHelper;
import com.mojang.minecraft.renderer.Tesselator;
import org.lwjgl.opengl.GL11;

public class ExtraDrawableHelper extends DrawableHelper {
    protected void drawTexture(int x, int y, int width, int height, int u, int v, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
        int x0 = x;
        int x1 = x + width;
        int y0 = y;
        int y1 = y + height;

        float u0 = u / (float)textureWidth;
        float v0 = v / (float)textureHeight;
        float u1 = (u + regionWidth) / (float)textureWidth;
        float v1 = (v + regionHeight) / (float)textureHeight;

        Tesselator builder = Tesselator.instance;
        builder.begin();
        builder.vertexUV(x0, y1, this.blitOffset, u0, v1);
        builder.vertexUV(x1, y1, this.blitOffset, u1, v1);
        builder.vertexUV(x1, y0, this.blitOffset, u1, v0);
        builder.vertexUV(x0, y0, this.blitOffset, u0, v0);
        builder.end();
    }

    public static void fillGradient(int x0, int y0, int x1, int y1, int z, int color1, int color2) {
        float var6 = (float)(color1 >>> 24) / 255.0F;
        float var7 = (float)(color1 >> 16 & 255) / 255.0F;
        float var8 = (float)(color1 >> 8 & 255) / 255.0F;
        int col1 = (int) ((float)(color1 & 255) / 255.0F);
        float var9 = (float)(color2 >>> 24) / 255.0F;
        float var10 = (float)(color2 >> 16 & 255) / 255.0F;
        float var11 = (float)(color2 >> 8 & 255) / 255.0F;
        int col2 = (int) ((float)(color2 & 255) / 255.0F);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(7);
        GL11.glColor4f(var7, var8, col1, var6);
        GL11.glVertex3f((float)x1, (float)y0, z);
        GL11.glVertex3f((float)x0, (float)y0, z);
        GL11.glColor4f(var10, var11, col2, var9);
        GL11.glVertex3f((float)x0, (float)y1, z);
        GL11.glVertex3f((float)x1, (float)y1, z);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }

    public static void fillGradientX(int x0, int y0, int x1, int y1, int z, int colorStart, int colorEnd) {
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);

        Tesselator tesselator = Tesselator.instance;
        tesselator.begin();
        fillGradientX(tesselator, x0, y0, x1, y1, z, colorStart, colorEnd);
        tesselator.end();

        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }

    public static void fillGradientX(Tesselator tesselator, int x0, int y0, int x1, int y1, int z, int colorStart, int colorEnd) {
        float sA = (float)(colorStart >>> 24) / 255.0F;
        float sR = (float)(colorStart >> 16 & 255) / 255.0F;
        float sG = (float)(colorStart >> 8 & 255) / 255.0F;
        float sB = (int) ((float)(colorStart & 255) / 255.0F);

        float eA = (float)(colorStart >>> 24) / 255.0F;
        float eR = (float)(colorStart >> 16 & 255) / 255.0F;
        float eG = (float)(colorStart >> 8 & 255) / 255.0F;
        float eB = (int) ((float)(colorStart & 255) / 255.0F);

        tesselator.color(sA, sG, sB);
        tesselator.vertex(x0, y1, z);

        tesselator.color(sA, sG, sB);
        tesselator.vertex(x1, y1, z);

        tesselator.color(sA, sG, sB);
        tesselator.vertex(x1, y0, z);

        tesselator.color(sA, sG, sB);
        tesselator.vertex(x0, y0, z);
    }

    public static void fillX(int x0, int y0, int x1, int y1, int color) {
        float sA = (float)(color >>> 24) / 255.0F;
        float sR = (float)(color >> 16 & 255) / 255.0F;
        float sG = (float)(color >> 8 & 255) / 255.0F;
        float sB = ((float)(color & 255) / 255.0F);

        Tesselator tesselator = Tesselator.instance;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);

        tesselator.begin();
        tesselator.color(sR, sG, sB);
        tesselator.vertex(x0, y1, 0);

        tesselator.color(sR, sG, sB);
        tesselator.vertex(x1, y1, 0);

        tesselator.color(sR, sG, sB);
        tesselator.vertex(x1, y0, 0);

        tesselator.color(sR, sG, sB);
        tesselator.vertex(x0, y0, 0);
        tesselator.end();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
}
